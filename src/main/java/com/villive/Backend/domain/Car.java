package com.villive.Backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String carNum;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Car(String carNum) {
        this.carNum = carNum;
    }

    public Car(String carNum, CarStatus status, Member member){
        this.carNum = carNum;
        this.status = status;
        this.member = member;
    }

}
