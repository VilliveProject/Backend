package com.villive.Backend.dto;

import com.villive.Backend.domain.Car;
import lombok.*;

@Getter
@Setter
@ToString
public class CarRequestDto {

    private String carNum;

    public Car toEntity(){
        return Car.builder()
                .carNum(carNum)
                .build();
    }
}
