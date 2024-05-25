package com.villive.Backend.dto;

import com.villive.Backend.domain.Car;
import com.villive.Backend.domain.CarStatus;
import com.villive.Backend.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private String carNum;
    private CarStatus status;
    private String address;

    public CarResponseDto(Car car) {
        this.id = car.getId();
        this.carNum = car.getCarNum();
        this.status = car.getStatus();
        this.address = car.getMember().getAddress();
    }
}

