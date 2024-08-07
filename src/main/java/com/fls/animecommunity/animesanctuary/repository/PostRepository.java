package com.fls.animecommunity.animesanctuary.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.post.Post;
import com.fls.animecommunity.animesanctuary.model.post.dto.BoardResponseDto;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByModifiedAtDesc();
}
