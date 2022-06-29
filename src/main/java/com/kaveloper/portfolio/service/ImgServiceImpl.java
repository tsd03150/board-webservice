package com.kaveloper.portfolio.service;

import com.kaveloper.portfolio.dto.ImgDeleteRequestDTO;
import com.kaveloper.portfolio.entity.UploadFile;
import com.kaveloper.portfolio.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ImgServiceImpl implements ImgService{

    private final ImgRepository imgRepository;

    @Override
    @Transactional
    public void saveImg(List<UploadFile> uploadFiles) {
        imgRepository.saveAll(uploadFiles);
    }

    @Override
    public List<UploadFile> getImages(Long bid) {
        return imgRepository.findImageByBid(bid);
    }

    @Override
    @Transactional
    public void deleteImgFile(ImgDeleteRequestDTO deleteRequestDTO) {
        imgRepository.deleteUploadFileByBidAndStoreFileName(deleteRequestDTO.getBid(), deleteRequestDTO.getDeleteImgFileName());
    }
}
