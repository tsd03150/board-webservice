package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<UploadFile, String> {

}
