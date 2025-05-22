package com.example.hospitalreservation.controller;

import com.example.hospitalreservation.dto.CancelRequest;
import com.example.hospitalreservation.dto.ReservationRequest;
import com.example.hospitalreservation.dto.ReservationResponse;
import com.example.hospitalreservation.service.ReservationService;
import com.example.hospitalreservation.model.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        Long reservationId = reservationService.createReservation(request.doctorId(), request.patientId(), request.reservationTime(), request.reason());
        // 생성된 예약 객체를 조회하여 반환하거나, 필요에 따라 응답 메시지를 구성
        int calculatedFee = reservationService.calculatedFee(request.reason());

        return new ReservationResponse(reservationId,"예약이 완료되었습니다.",calculatedFee);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage cancelReservation(@PathVariable Long id, @RequestBody CancelRequest request) {
        reservationService.cancelReservation(id, request.cancelReason());
        return new ResponseMessage("예약이 취소되었습니다.");
    }
}
