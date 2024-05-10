package com.villive.Backend.service;

import com.villive.Backend.dto.LogInRequestDto;
import com.villive.Backend.dto.SignUpRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Long join(SignUpRequestDto requestDto);

    String login(LogInRequestDto requestDto);


}