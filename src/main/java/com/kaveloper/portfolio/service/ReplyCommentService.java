package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ReplyCommentDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyCommentResponseDTO;
import com.kaveloper.portfolio.dto.ReplyCommentSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyDeleteSaveRequestDTO;
import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.entity.Reply;
import com.kaveloper.portfolio.entity.ReplyComment;

import java.util.List;

public interface ReplyCommentService {

    List<ReplyCommentResponseDTO> getReplyCommentList(Long bid);

    Long saveReplyComment(ReplyCommentSaveRequestDTO replyCommentSaveRequestDTO);

    void deleteReplyComment(ReplyCommentDeleteSaveRequestDTO replyCommentDeleteSaveRequestDTO);


    default ReplyComment dtoToEntity(ReplyCommentSaveRequestDTO replyCommentSaveRequestDTO) {

        Member member = Member.builder().mid(replyCommentSaveRequestDTO.getMid()).build();
        Board board = Board.builder().bid(replyCommentSaveRequestDTO.getBid()).build();
        Reply reply = Reply.builder().rid(replyCommentSaveRequestDTO.getRid()).build();

        ReplyComment replyComment = ReplyComment.builder()
                .text(replyCommentSaveRequestDTO.getText())
                .member(member)
                .board(board)
                .reply(reply)
                .build();

        return replyComment;
    }

    default ReplyCommentResponseDTO entityToDTO(ReplyComment replyComment) {
        ReplyCommentResponseDTO replyCommentDTO = ReplyCommentResponseDTO.builder()
                .cid(replyComment.getCid())
                .rid(replyComment.getReply().getRid())
                .bid(replyComment.getBoard().getBid())
                .mid(replyComment.getMember().getMid())
                .text(replyComment.getText())
                .author(replyComment.getMember().getName())
                .regDate(replyComment.getRegDate())
                .modDate(replyComment.getModeDate())
                .build();
        return replyCommentDTO;
    }

}
