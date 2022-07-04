package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Reply;
import com.kaveloper.portfolio.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {


    List<ReplyComment> getReplyCommentByBoardAndReplyOrderByRegDate(Board board, Reply reply);

    @Modifying
    @Query("delete from ReplyComment rc where rc.cid =:cid")
    void deleteReplyCommentByCid(@Param("cid") Long cid);

    @Modifying
    @Query("delete from ReplyComment rc where rc.reply.rid=:rid")
    void deleteReplyCommentByRid(@Param("rid") Long rid);

    @Modifying
    @Query("delete from ReplyComment rc where rc.board.bid=:bid")
    void deleteReplyCommentByBid(@Param("bid") Long bid);


}
