package com.example.hospitalreservation.service;

import com.example.hospitalreservation.exception.ReservationException;
import com.example.hospitalreservation.model.Reservation;
import com.example.hospitalreservation.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    //주입 받은 객체
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //모든 예약 리스트를 조회
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Long createReservation(Long doctorId, Long patientId, String reservationTimeStr, String reason) {
        //진료 가능 시간 체크
        LocalTime desiredTime = LocalTime.parse(reservationTimeStr);
        if(desiredTime.isBefore(LocalTime.of(9, 0))||desiredTime.isAfter(LocalTime.of(16, 0))){
            throw new ReservationException("의사의 진료 가능 시간(09:00~17:00) 내에서만 예약할 수 있습니다.");
        }

        Optional<Reservation> existingReservation = reservationRepository.findByDoctorIdAndReservationTime(doctorId, desiredTime);
        if(existingReservation.isPresent()){
            throw new ReservationException("해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요.");
        }


        Reservation reservation = Reservation.of(doctorId, patientId, desiredTime, reason);
        return reservationRepository.save(reservation).getId();
    }

    public void cancelReservation(Long id, String cancelReason) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isEmpty()){
            throw new ReservationException("존재하지 않는 예약입니다.");
        }

        System.out.println("예약 ID: "+id+"취소 (사유: "+cancelReason+")");
        logger.info("예약 ID {} 취소됨. 취소 사유: {}", id, cancelReason);

        reservationRepository.deleteById(id);
    }
}