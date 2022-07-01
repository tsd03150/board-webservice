package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.dto.ReplyCommentDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyCommentSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplyDeleteSaveRequestDTO;
import com.kaveloper.portfolio.dto.ReplySaveRequestDTO;
import com.kaveloper.portfolio.service.ReplyCommentService;
import com.kaveloper.portfolio.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reply")
@RestController
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyCommentService replyCommentService;

    // 댓글 입력 처리
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ReplySaveRequestDTO replySaveRequestDTO) {

        Long rid = replyService.saveReply(replySaveRequestDTO);

        log.info("등록된 댓글 : {}", replySaveRequestDTO);

        return new ResponseEntity<>(rid, HttpStatus.OK);
    }

    // 댓글 삭제 처리
    @DeleteMapping
    public ResponseEntity<Long> delete(@RequestBody ReplyDeleteSaveRequestDTO deleteSaveRequestDTO) {
        replyService.deleteReply(deleteSaveRequestDTO);
        log.info("삭제해야 할 댓글 : {} ", deleteSaveRequestDTO);

        return new ResponseEntity<>(deleteSaveRequestDTO.getRid(), HttpStatus.OK);
    }

    // 대댓글 입력 처리
    @PostMapping("/add")
    public ResponseEntity<Long> createReplyComment(@RequestBody ReplyCommentSaveRequestDTO replyCommentSaveRequestDTO) {
        Long cid = replyCommentService.saveReplyComment(replyCommentSaveRequestDTO);

        log.info("등록된 대댓글 : {}", replyCommentSaveRequestDTO);

        return new ResponseEntity<>(cid, HttpStatus.OK);
    }

    // 대댓글 삭제 처리
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestBody ReplyCommentDeleteSaveRequestDTO replyCommentDeleteSaveRequestDTO) {
        replyCommentService.deleteReplyComment(replyCommentDeleteSaveRequestDTO);

        return new ResponseEntity<>(replyCommentDeleteSaveRequestDTO.getCid(), HttpStatus.OK);
    }

}
