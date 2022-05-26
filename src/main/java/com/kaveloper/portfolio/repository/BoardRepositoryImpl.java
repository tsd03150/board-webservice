package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BoardRepositoryImpl {

    private JPAQueryFactory query;

    // 스프링 DI를 통해서 EntityManage bean을 주입받음
    @Autowired
    public BoardRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

//    @Override
//    public Page<Object[]> getBoardListWithSearchCondition(PageRequestDTO requestDTO) {
//
//        Pageable pageable = requestDTO.getPageable(Sort.by("bid").descending());
//
//
//        return null;
//    }
}
