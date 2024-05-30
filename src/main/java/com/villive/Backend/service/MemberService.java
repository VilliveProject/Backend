package com.villive.Backend.service;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.*;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.jwt.JwtTokenProvider;
import com.villive.Backend.repository.CommentRepository;
import com.villive.Backend.repository.MemberRepository;
import com.villive.Backend.repository.PostsRepository;
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
    public LoginResponseDto login(LogInRequestDto requestDto) {
        Member member = memberRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입된 아이디가 아닙니다."));
        validateMatchedPassword(requestDto.getPassword(), member.getPassword());

        log.info("로그인 성공: {}", requestDto.getMemberId());

        boolean hasBuildingCode = member.getBuildingCode() != null;

        String token = jwtTokenProvider.createToken(member.getMemberId(), member.getRole());
        return new LoginResponseDto(token, hasBuildingCode);
    }


    // 회원 탈퇴
    @Transactional
    public void deleteMem(DeleteMemRequestDto deleteMemRequestDto, Member member){

        if(!passwordEncoder.matches(deleteMemRequestDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteById(member.getId());

    }

    // 건물 코드 + 호수 등록
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
    
    // 닉네임 변경
    @Transactional
    public UpdateNicknameDto updateNickname(UpdateNicknameDto updateNicknameDto, Member member){

        member.setNickname(updateNicknameDto.getNickname());
        memberRepository.save(member);

        return updateNicknameDto;

    }

    // 닉네임 중복 확인
    public Boolean checkNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


    // 비밀번호 변경
    @Transactional
    public UpdatePwdRequestDto updatePassword(UpdatePwdRequestDto updatePwdRequestDto, Member member) {
        // 현재 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(updatePwdRequestDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        // 새 비밀번호 암호화
        String encodedNewPassword = passwordEncoder.encode(updatePwdRequestDto.getNewPassword());

        // 비밀번호 업데이트
        member.setPassword(encodedNewPassword);
        memberRepository.save(member);

        return updatePwdRequestDto;
    }



    // 비밀번호 일치 여부 확인 메소드
    private void validateMatchedPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public String getMemberName(CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return member.getName();
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
