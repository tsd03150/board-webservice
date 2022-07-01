package com.kaveloper.portfolio.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyCommentResponseDTO {

    private Long cid;

    private Long bid;

    private Long rid;

    private String text;

    private String author;

    private Long mid;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
