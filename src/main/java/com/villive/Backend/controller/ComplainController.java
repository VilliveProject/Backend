package com.villive.Backend.controller;

import com.villive.Backend.dto.ComplainRequestDto;
import com.villive.Backend.dto.ComplainResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.ComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/complain")
@RequiredArgsConstructor
@Tag(name="민원", description = "민원 처리 관련 API")
public class ComplainController {

    private final ComplainService complainService;

    @Operation(summary = "민원 등록")
    @PostMapping("/add")
    public ResponseEntity<ComplainResponseDto> createComplain(@RequestBody ComplainRequestDto complainRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        ComplainResponseDto responseDto = complainService.createComplain(complainRequestDto, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }
}
