package com.kaveloper.portfolio.dto;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDeleteSaveRequestDTO {

    private Long bid;

    private Long rid;

}
