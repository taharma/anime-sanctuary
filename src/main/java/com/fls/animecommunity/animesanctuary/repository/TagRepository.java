package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fls.animecommunity.animesanctuary.model.tag.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}