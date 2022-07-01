package com.kaveloper.portfolio.entity;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = {"member", "board"})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReplyComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reply reply;

}
