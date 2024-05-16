package com.villive.Backend.controller;

import com.villive.Backend.dto.BuildingCodeDto;
import com.villive.Backend.dto.LogInRequestDto;
import com.villive.Backend.dto.SignUpRequestDto;
import com.villive.Backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /*
    @PostMapping("/building-code")
    public ResponseEntity<BuildingCodeDto> addBuildingCode(@RequestBody BuildingCodeDto buildingCodeDto, HttpServletRequest request){

        return ResponseEntity.ok(memberService.addBuildingCode(buildingCodeDto, request));
    }

     */
}
