package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fls.animecommunity.animesanctuary.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
