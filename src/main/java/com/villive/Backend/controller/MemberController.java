package com.villive.Backend.controller;

import com.villive.Backend.dto.BuildingCodeRequestDto;
import com.villive.Backend.dto.BuildingCodeResponseDto;
import com.villive.Backend.dto.LogInRequestDto;
import com.villive.Backend.dto.SignUpRequestDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원", description = "회원가입, 로그인, 건물 코드 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회워")
    @PostMapping("/join")
    public Long join(@RequestBody SignUpRequestDto requestDto) {

        return memberService.join(requestDto);

    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public String login(@RequestBody LogInRequestDto requestDto) {

        return memberService.login(requestDto);

    }

    @Operation(summary = "건물 코드", description = "건물 코드와 호수 입력")
    @PatchMapping("/addinfo")
    public ResponseEntity<BuildingCodeResponseDto> addInfo(@RequestBody BuildingCodeRequestDto buildingCodeRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(memberService.addHomeInfo(buildingCodeRequestDto, customUserDetails.getMember()));
    }

}
