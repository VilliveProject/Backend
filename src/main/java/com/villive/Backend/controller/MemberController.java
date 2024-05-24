package com.villive.Backend.controller;

import com.villive.Backend.dto.*;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원", description = "회원가입, 로그인, 건물 코드 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입하는 API입니다.")
    @PostMapping("/join")
    public Long join(@RequestBody SignUpRequestDto requestDto) {

        return memberService.join(requestDto);

    }

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인하는 API입니다.")
    @PostMapping("/login")
    public String login(@RequestBody LogInRequestDto requestDto) {

        return memberService.login(requestDto);

    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/updatePassword")
    public ResponseEntity<UpdatePwdRequestDto> updatePassword(@RequestBody UpdatePwdRequestDto updatePwdRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(memberService.updatePassword(updatePwdRequestDto, customUserDetails.getMember()));
    }

    @Operation(summary = "닉네임 변경")
    @PutMapping("/updateNickname")
    public ResponseEntity<UpdateNicknameDto> updateNickname(@RequestBody UpdateNicknameDto updateNicknameDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(memberService.updateNickname(updateNicknameDto, customUserDetails.getMember()));
    }


    @Operation(summary = "닉네임 중복 확인", description = "닉네임이 존재하면 true, 없으면 false")
    @GetMapping("/isExist/{nickname}")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname){
        return ResponseEntity.ok(memberService.checkNickname(nickname));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/delMember")
    private ResponseEntity<MsgResponseDto> deleteMem(@RequestBody DeleteMemRequestDto deleteMemRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        memberService.deleteMem(deleteMemRequestDto, customUserDetails.getMember());
        return ResponseEntity.ok(new MsgResponseDto("회원탈퇴 완료", HttpStatus.OK.value()));
    }

    @Operation(summary = "건물 코드", description = "건물 코드와 호수 입력")
    @PostMapping("/addinfo")
    public ResponseEntity<BuildingCodeResponseDto> addHomeInfo(@RequestBody BuildingCodeRequestDto buildingCodeRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(memberService.addHomeInfo(buildingCodeRequestDto, customUserDetails.getMember()));
    }

}
