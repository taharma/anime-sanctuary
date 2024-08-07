package com.fls.animecommunity.animesanctuary.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.board.Board;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardResponseDto;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findAllByOrderByModifiedAtDesc();
}
