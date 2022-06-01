package com.kaveloper.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardListResponseDTO {

    private Long bid;

    private String title;

    private String content;

    private String author;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    // 작성한 글이 24시간 이전일 경우 몇 시간 전으로 표시
    // 24시간 이후일 경우 연도-날짜 순으로 표시
    private long differentTime;

    private int count; // 조회 수

    private int replyCount; // 댓글 수

}
