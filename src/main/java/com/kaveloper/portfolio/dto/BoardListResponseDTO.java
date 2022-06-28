package com.kaveloper.portfolio.dto;

import com.kaveloper.portfolio.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardListResponseDTO {

    private Long bid;

    private String title;

    private String content;

    private Long mid;

    private String author;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    // 작성한 글이 24시간 이전일 경우 시간으로 표시 ex) 15:37
    // 24시간 이후일 경우 연도-날짜 순으로 표시 ex) 2022/06/22
    private long differentTime;

    private int count; // 조회 수

    private int replyCount; // 댓글 수

    private int nid; // 공지 사항 여부


}
