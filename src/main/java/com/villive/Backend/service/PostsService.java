package com.villive.Backend.service;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Posts;
import com.villive.Backend.domain.Member;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.CommentResponseDto;
import com.villive.Backend.dto.PostsRequestDto;
import com.villive.Backend.dto.PostsResponseDto;
import com.villive.Backend.jwt.JwtTokenProvider;
import com.villive.Backend.repository.MemberRepository;
import com.villive.Backend.repository.PostsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberService memberService;

    // 게시글 전체 조회
    public List<PostsResponseDto> getPostsList() {

        return postsRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(PostsResponseDto::new)
                .toList();
    }

    // 게시글 선택 조회
    public PostsResponseDto getPosts(Long id) {
        // 해당 id가 없을 경우
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        List<CommentResponseDto> commentList = new ArrayList<>();
        for(Comment comment : posts.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        // 해당 id가 있을 경우
        return new PostsResponseDto(posts, commentList);
    }

    // 게시글 작성
    public PostsResponseDto createPosts(PostsRequestDto requestDto, HttpServletRequest request) {

        // 토큰에서 사용자 ID 추출
        Member member = memberService.getMemberFromToken(request);


        Posts posts = new Posts(requestDto, member);
        Posts savedPosts = postsRepository.save(posts);

        return new PostsResponseDto(savedPosts);
    }

    // 게시글 수정
    @Transactional
    public PostsResponseDto updatePosts(Long id, PostsRequestDto postsRequestDto, HttpServletRequest request) {

        Member member = memberService.getMemberFromToken(request);

        Posts posts = postsRepository.findByIdAndMemberId(id, member.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자의 게시글을 찾을 수 없습니다.")
        );

        posts.update(postsRequestDto);

        return new PostsResponseDto(posts);
    }

    // 게시글 삭제
    public void deletePosts(Long id, HttpServletRequest request) {

        Member member = memberService.getMemberFromToken(request);

        Posts posts = postsRepository.findByIdAndMemberId(id, member.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자의 게시글을 찾을 수 없습니다.")
        );

        postsRepository.delete(posts);

    }


}
