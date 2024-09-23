package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Member findByUsername(String username);
    Member findByEmail(String email);
    
    @Query("SELECT m FROM Member m WHERE m.username = :usernameOrEmail OR m.email = :usernameOrEmail")
    Member findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}
