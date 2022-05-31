package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {


}
