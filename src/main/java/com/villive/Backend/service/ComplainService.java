package com.villive.Backend.service;

import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.ComplainStatus;
import com.villive.Backend.domain.Member;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.ComplainRequestDto;
import com.villive.Backend.dto.ComplainResponseDto;
import com.villive.Backend.repository.ComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
