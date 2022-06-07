package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.kaveloper.portfolio.entity.QBoard.*;
import static com.kaveloper.portfolio.entity.QMember.member;
import static com.kaveloper.portfolio.entity.QReply.reply;

@Log4j2
@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {

    private JPAQueryFactory query;

    // 스프링 DI를 통해서 EntityManage bean을 주입받음
    @Autowired
    public BoardRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Object[]> getBoardListWithSearchCondition(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("nid").descending()
                .and(Sort.by("bid").descending()));

        JPAQuery<Tuple> tuple = query
                .select(board, member, reply.count())
                .from(board)
                .leftJoin(member).on(board.author.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .groupBy(board);
        // 하나의 board를 기준으로 댓글(reply)들이 몇 개 달려 있는지 count함수를 사용하기 위해 board를 그룹으로 묶어줘야 한다
        // 그래서 groupBy로 통해서 board를 그룹으로 묶어 준 뒤 where절을 쓸 수 없음으로 쿼리를 둘로 분리했다
        List<Tuple> content = tuple.where(checkTypeOrKeywordEq(requestDTO.getType(), requestDTO.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.nid.desc(), board.bid.desc())
                .fetch();

        // fetchResults(), fetchCount() 메서드가 deprecated 되었기 때문에
        // 전체 게시글이 몇 개 인지를 count 하는 쿼리를 따로 작성해두었다
        Long total = query.select(board.count())
                .from(board)
                .where(checkTypeOrKeywordEq(requestDTO.getType(), requestDTO.getKeyword()))
                .fetchOne();
        // content에는 이제 board를 기준으로 조건 검색 및 페이징 처리가 끝난 투플들이다
        // 이를 반환할 때 List<Object[]> 타입으로 변환을 해줘야 한다
        // 하나의 게시글을 기준으로 Object[] 배열에는 [0]에 board, [1]에 member, [2]에 reply.count() 값이 저장된다

        return new PageImpl<>(content.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, total);
    }

    // requestDTO에 type에 따라서
    // 1. 제목+내용(titleOrContent), 2. 제목(title), 3. 내용(content), 4. 작성자(author)로
    // 검색할 조건을 정할 수 있다
    private BooleanExpression checkTypeOrKeywordEq(String type, String keyword) {
        if (!StringUtils.hasText(type)) {
            return null;
        } else if (type.equals("titleOrContent")) {
            return board.title.like("%" + keyword + "%")
                    .or(board.content.like("%" + keyword + "%"));
        } else if (type.equals("title")) {
            return board.title.like("%" + keyword + "%");
        } else if (type.equals("content")) {
            return board.content.like("%" + keyword + "%");
        } else {
            return board.author.name.eq(keyword);
        }
    }
}