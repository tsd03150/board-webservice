package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kaveloper.portfolio.entity.QBoard.*;
import static com.kaveloper.portfolio.entity.QMember.member;
import static com.kaveloper.portfolio.entity.QReply.reply;

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

        Pageable pageable = requestDTO.getPageable(Sort.by("bid").descending());

        JPAQuery<Tuple> tuple = query
                .select(board, member, reply.count())
                .from(board)
                .leftJoin(member).on(board.author.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .groupBy(board);
        // 하나의 board를 기준으로 댓글(reply)들이 몇 개 달려 있는지 count함수를 사용하기 위해 board를 그룹으로 묶어줘야 한다
        // 그래서 groupBy로 통해서 board를 그룹으로 묶어 준 뒤 where절을 쓸 수 없음으로 쿼리를 둘로 분리했다

        List<Tuple> result = tuple.where(checkTypeOrKeywordEq(requestDTO.getType(), requestDTO.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.bid.desc())
                .fetch();

        // content 에는 이제 board를 기준으로 조건 검색 및 페이징 처리가 끝난 투플들이다
        // 이를 반환할 때 List<Object[]> 타입으로 변환이 되어
        // 하나의 게시글을 기준으로 list[0]에는 board, list[1]에는 member, list[2]에는 reply(count)가 각각 저장된다
        return new PageImpl<>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, result.size());
    }

    // requestDTO에 type에 따라서
    // 1. 제목+내용(titleOrContent), 2. 제목(title), 3. 내용(content), 4. 작성자(author)로
    // 검색할 조건을 정할 수 있다
    private BooleanExpression checkTypeOrKeywordEq(String type, String keyword) {
        switch (type) {
            case "titleOrContent":
                return board.title.like("%" + keyword + "%")
                        .or(board.content.like("%" + keyword + "%"));
            case "title":
                return board.title.like("%" + keyword + "%");
            case "content":
                return board.content.like("%" + keyword + "%");
            case "author":
                return board.author.name.eq(keyword);
            default:
                return null;
        }
    }
}
