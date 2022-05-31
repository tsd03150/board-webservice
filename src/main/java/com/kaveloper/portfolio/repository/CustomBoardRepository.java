package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface CustomBoardRepository {

    Page<Object[]> getBoardListWithSearchCondition(PageRequestDTO pageRequestDTO);

}
