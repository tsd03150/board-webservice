package com.kaveloper.portfolio.dto;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyCommentDeleteSaveRequestDTO {

    private Long cid;

    private Long bid;

}
