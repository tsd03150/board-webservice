package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.PageResultDTO;
import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.entity.Member;

import java.time.Duration;
import java.time.LocalDateTime;

public interface BoardService {

    // 게시글 등록
    void saveBoard(BoardSaveRequestDTO requestDto, Long mid);

    // 특정 게시글 조회
    BoardListResponseDTO getBoard(Long bid);

    // 게시글 조회 메서드 (전체 게시글 및 검색 조건, 페이징에 따른 조회 포함)
    PageResultDTO<BoardListResponseDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // dto -> entity로 변환하는 메서드
    default Board dtoToEntity(BoardSaveRequestDTO dto, Long mid) {
        Member member = Member.builder().mid(mid).build();

        Board board = Board.builder()
                .bid(dto.getBid())
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(member)
                .build();

        return board;
    }

    // entity -> dto로 변환하는 메서드
    default BoardListResponseDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardListResponseDTO boardListResponseDTO = BoardListResponseDTO.builder()
                .bid(board.getBid())
                .title(board.getTitle())
                .content(board.getContent())
                .author(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModeDate())
                .nid(board.getNid())
                .differentTime(Duration.between(board.getRegDate(), LocalDateTime.now()).getSeconds())
                .count(board.getCount())
                .replyCount(replyCount.intValue())
                .build();

        return boardListResponseDTO;
    }
}
