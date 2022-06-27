package com.kaveloper.portfolio.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UploadFile extends BaseEntity{

    @Id
    private String storeFileName;
    private String uploadFileName;

}
