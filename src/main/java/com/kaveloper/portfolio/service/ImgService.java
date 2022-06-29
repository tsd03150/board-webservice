package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ImgDeleteRequestDTO;
import com.kaveloper.portfolio.entity.UploadFile;

import java.util.List;

public interface ImgService {

    // 이미지 저장 메서드
    void saveImg(List<UploadFile> uploadFiles);

    List<UploadFile> getImages(Long bid);

    void deleteImgFile(ImgDeleteRequestDTO deleteRequestDTO);
}
