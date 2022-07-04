package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ReplyDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyResponseDTO;
import com.kaveloper.portfolio.dto.ReplySaveRequestDTO;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Override
    public List<ReplyResponseDTO> getReplyList(Long bid) {

        List<Reply> result = replyRepository.getRepliesByBoardOrderByRegDate(Board.builder().bid(bid).build());

        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveReply(ReplySaveRequestDTO replySaveRequestDTO) {
        Reply reply = dtoToEntity(replySaveRequestDTO);
        boardRepository.getBoardNoCount(replySaveRequestDTO.getBid());
        // 이 메서드를 추가한 이유는 게시판에 글을 누르면 detail페이지가 나오고 자동으로 조회수가 하나 증가한다
        // 여기서 만약 댓글을 작성하게 된다면 댓글 작성 후 다시 detail페이지가 나오기 때문에 조회수가 또 증가한다
        // 이러한 이유로 댓글을 작성했을 때는 조회수를 마이너스 한 번 시킨다

        return replyRepository.save(reply).getRid();
    }

    @Override
    @Transactional
    public void deleteReply(ReplyDeleteSaveRequestDTO deleteSaveRequestDTO) {
        // 우선 대댓글 부터 삭제
        replyCommentRepository.deleteReplyCommentByRid(deleteSaveRequestDTO.getRid());

        // 댓글 삭제
        replyRepository.deleteReplyByRid(deleteSaveRequestDTO.getRid());

        // 조회수 감소
        boardRepository.getBoardNoCount(deleteSaveRequestDTO.getBid());
    }
}
