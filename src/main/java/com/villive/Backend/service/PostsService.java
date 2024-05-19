package com.villive.Backend.service;

import com.villive.Backend.domain.*;
import com.villive.Backend.dto.*;
import com.villive.Backend.repository.PostsLikeRepository;
import com.villive.Backend.repository.PostsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberService memberService;
    private final PostsLikeRepository postsLikeRepository;

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> getPostsList() {

        return postsRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(PostsResponseDto::new)
                .toList();
    }

    // 카테고리별 게시글 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> getPostsListByCategory(PostCategory category) {
        return postsRepository.findByCategoryOrderByCreatedDateDesc(category).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 선택 조회
    public PostsResponseDto getPosts(Long id, Member member) {
        // 해당 id가 없을 경우
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        // 댓글
        List<CommentResponseDto> commentList = new ArrayList<>();
        for(Comment comment : posts.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        // 해당 id가 있을 경우
        return new PostsResponseDto(posts, commentList, checkPostsLike(posts.getId(), member));
    }

    // 게시글 작성
    public PostsResponseDto createPosts(PostsRequestDto requestDto, Member member) {

        Posts posts = new Posts(requestDto, member);
        Posts savedPosts = postsRepository.save(posts);

        return new PostsResponseDto(savedPosts);
    }

    // 게시글 수정
    @Transactional
    public PostsResponseDto updatePosts(Long id, PostsRequestDto postsRequestDto, Member member) {


        Posts posts = postsRepository.findByIdAndMemberId(id, member.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자의 게시글을 찾을 수 없습니다.")
        );

        posts.update(postsRequestDto);

        return new PostsResponseDto(posts);
    }

    // 게시글 삭제
    public MsgResponseDto deletePosts(Long id, Member member) {

        Posts posts = memberService.findByPostsAndMember(id, member);

        postsRepository.delete(posts);

        return new MsgResponseDto("게시글을 삭제했습니다.", HttpStatus.OK.value());
    }


    /*
        게시글 좋아요
     */

    // 게시글 좋아요 유/무 (false면 좋아요 취소, true면 좋아요)
    @Transactional
    public boolean checkPostsLike(Long postsId, Member member){
        return postsLikeRepository.existsByPostsIdAndMemberId(postsId, member.getId());
    }

    // 게시글 좋아요 개수
    @Transactional
    public MsgResponseDto savePostsLike(Long id, Member member) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        if(!checkPostsLike(id, member)){
            PostsLike postsLike = new PostsLike(posts, member);
            postsLikeRepository.save(postsLike);
            return new MsgResponseDto("게시글 좋아요", HttpStatus.OK.value());

        } else {
            postsLikeRepository.deleteByPostsIdAndMemberId(id, member.getId());
            return new MsgResponseDto("게시글 좋아요 취소", HttpStatus.OK.value());
        }

    }


}
