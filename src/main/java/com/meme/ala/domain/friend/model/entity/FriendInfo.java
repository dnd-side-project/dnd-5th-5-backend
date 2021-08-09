package com.meme.ala.domain.friend.model.entity;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@Document(collection = "FRIEND_INFO")
public class FriendInfo {

    @Id
    private ObjectId memberId;

    @Builder.Default
    private List<ObjectId> friends = new LinkedList<>();

    @Builder.Default
    private List<ObjectId> myAcceptancePendingList = new LinkedList<>();

    @Builder.Default
    private List<ObjectId> friendAcceptancePendingList = new LinkedList<>();

    public FriendRelation getRelation(ObjectId friendId){
        if(friends.contains(friendId))
            return FriendRelation.FRIEND;
        else if(myAcceptancePendingList.contains(friendId))
            return FriendRelation.FOLLOWER;
        else if(friendAcceptancePendingList.contains(friendId))
            return FriendRelation.FOLLOWING;
        else
            return FriendRelation.DEFAULT;
    }
}
