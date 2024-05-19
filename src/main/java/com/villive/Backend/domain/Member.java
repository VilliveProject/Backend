package com.villive.Backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.villive.Backend.dto.BuildingCodeRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Entity
@Builder // Lombok 빌더 패턴 구현을 위한 어노테이션
@Getter
@Setter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 매개 변수로 갖는 생성자 추가
public class Member {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;


    @Column(name = "member_id", nullable = false, unique = true)
    private String memberId; // 아이디

    private String nickname;

    private String name;


    @JsonIgnore // 비밀번호는 민감한 정보이므로, JSON으로 데이터 이동간에 숨김
    @Column(nullable = false)
    private String password;

    private String address; // 호수

    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Column(name = "building_code")
    private String buildingCode;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Posts> postsList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Complain> complainList = new ArrayList<>();


    public void addUserAuthority() {
        this.role = MemberRole.USER;
    }

    public void addAdminAuthority() {
        this.role = MemberRole.ADMIN;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }


    public void addHomeInfo(BuildingCodeRequestDto buildingCodeRequestDto) {
        this.buildingCode = buildingCodeRequestDto.getBuildingCode();
        this.address = buildingCodeRequestDto.getAddress();
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

}
