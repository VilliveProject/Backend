package com.villive.Backend.service;

import com.villive.Backend.domain.Car;
import com.villive.Backend.domain.CarStatus;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.dto.CarRequestDto;
import com.villive.Backend.dto.CarResponseDto;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public CarResponseDto addCar(CarRequestDto carRequestDto, Member member) {
        List<Car> cars = carRepository.findByMember(member);

        if (cars.size() >= 2) {
            throw new IllegalArgumentException("한 사람 당 최대 2대의 차량만 등록할 수 있습니다.");
        }

        Car car = new Car(carRequestDto.getCarNum(), CarStatus.입차, member);
        Car savedCar = carRepository.save(car);

        return new CarResponseDto(savedCar);
    }

    public List<CarResponseDto> getCarsByMember(Member member) {
        List<Car> cars = carRepository.findByMember(member);
        return cars.stream()
                .map(CarResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(CarResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MsgResponseDto updateCarStatus(Long carId, CarStatus status, Member member) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("차량을 찾을 수 없습니다."));

        if (!member.getRole().equals(MemberRole.ADMIN)) {
            throw new AccessDeniedException("차량 상태를 변경할 권한이 없습니다.");
        }

        car.setStatus(status);
        carRepository.save(car);

        return new MsgResponseDto("차량 상태가 성공적으로 업데이트되었습니다.", HttpStatus.OK.value());
    }

}
