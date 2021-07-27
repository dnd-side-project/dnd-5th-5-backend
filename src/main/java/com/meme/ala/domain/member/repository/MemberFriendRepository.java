package com.meme.ala.domain.member.repository;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberFriend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFriendRepository extends MongoRepository<MemberFriend, ObjectId> {
}
