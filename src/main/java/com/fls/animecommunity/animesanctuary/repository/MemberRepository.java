package com.fls.animecommunity.animesanctuary.repository;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	// Username으로 회원이 존재하는지 확인하는 메서드
    boolean existsByUsername(String username);
	Member findByUsername(String username);
    Member findByEmail(String email);
}
