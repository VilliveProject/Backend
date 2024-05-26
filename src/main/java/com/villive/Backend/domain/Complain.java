package com.villive.Backend.domain;

import com.villive.Backend.dto.ComplainRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Complain {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String title;

    private String contents;

    private String writer;

    @Enumerated(EnumType.STRING)
    private ComplainStatus status;

    @Enumerated(EnumType.STRING)
    private ComplainType type;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Complain(ComplainRequestDto complainRequestDto, Member member) {
        this.title = complainRequestDto.getTitle();
        this.contents = complainRequestDto.getContents();
        this.writer = member.getNickname();
        this.status = complainRequestDto.getStatus();
        this.type = complainRequestDto.getType();
        this.member = member;
    }


}
