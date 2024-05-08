package com.villive.Backend.controller;

import com.villive.Backend.dto.MemberLogInRequestDto;
import com.villive.Backend.dto.MemberSignUpRequestDto;
import com.villive.Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Long join(@RequestBody MemberSignUpRequestDto requestDto) {

        return memberService.join(requestDto);

    }

    @PostMapping("/login")
    public String login(@RequestBody MemberLogInRequestDto requestDto) {

        return memberService.login(requestDto);

    }
}
