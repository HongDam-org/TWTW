package com.twtw.backend.domain.friend.repository;

import com.twtw.backend.domain.friend.entity.Friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendCommandRepositoryImpl
        extends JpaRepository<Friend, UUID>, FriendCommandRepository {

    @Query(
            value =
                    "SELECT f.* FROM friend f JOIN member fm ON f.from_member_id = fm.id JOIN"
                        + " member tm ON f.to_member_id = tm.id WHERE f.friend_status = 'ACCEPTED'"
                        + " AND ((f.to_member_id = UNHEX(REPLACE(:memberId, '-', '')) AND"
                        + " MATCH(fm.nickname) AGAINST(:nickname IN BOOLEAN MODE)) OR"
                        + " (f.from_member_id = UNHEX(REPLACE(:memberId, '-', '')) AND"
                        + " MATCH(tm.nickname) AGAINST(:nickname IN BOOLEAN MODE)))",
            nativeQuery = true)
    List<Friend> findByMemberAndMemberNickname(
            @Param("memberId") final UUID memberId, @Param("nickname") final String nickname);

    Friend save(final Friend friend);
}
