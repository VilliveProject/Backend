package com.villive.Backend.controller;

import com.villive.Backend.domain.CarStatus;
import com.villive.Backend.domain.MemberRole;
import com.villive.Backend.dto.CarRequestDto;
import com.villive.Backend.dto.CarResponseDto;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@Tag(name="차량", description = "차량 관련 API")
public class CarController {

    private final CarService carService;

    @Operation(summary = "차량 등록", description = "차량은 한 사람당 2대까지 등록할 수 있습니다.")
    @PostMapping("/add")
    public ResponseEntity<CarResponseDto> addCar(@RequestBody CarRequestDto carRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        CarResponseDto responseDto = carService.addCar(carRequestDto, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "차량 조회", description = "사용자 차량 조회")
    @GetMapping("/mycars")
    public ResponseEntity<List<CarResponseDto>> getMyCars(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<CarResponseDto> responseDtos = carService.getCarsByMember(customUserDetails.getMember());
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "모든 차량 조회(관리자)", description = "관리자만 모든 차량을 조회할 수 있습니다.")
    @GetMapping("/all")
    public ResponseEntity<List<CarResponseDto>> getAllCars(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (!customUserDetails.getMember().getRole().equals(MemberRole.ADMIN)) {
            throw new AccessDeniedException("모든 차량을 조회할 권한이 없습니다.");
        }
        List<CarResponseDto> responseDto = carService.getAllCars();
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "차량 상태 변경", description = "차량 상태는 관리자만 변경할 수 있습니다.")
    @PutMapping("/updateStatus/{carId}")
    public ResponseEntity<MsgResponseDto> updateCarStatus(@PathVariable Long carId, @RequestParam CarStatus status, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MsgResponseDto responseDto = carService.updateCarStatus(carId, status, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "차량 삭제", description = "차량 삭제는 소유자만 할 수 있습니다.")
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<MsgResponseDto> deleteCar(@PathVariable Long carId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MsgResponseDto responseDto = carService.deleteCar(carId, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }
}
