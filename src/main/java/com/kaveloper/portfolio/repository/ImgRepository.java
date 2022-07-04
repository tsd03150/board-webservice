package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImgRepository extends JpaRepository<UploadFile, String> {

    @Query("select u " +
            "from UploadFile u " +
            "where u.bid = :bid " +
            "order by u.regDate")
    List<UploadFile> findImageByBid(@Param("bid") Long bid);

    void deleteUploadFileByBidAndStoreFileName(@Param("bid") Long bid, @Param("storeFileName") String storeFileName);

    @Modifying
    @Query("delete from UploadFile u where u.bid=:bid")
    void deleteImgByBid(@Param("bid") Long bid);

}
