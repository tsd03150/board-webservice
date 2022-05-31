package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.entity.Member;

public interface BoardService {

    // 멤버 등록 메서드
    Long save(BoardSaveRequestDTO requestDto);

    // 게시글 조회 메서드 (전체 게시글 및 검색 조건, 페이징에 따른 조회 포함)
    PageResultDTO<BoardListResponseDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // dto -> entity로 변환하는 메서드
    default Board dtoToEntity(BoardSaveRequestDTO dto) {
        Member member = Member.builder().name(dto.getAuthor()).build();

        Board board = Board.builder()
                .bid(dto.getBid())
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(member)
                .build();

        return board;
    }
}
