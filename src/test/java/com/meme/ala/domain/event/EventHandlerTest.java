package com.meme.ala.domain.event;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.repository.AggregationRepository;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.event.model.entity.DeleteEvent;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.friend.service.FriendInfoService;
import com.meme.ala.domain.member.model.entity.Member;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class EventHandlerTest {
    @MockBean
    private FriendInfoService friendInfoService;
    @MockBean
    private AggregationService aggregationService;
    @MockBean
    private AggregationRepository aggregationRepository;

    @Test
    void deleteMemberTest() {
        Member member = EntityFactory.testMember(); // objectId : 000000000000000000000001
        member.setId(new ObjectId(EntityFactory.testObjectId() + "1"));
        member.getMemberSetting().setNickname("friendNickname");

        Member follower = EntityFactory.testMember(); // objectId : 60f3f89c9f21ff292724eb38

        FriendInfo memberFriendInfo = EntityFactory.testFriendInfo();
        memberFriendInfo.getMyAcceptancePendingList().add(follower.getId());
        memberFriendInfo.getFriendAcceptancePendingList().add(follower.getId());
        memberFriendInfo.getFriends().add(follower.getId());

        given(friendInfoService.getFriendInfo(any(Member.class))).willReturn(memberFriendInfo);
        given(friendInfoService.getMemberFriend(any(Member.class))).willReturn(new LinkedList<>(Arrays.asList(member)));
        given(aggregationService.findByMember(any(Member.class))).willReturn(EntityFactory.testAggregation());
        doNothing().when(aggregationRepository).delete(any(Aggregation.class));

        deleteMember(new DeleteEvent(follower));

        assertFalse(memberFriendInfo.getFriendAcceptancePendingList().contains(follower.getId()));
    }

    private void deleteMember(DeleteEvent event) {
        Member deletedMember = event.getDeletedMember();

        Aggregation deletedAggregation = aggregationService.findByMember(deletedMember);
        aggregationRepository.delete(deletedAggregation);

        List<Member> friendList = friendInfoService.getMemberFriend(deletedMember);
        friendList.forEach(member -> flushFriendInfo(member, deletedMember));
    }

    private void flushFriendInfo(Member targetMember, Member deletedMember) {
        FriendInfo friendInfo = friendInfoService.getFriendInfo(targetMember);
        friendInfo
                .getFriends()
                .removeIf(f -> f == deletedMember.getId());
        friendInfo
                .getFriendAcceptancePendingList()
                .removeIf(f -> f == deletedMember.getId());
        friendInfo
                .getMyAcceptancePendingList()
                .removeIf(f -> f == deletedMember.getId());
    }
}
