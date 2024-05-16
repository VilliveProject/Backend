package com.villive.Backend.repository;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 ID로 조회
    Optional<Member> findByMemberId(String memberId);

    // 회원 고유번호(PK)로 조회
    Optional<Member> findById(Long id);

    Optional<Member> findByRoleAndBuildingCode(MemberRole role, String buildingCode);


}
