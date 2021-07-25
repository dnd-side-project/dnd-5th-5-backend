package com.meme.ala.domain.member.repository;

import com.meme.ala.domain.member.model.entity.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {
    Optional<Member> findByEmail(String email);
    boolean existsMemberByMemberSettingNickname(String nickname);
    Optional<Member> findByMemberSettingNickname(String nickname);
}
