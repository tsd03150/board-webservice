package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ReplyDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyResponseDTO;
import com.kaveloper.portfolio.dto.ReplySaveRequestDTO;
import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyResponseDTO> getReplyList(Long bid);

    Long saveReply(ReplySaveRequestDTO replySaveRequestDTO);

    void deleteReply(ReplyDeleteSaveRequestDTO deleteSaveRequestDTO);

    default Reply dtoToEntity(ReplySaveRequestDTO replySaveRequestDTO) {

        Member member = Member.builder().mid(replySaveRequestDTO.getMid()).build();
        Board board = Board.builder().bid(replySaveRequestDTO.getBid()).build();


        Reply reply = Reply.builder()
                .text(replySaveRequestDTO.getText())
                .member(member)
                .board(board)
                .build();

        return reply;
    }

    default ReplyResponseDTO entityToDTO(Reply reply) {
        ReplyResponseDTO replyDTO = ReplyResponseDTO.builder()
                .rid(reply.getRid())
                .bid(reply.getBoard().getBid())
                .mid(reply.getMember().getMid())
                .text(reply.getText())
                .author(reply.getMember().getName())
                .regDate(reply.getRegDate())
                .modDate(reply.getModeDate())
                .build();
        return replyDTO;
    }


}
