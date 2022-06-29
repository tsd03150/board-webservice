package com.kaveloper.portfolio.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImgSaveRequestDTO {

    private Long bid;
    private List<MultipartFile> addImageFiles;
}
