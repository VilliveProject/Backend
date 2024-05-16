package com.villive.Backend.service;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.BuildingCodeRequestDto;
import com.villive.Backend.dto.BuildingCodeResponseDto;
import com.villive.Backend.dto.LogInRequestDto;
import com.villive.Backend.dto.SignUpRequestDto;
import com.villive.Backend.jwt.JwtTokenProvider;
import com.villive.Backend.repository.CommentRepository;
import com.villive.Backend.repository.MemberRepository;
import com.villive.Backend.repository.PostsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Long join(SignUpRequestDto requestDto) {
        if (memberRepository.findByMemberId(requestDto.getMemberId()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 유저아이디입니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();

        return member.getId();
    }

    @Transactional
    public String login(LogInRequestDto requestDto) {
        Member member = memberRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입된 아이디가 아닙니다."));
        validateMatchedPassword(requestDto.getPassword(), member.getPassword());

        log.info("로그인 성공: {}", requestDto.getMemberId());

        return jwtTokenProvider.createToken(member.getMemberId(), member.getRole());
    }

    @Transactional
    public BuildingCodeResponseDto addHomeInfo(BuildingCodeRequestDto buildingCodeRequestDto, Member member){

        Optional<Member> existsAdminCode = memberRepository.findByRoleAndBuildingCode(MemberRole.ADMIN, buildingCodeRequestDto.getBuildingCode());

        if(existsAdminCode.isPresent()){
            member.addHomeInfo(buildingCodeRequestDto);
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("건물코드를 다시 확인하세요.");
        }

        return new BuildingCodeResponseDto(member);

    }





    // 비밀번호 일치 여부 확인 메소드
    private void validateMatchedPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }



    //
    Comment findByCommentAndMember(Long commentId, Member member){
        Comment comment;

        if(member.getRole().equals(MemberRole.ADMIN)) {
            comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 없습니다.")
            );
        } else {
            comment = commentRepository.findByIdAndMemberId(commentId, member.getId()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 작성한 댓글이 아닙니다.")
            );
        }

        return comment;
    }

    Posts findByPostsAndMember(Long id, Member member) {
        Posts posts;
        // ADMIN
        if(member.getRole().equals(MemberRole.ADMIN)) {
            posts = postsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
            );
        } else {
            posts = postsRepository.findByIdAndMemberId(id, member.getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글을 찾을 수 없거나 작성자만 삭제/수정할 수 있습니다.")
            );
        }
        return posts;
    }



}
