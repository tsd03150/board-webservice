package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    // 한 게시글에 댓글이 없을 수도 있으니(null) left outer join
    @Query("select b, a, count(r) " +
            "from Board b left join b.author a " +
            "left outer join Reply r on r.board = b " +
            "where b.bid = :bid")
    Object getBoardByBid(@Param("bid") Long bid);

}
