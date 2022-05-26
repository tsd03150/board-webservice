package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Object[]> getBoardListWithSearchCondition(PageRequestDTO pageRequestDTO);



}
