package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.PageResultDTO;
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
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(BoardSaveRequestDTO requestDto) {
        Board board = dtoToEntity(requestDto);
        boardRepository.save(board);
        return board.getBid();
    }

    @Override
    public PageResultDTO<BoardListResponseDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[], BoardListResponseDTO> fn = (list -> entityToDTO((Board) list[0], (Member) list[1], (Long) list[2]));

        Page<Object[]> content = boardRepository.getBoardListWithSearchCondition(pageRequestDTO);

        return new PageResultDTO<>(content, fn);
    }
}
