package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.domain.Board;
import com.kaveloper.portfolio.dto.BoardSaveRequestDto;
import com.kaveloper.portfolio.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        Board board = dtoTOEntity(requestDto);
        boardRepository.save(board);
        return board.getBid();
    }
}
