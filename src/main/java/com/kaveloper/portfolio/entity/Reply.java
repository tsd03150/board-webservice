package com.kaveloper.portfolio.entity;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = {""})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

// 댓글 엔티티는 회원에 대한 정보와 자신이 어디 댓글에 달려있는 지 알아야 하기 떄문에
// 게시판 정보도 참조해야 한다
// 하나의 회원을 여러개의 댓글을 쓸 수 있다
// 하나의 게시글에 여러갯의 댓글이 달릴 수 있다
// 우선 다대일 관계로 설정
// 나중에 게시글과 댓글의 연관관계를 좀 더 고민해볼 것
// 양방향으로 해야 될 것 같음
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}