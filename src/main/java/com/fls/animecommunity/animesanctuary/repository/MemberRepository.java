package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.Member;

@Repository
<<<<<<< Updated upstream
public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByUsername(String username);
=======
public interface MemberRepository extends CrudRepository<Member, Long> {
    boolean existsByUsername(String username);
    Member findByUsername(String username);
>>>>>>> Stashed changes
    Member findByEmail(String email);
}
