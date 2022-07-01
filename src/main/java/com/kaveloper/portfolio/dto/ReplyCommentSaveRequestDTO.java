package com.kaveloper.portfolio.dto;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyCommentSaveRequestDTO {

    private String text;

    private Long mid;

    private Long bid;

    private Long rid;

}
