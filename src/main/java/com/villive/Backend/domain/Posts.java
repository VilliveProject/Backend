package com.villive.Backend.domain;

import com.villive.Backend.dto.PostsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 정보")
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String contents;

    @Schema(description = "작성자(닉네임)")
    private String writer;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    // posts : user = N : 1 다대일 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_no")
    private Member member;


    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    private List<PostsLike> PostsLikeList = new ArrayList<>();


    public Posts(PostsRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer =  member.getNickname();
        this.category = requestDto.getCategory();
        this.member = member;
    }


    // 게시글 수정
    public void update(PostsRequestDto postsRequestDto) {
        this.title = postsRequestDto.getTitle();
        this.contents = postsRequestDto.getContents();
    }


}
