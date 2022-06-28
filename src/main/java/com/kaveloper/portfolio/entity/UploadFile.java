package com.kaveloper.portfolio.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UploadFile extends BaseEntity{

    @Id
    private String storeFileName;

    private String uploadFileName;

    private Long bid;
}
