package com.villive.Backend.service;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.domain.Notice;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.dto.NoticeRequestDto;
import com.villive.Backend.dto.NoticeResponseDto;
import com.villive.Backend.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 등록
    @Transactional
    public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto, Member member){

        if (!member.getRole().equals(MemberRole.ADMIN)) {
            throw new AccessDeniedException("공지사항을 등록할 권한이 없습니다.");
        }

        Notice notice = new Notice(noticeRequestDto, member);
        Notice saveNotice = noticeRepository.save(notice);

        return new NoticeResponseDto(saveNotice);
    }

    // 공지사항 조회
    public List<NoticeResponseDto> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream().map(NoticeResponseDto::new).collect(Collectors.toList());
    }


    // 공지사항 삭제
    @Transactional
    public MsgResponseDto deleteNotice(Long noticeId, Member member) {
        if (!member.getRole().equals(MemberRole.ADMIN)) {
            throw new AccessDeniedException("공지사항을 삭제할 권한이 없습니다.");
        }

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다."));

        noticeRepository.delete(notice);

        return new MsgResponseDto("공지사항을 삭제했습니다.", HttpStatus.OK.value());
    }

}
