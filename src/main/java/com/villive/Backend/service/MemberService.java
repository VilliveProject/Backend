package com.villive.Backend.service;

import com.villive.Backend.dto.MemberLogInRequestDto;
import com.villive.Backend.dto.MemberSignUpRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Long join(MemberSignUpRequestDto requestDto);

    String login(MemberLogInRequestDto requestDto);


}