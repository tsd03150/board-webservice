package com.kaveloper.portfolio.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 전체 페이지
    private int totalPageSize;

    // 한 페이지 당 크기
    private int nowPageSize;

    // 현재 페이지 번호
    private int nowPageNum;

    // 시작 페이지 번호
    private int startPageNum;

    // 끝 페이지 번호
    private int endPageNum;

    // 이전 페이지 여부
    private boolean isPrevPage;

    // 다음 페이지 여부
    private boolean isNextPage;

    // 페이지 번호 목록 (게시글 밑에 <1, 2, 3, 4, 5, 6, 7, 8, 9, 10> 이것 처럼 번호를 몇 개 표시할지)
    private List<Integer> pageList;

    // Page<Object> 타입으로 result가 파라미터로 넘어온다
    // 이 result는 페이징 설정 및 검색 조건 처리가 끝난 데이터 투플이다
    // 결과적으로 이 DTO 생성자는 result 타입의 Page<Object>를 여기서 PageResultDTO<BoardListResponseDTO>로 매핑시키는 작업
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {

        // 함수로 정의된 fn에 의해서 getBoardListWithSearchCondition()의 조회 대상들인 List<T> 타입들이 List<DTO>로 매핑이 되어 dtoList에 저장된다
        // 그래서 이 dtoList에는 사용자가 요청한 조건들에 맞는 게시글 목록에 조회할 데이터들이 들어있다
        dtoList = result.stream().map(fn).collect(Collectors.toList());


    }



}
