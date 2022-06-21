package com.kaveloper.portfolio.entity;

import lombok.*;

import javax.persistence.*;


// 우선 Board -> Member 단방향 관계
// 게시판은 글을 작성한 회원의 정보를 참조해야 하고
// 하나의 멤버는 여려개의 글을 쓸 수 있음으로
// 게시판과 회원은 Board 기준 다대일 단방향 관계
// 나중에 회원 정보에서 자신이 작성한 글 목록만 불러올 때
// 근데 이 방법도 그냥 게시글을 불러올 때 조건을 회원을 걸어준다면
// 굳이 양방향으로 안해도 될 것 같은데... 이것은 생각해보자

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String picture;

    // 로그인 한 웹 사이트 코드 (google, naver, ...)
   private String webCode;

    public Member updateProfile(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
