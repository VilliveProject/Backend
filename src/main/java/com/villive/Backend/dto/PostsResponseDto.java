package com.villive.Backend.dto;

import com.villive.Backend.domain.PostCategory;
import com.villive.Backend.domain.Posts;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String createDate, modifiedDate;
    private int postsLikeCnt;
    private boolean postsLikeCheck;
    private PostCategory category;
    private List<CommentResponseDto> commentList = new ArrayList<>();


    // 게시글 작성
    public PostsResponseDto(Posts posts){
        this.id = posts.getId();
        this.writer = posts.getWriter();
        this.title = posts.getTitle();
        this.contents = posts.getContents();
        this.createDate = posts.getCreatedDate();
        this.modifiedDate = posts.getModifiedDate();
        this.category = posts.getCategory();
    }

    // 게시글 전체/선택 조회, 수정
    public PostsResponseDto(Posts posts, List<CommentResponseDto> commentList, boolean postsLikeCheck){
        this.id = posts.getId();
        this.writer = posts.getWriter();
        this.title = posts.getTitle();
        this.contents = posts.getContents();
        this.postsLikeCnt = posts.getPostsLikeList().size();
        this.postsLikeCheck = postsLikeCheck;
        this.createDate = posts.getCreatedDate();
        this.modifiedDate = posts.getModifiedDate();
        this.category = posts.getCategory();
        this.commentList = commentList;

    }

}
