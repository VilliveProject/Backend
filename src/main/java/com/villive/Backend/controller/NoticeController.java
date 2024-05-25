package com.villive.Backend.controller;

import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.dto.NoticeRequestDto;
import com.villive.Backend.dto.NoticeResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name="공지사항", description = "공지사항 관련 API")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록", description = "공지사항은 관리자만 등록할 수 있습니다.")
    @PostMapping("/add")
    public ResponseEntity<NoticeResponseDto> createNotice(@RequestBody NoticeRequestDto noticeRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        NoticeResponseDto responseDto = noticeService.createNotice(noticeRequestDto, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "공지사항 조회", description = "공지사항을 조회하는 API 입니다.")
    @GetMapping("/")
    public ResponseEntity<List<NoticeResponseDto>> getAllNotices() {
        List<NoticeResponseDto> noticeList = noticeService.getAllNotices();
        return ResponseEntity.ok(noticeList);
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항은 관리자만 삭제할 수 있습니다.")
    @DeleteMapping("/delete/{noticeId}")
    public ResponseEntity<MsgResponseDto> deleteNotice(@PathVariable Long noticeId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MsgResponseDto responseDto = noticeService.deleteNotice(noticeId, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }

}
