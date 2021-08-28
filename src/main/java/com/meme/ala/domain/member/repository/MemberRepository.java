package com.meme.ala.domain.member.repository;

import com.meme.ala.domain.member.model.entity.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {
    Optional<Member> findByProviderIdAndMemberSetting_IsDeleted(String providerId, boolean flag);

    boolean existsMemberByMemberSettingNicknameAndMemberSetting_IsDeleted(String nickname, boolean flag);

    Optional<Member> findByMemberSettingNicknameAndMemberSetting_IsDeleted(String nickname, boolean flag);

    boolean existsMemberByProviderIdAndMemberSetting_IsDeleted(String providerId, boolean flag);

    Optional<Member> findByIdAndMemberSetting_IsDeleted(ObjectId id, boolean flag);

    Member findTop1ByMemberSettingNicknameRegexOrderByCreatedAtDesc(String regex);
}
