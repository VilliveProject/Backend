package com.villive.Backend.domain;

import com.villive.Backend.dto.NoticeRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Notice {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String title;

    private String contents;

    private String writer;

    private String buildingName;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private String createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Notice(NoticeRequestDto noticeRequestDto, Member member) {
        this.title = noticeRequestDto.getTitle();
        this.contents = noticeRequestDto.getContents();
        this.writer = member.getNickname();
        this.member = member;
    }
}
