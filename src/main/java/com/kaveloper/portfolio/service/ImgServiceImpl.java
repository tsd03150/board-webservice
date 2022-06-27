package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.entity.UploadFile;
import com.kaveloper.portfolio.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ImgServiceImpl implements ImgService{

    private final ImgRepository imgRepository;

    @Override
    public void saveImg(List<UploadFile> uploadFiles) {
        imgRepository.saveAll(uploadFiles);
    }
}
