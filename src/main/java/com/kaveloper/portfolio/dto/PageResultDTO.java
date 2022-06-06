package com.kaveloper.portfolio.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 전체 페이지 번호
    private int totalPageNum;

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
    public PageResultDTO(Page<EN> content, Function<EN, DTO> fn) {

        // 함수로 정의된 fn에 의해서 getBoardListWithSearchCondition()의 조회 대상들인 List<T> 타입들이 List<DTO>로 매핑이 되어 dtoList에 저장된다
        // 그래서 이 dtoList에는 사용자가 요청한 조건들에 맞는 게시글 목록에 조회할 데이터들이 들어있다
        dtoList = content.stream().map(fn).collect(Collectors.toList());
        totalPageNum = content.getTotalPages();

        makePageList(content.getPageable());
    }

    // 이미 content 객체는 모든 페이징 설정 및 검색 조건 처리가 끝난 상태이다
    // 여기서는 이 content 객체를 기반으로 어떻게 페이지를 출력할 지 즉 사용자에게 보여줄 지 만드는 것이다
    // 예를 들면 list 뷰 페이지에서 게시글 하단에 <- 1 2 3 4 5 6 7 8 9 10->
    // 이런 식으로 어디서 부터 시작하고 어디서 부터 끝나는 지를 정해준다
    private void makePageList(Pageable pageable) {
        // 1을 더해 준 이유는 PageRequestDTO를 처음에 생성할 때 디폴트 값을 1로 해주었다
        // 그리고 나서 repository에서 PageRequestDTO에 getPageable()를 사용할 때 -1을 해줘서 0으로 만들어줬다
        // 그 이유는 DB에서는 인덱스가 0부터 처리되기 때문이다
        // 그래서 DB에서 데이터를 다 처리한 후 다시 들고올 때는 +1을 해주어야 한다
        this.nowPageNum = pageable.getPageNumber() + 1;
        this.nowPageSize = pageable.getPageSize();

        int tmpNum = (int) (Math.ceil(nowPageNum / 10.0)) * 10;

        startPageNum = tmpNum - 9;
        endPageNum = Math.min(totalPageNum, tmpNum);

        isPrevPage = startPageNum > 1;
        isNextPage = totalPageNum > tmpNum;

        pageList = IntStream.rangeClosed(startPageNum, endPageNum).boxed().collect(Collectors.toList());
    }

    /**
     * 위에서 설명한 대로 이 메서드는 페이지를 어디서부터(startPageNum) 어디까지(endPageNum) 출력할지에 대해서 정하는 메서드이다
     * 만약 전체 페이지 totalPageNum의 값이 24라고 가정을 한다면
     * <-  1  2  3  4  5  6  7  8  9 10 -> 1 페이지
     * <- 11 12 13 14 15 16 17 18 19 20 -> 2 페이지
     * <- 21 22 23 24 ->                   3 페이지
     *
     * 위의 순서대로 나누어 질 것이다
     * 1 페이지를 보면 맨 처음 startPageNum의 값이 1 부터 시작해서 endPageNum 값인 10 까지가 된다
     * startPageNum와 endPageNum를 구하는 코드를 보면
     * 현제 페이지 숫자가 1~10 사이의 무슨 숫자가 되든 tmpNum의 값은 10이 되고 여기서 -9를 하게 되면 1로 startPageNum이 된다
     * tmpNum 값이 10이고 totalPageNum이 24면 여기서 둘 중에 작은 값이 endPageNum이 됨으로 10이 endPageNum이 된다
     * 그래서 <-  1  2  3  4  5  6  7  8  9 10 -> 이런식으로 출력이된다
     *
     * 다음 번에 2 페이지를 보면 1페이지와 똑같은 방식으로 구현하면 tmpNum값이 20이여서 startPageNum 값이 11이 되고
     * tmpNum(20)값보다 totalPageNum(24)값이 더 큼으로 endPageNum 값은 20이 되어
     * <- 11 12 13 14 15 16 17 18 19 20 -> 이렇게 출력할 수 있다
     *
     * 마지막 3 페이지에서는 tmpNum 값이 30이 되어 startPageNum이 21이 되고
     * tmpNum(30)값이 totalPageNum(24)값 보다 큼으로 endPageNum은 더 작은 값이 24가 된다
     * 최종적으로 <-21 22 23 24 -> 24까지만 출력된다
     *
     * 이전 페이지 여부는 startPageNum 값은 1, 11, 21... 이런식으로 주어지기 때문에
     * 값이 1 일때 말고는 이전 페이지 여부가 존재한다
     * 다음 페이지 여부는 totalPageNum과 tmpNum을 비교하여서 totalPageNum 값이 클 경우
     * 다음 페이지 여부가 존재한다
     */
}