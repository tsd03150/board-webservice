package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> getRepliesByBoardOrderByRegDate(Board board);

    @Modifying
    @Query("delete from Reply r where r.rid=:rid and r.board.bid=:bid")
    void deleteReply(@Param("rid") Long rid, @Param("bid") Long bid);

}
