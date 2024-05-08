package com.villive.Backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder // Lombok 빌더 패턴 구현을 위한 어노테이션
@NoArgsConstructor // Lombok을 사용하여 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 매개 변수로 갖는 생성자 추가
public class Member {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public void addUserAuthority() {
        this.role = MemberRole.USER;
    }

    public void addAdminAuthority() {
        this.role = MemberRole.ADMIN;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }


}
