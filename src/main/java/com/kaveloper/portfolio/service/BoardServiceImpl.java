package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl {

    private final BoardRepository boardRepository;

//    @Override
//    @Transactional
//    public Long save(BoardSaveRequestDTO requestDto) {
//        Board board = dtoTOEntity(requestDto);
//        boardRepository.save(board);
//        return board.getBid();
//    }

//    @Override
//    public PageResultDTO<BoardListResponseDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
//
//        Function<Object[], BoardListResponseDTO> fn = (en -> entitiyToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
//
//        Page<Object[]> result = boardRepository.getBoardListWithSearchCondition(pageRequestDTO);
//
//        return new PageResultDTO<>(result, fn);
//    }
}
