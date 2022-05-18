package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.domain.Board;
import com.kaveloper.portfolio.dto.BoardSaveRequestDto;

public interface BoardService {

    Long save(BoardSaveRequestDto requestDto);

    // dto -> entity로 변환하는 메서드
    default Board dtoTOEntity(BoardSaveRequestDto dto) {
        Board board = Board.builder()
                .bid(dto.getBid())
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();
        return board;
    }
}
