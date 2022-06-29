package com.kaveloper.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImgDeleteRequestDTO {

    private Long bid;
    private String deleteImgFileName;

}
