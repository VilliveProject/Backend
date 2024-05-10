package com.villive.Backend.service;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.dto.LogInRequestDto;
import com.villive.Backend.dto.SignUpRequestDto;
import com.villive.Backend.jwt.JwtTokenProvider;
import com.villive.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Long join(SignUpRequestDto requestDto) {
        if (memberRepository.findByMemberId(requestDto.getMemberId()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 유저아이디입니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.passwordEncode(passwordEncoder);

        // 사용자 역할에 따라 권한 설정
        if (requestDto.getRole().equals(MemberRole.ADMIN)) {
            member.addAdminAuthority(); // ADMIN 권한 추가
        } else {
            member.addUserAuthority(); // 기본적으로 USER 권한 추가
        }

        return member.getId();
    }

    @Transactional
    @Override
    public String login(LogInRequestDto requestDto) {
        Member member = memberRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입된 아이디가 아닙니다."));
        validateMatchedPassword(requestDto.getPassword(), member.getPassword());

        log.info("로그인 성공: {}", requestDto.getMemberId());

        return jwtTokenProvider.createToken(member.getMemberId(), member.getRole());
    }

    // 비밀번호 일치 여부 확인 메소드
    private void validateMatchedPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
