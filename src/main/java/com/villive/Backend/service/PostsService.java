package com.villive.Backend.service;

import com.villive.Backend.domain.Posts;
import com.villive.Backend.domain.Member;
import com.villive.Backend.dto.PostsRequestDto;
import com.villive.Backend.dto.PostsResponseDto;
import com.villive.Backend.jwt.JwtTokenProvider;
import com.villive.Backend.repository.MemberRepository;
import com.villive.Backend.repository.PostsRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     *  게시글 작성
     */

    public PostsResponseDto createPosts(@RequestBody PostsRequestDto requestDto, HttpServletRequest request) {

        // 토큰에서 사용자 ID 추출
        String memberId = MemberIdFromToken(request);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with memberId: " + memberId));

        Posts posts = new Posts(requestDto, member);
        Posts savedPosts = postsRepository.save(posts);

        return new PostsResponseDto(savedPosts);
    }

    private String MemberIdFromToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            throw new IllegalArgumentException("Token is missing or invalid");
        }
        System.out.println("MemberIdFromToken 실행");
        return jwtTokenProvider.getUserPk(token);
    }


}
