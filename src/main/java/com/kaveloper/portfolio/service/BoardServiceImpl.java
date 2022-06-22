package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.PageResultDTO;
import com.kaveloper.portfolio.entity.Board;
import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void saveBoard(BoardSaveRequestDTO requestDto, Long mid) {
        Board board = dtoToEntity(requestDto, mid);
        boardRepository.save(board);
    }

    @Override
    public BoardListResponseDTO getBoard(Long bid) {
        Object result = boardRepository.getBoardByBid(bid);

        Object[] list = (Object[]) result;

        return entityToDTO((Board) list[0], (Member) list[1], (Long) list[2]);
    }

    @Override
    public PageResultDTO<BoardListResponseDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[], BoardListResponseDTO> fn = (list -> entityToDTO((Board) list[0], (Member) list[1], (Long) list[2]));

        Page<Object[]> content = boardRepository.getBoardListWithSearchCondition(pageRequestDTO);

        return new PageResultDTO<>(content, fn);
    }
}
