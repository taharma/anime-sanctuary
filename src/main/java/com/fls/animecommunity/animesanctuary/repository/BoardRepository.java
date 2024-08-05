package com.fls.animecommunity.animesanctuary.repository;

import com.fls.animecommunity.animesanctuary.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
