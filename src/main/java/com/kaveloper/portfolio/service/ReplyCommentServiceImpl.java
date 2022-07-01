package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ReplyCommentDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyCommentResponseDTO;
import com.kaveloper.portfolio.dto.ReplyCommentSaveRequestDTO;
import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Reply;
import com.kaveloper.portfolio.entity.ReplyComment;
import com.kaveloper.portfolio.repository.BoardRepository;
import com.kaveloper.portfolio.repository.ReplyCommentRepository;
import com.kaveloper.portfolio.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyCommentServiceImpl implements ReplyCommentService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Override
    public List<ReplyCommentResponseDTO> getReplyCommentList(Long bid) {

        List<Reply> replyResult = replyRepository.getRepliesByBoardOrderByRegDate(Board.builder().bid(bid).build());

        List<ReplyCommentResponseDTO> result = new ArrayList<>();

        for (Reply reply : replyResult) {
            List<ReplyComment> replyCommentResult = replyCommentRepository.getReplyCommentByBoardAndReplyOrderByRegDate(
                    Board.builder().bid(bid).build(),
                    Reply.builder().rid(reply.getRid()).build());
            for (ReplyComment replyComment : replyCommentResult) {
                result.add(entityToDTO(replyComment));
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Long saveReplyComment(ReplyCommentSaveRequestDTO replyCommentSaveRequestDTO) {
        ReplyComment replyComment = dtoToEntity(replyCommentSaveRequestDTO);
        boardRepository.getBoardNoCount(replyCommentSaveRequestDTO.getBid());
        // 이 메서드를 추가한 이유는 게시판에 글을 누르면 detail페이지가 나오고 자동으로 조회수가 하나 증가한다
        // 여기서 만약 댓글을 작성하게 된다면 댓글 작성 후 다시 detail페이지가 나오기 때문에 조회수가 또 증가한다
        // 이러한 이유로 댓글을 작성했을 때는 조회수를 마이너스 한 번 시킨다

        return replyCommentRepository.save(replyComment).getCid();
    }

    @Override
    @Transactional
    public void deleteReplyComment(ReplyCommentDeleteSaveRequestDTO replyCommentDTO) {
        replyCommentRepository.deleteReplyComment(replyCommentDTO.getCid(), replyCommentDTO.getRid(), replyCommentDTO.getBid());
        // 조회수 감소
        boardRepository.getBoardNoCount(replyCommentDTO.getBid());
    }

}
