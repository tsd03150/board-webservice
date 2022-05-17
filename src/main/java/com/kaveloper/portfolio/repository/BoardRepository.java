package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
