package com.kaveloper.portfolio.config.auth.dto;

import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.entity.Role;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
public class SessionMember implements Serializable {

    private Long mid;
    private String name;
    private String email;
    private String picture;
    private Role role;

    public SessionMember(Member entity) {
        this.mid = entity.getMid();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
    }
}
