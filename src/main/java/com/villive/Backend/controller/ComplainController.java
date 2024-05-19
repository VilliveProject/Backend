package com.villive.Backend.controller;

import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.ComplainStatus;
import com.villive.Backend.domain.Member;
import com.villive.Backend.dto.ComplainRequestDto;
import com.villive.Backend.dto.ComplainResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.ComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary="민원 조회")
    @GetMapping("/")
    public ResponseEntity<List<ComplainResponseDto>> getComplainList(){
        return ResponseEntity.ok(complainService.getComplainList());
    }

    @Operation(summary = "민원 상태 변경", description = "민원 상태는 관리자만 변경할 수 있습니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ComplainResponseDto> updateComplainStatus(@PathVariable Long id, @RequestParam ComplainStatus status) {
        ComplainResponseDto updatedComplain = complainService.updateComplainStatus(id, status);
        return ResponseEntity.ok(updatedComplain);
    }
}
