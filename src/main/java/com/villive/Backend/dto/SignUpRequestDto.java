package com.villive.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
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
    private String memberId;
    private String password;
    private String name;
    private String address;
    private MemberRole role;

    /* DTO -> Entity */
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .name(name)
                .address(address)
                .role(role)
                .build();
    }

}
