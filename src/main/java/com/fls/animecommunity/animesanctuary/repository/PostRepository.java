package com.fls.animecommunity.animesanctuary.repository;

import com.fls.animecommunity.animesanctuary.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
