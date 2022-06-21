package com.kaveloper.portfolio.config.auth.dto;

import com.kaveloper.portfolio.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private Long mid;
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member entity) {
        this.mid = entity.getMid();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
    }
}
