package com.villive.Backend.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.villive.Backend.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isAnonymous; // 익명 여부

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private String createdDate;

    // comment : member = N : 1 다대일 단방향 연관관계
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // comment : posts = N : 1 다대일 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;


    public Comment(CommentRequestDto commentRequestDto, Posts posts, Member member) {
        this.content = commentRequestDto.getContent();
        this.writer = member.getName();
        this.isAnonymous = commentRequestDto.getIsAnonymous();
        this.posts = posts;
        this.member = member;
    }
}
