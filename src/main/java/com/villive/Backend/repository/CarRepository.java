package com.villive.Backend.repository;

import com.villive.Backend.domain.Car;
import com.villive.Backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByMember(Member member);
}
