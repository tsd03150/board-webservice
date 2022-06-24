package com.kaveloper.portfolio.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardSaveRequestDTO {

    private Long bid;

    @NotNull
    @Size(min = 2, max = 30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;

    private String content;

    // 공지사항 여부
    private int nid;

    // 작성자 member id
    private Long mid;

    // 이미지 파일
    private List<MultipartFile> imageFiles;

}
