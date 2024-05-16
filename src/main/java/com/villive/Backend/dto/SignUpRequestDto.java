package com.villive.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {

    @JsonProperty("member_id")
    @Schema(description = "아이디")
    private String memberId;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "회원유형", example = "[USER, ADMIN]")
    private MemberRole role;

    /* DTO -> Entity */
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .name(name)
                .nickname(nickname)
//                .role(role)
                .build();
    }

}
