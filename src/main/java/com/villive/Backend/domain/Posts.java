package com.villive.Backend.domain;

import com.villive.Backend.dto.PostsRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private String writer;

    private Boolean isAnonymous; // 익명 여부

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    // posts : user = N : 1 다대일 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_no")
    private Member member;


    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comment = new ArrayList<>();

    public Posts(PostsRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer =  member.getName();
        this.category = requestDto.getCategory();
        this.isAnonymous = requestDto.getIsAnonymous();
        this.member = member;
    }

    public Posts(Long id, String title, String contents, String writer, Boolean isAnonymous, PostCategory category, Member member, List<Comment> comment) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
        this.category = category;
        this.member = member;
        this.comment = comment;
    }


}
