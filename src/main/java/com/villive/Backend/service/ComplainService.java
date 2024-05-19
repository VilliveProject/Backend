package com.villive.Backend.service;

import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.ComplainStatus;
import com.villive.Backend.domain.Member;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.ComplainRequestDto;
import com.villive.Backend.dto.ComplainResponseDto;
import com.villive.Backend.repository.ComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ComplainService {

    private final ComplainRepository complainRepository;

    // 민원 등록
    @Transactional
    public ComplainResponseDto createComplain(ComplainRequestDto complainRequestDto, Member member){

        Complain complain = new Complain(complainRequestDto, member);
        Complain saveComplain = complainRepository.save(complain);

        return new ComplainResponseDto(saveComplain);

    }

    // 민원 조회
    public List<ComplainResponseDto> getComplainList() {

        return complainRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(ComplainResponseDto::new)
                .toList();
    }

    // 민원 처리 현황 업데이트
    @Transactional
    public ComplainResponseDto updateComplainStatus(Long id, ComplainStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if (!isAdmin) {
            throw new IllegalStateException("관리자만 민원 상태를 변경할 수 있습니다.");
        }

        Complain complain = complainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 민원이 존재하지 않습니다. ID: " + id));

        complain.setStatus(status);
        Complain updatedComplain = complainRepository.save(complain);
        return new ComplainResponseDto(updatedComplain); // Complain 엔티티를 ComplainResponseDto로 변환하여 반환
    }

}
