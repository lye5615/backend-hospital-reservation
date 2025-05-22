package com.example.hospitalreservation.service;

import com.example.hospitalreservation.exception.ReservationException;
import com.example.hospitalreservation.fee.FeeCalculator;
import com.example.hospitalreservation.model.Doctor;
import com.example.hospitalreservation.model.Patient;
import com.example.hospitalreservation.model.Reservation;
import com.example.hospitalreservation.repository.DoctorRepository;
import com.example.hospitalreservation.repository.PatientRepository;
import com.example.hospitalreservation.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final List<FeeCalculator> feeCalculators;

    public ReservationService(
            ReservationRepository reservationRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            List<FeeCalculator> feeCalculators) {
        this.reservationRepository = reservationRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.feeCalculators = feeCalculators;
    }

    // 모든 예약 리스트 조회
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // 예약 생성
    public Long createReservation(Long doctorId, Long patientId, String reservationTimeStr, String reason) {
        LocalTime desiredTime = LocalTime.parse(reservationTimeStr);
        if (desiredTime.isBefore(LocalTime.of(9, 0)) || desiredTime.isAfter(LocalTime.of(16, 0))) {
            throw new ReservationException("의사의 진료 가능 시간(09:00~17:00) 내에서만 예약할 수 있습니다.");
        }

        // 의사, 환자 조회
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ReservationException("존재하지 않는 의사입니다."));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ReservationException("존재하지 않는 환자입니다."));

        // 예약 중복 체크
        Optional<Reservation> existingReservation =
                reservationRepository.findByDoctorIdAndDesiredTime(doctorId, desiredTime);

        if (existingReservation.isPresent()) {
            throw new ReservationException("해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요.");
        }

        // 진료비 계산
        int calculatedFee = feeCalculators.stream()
                .filter(calculator -> calculator.supports(reason))
                .findFirst()
                .orElseThrow(() -> new ReservationException("지원되지 않는 진료 목적입니다."))
                .calculateFee();

        // 예약 생성 및 저장
        Reservation reservation = Reservation.of(doctor, patient, desiredTime, reason, calculatedFee);
        return reservationRepository.save(reservation).getId();
    }

    // 예약 취소
    public void cancelReservation(Long id, String cancelReason) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ReservationException("존재하지 않는 예약입니다.");
        }

        logger.info("예약 ID {} 취소됨. 취소 사유: {}", id, cancelReason);
        reservationRepository.deleteById(id);
    }

    // 진료비 미리 계산
    public int calculatedFee(String reason) {
        return feeCalculators.stream()
                .filter(calculator -> calculator.supports(reason))
                .findFirst()
                .orElseThrow(() -> new ReservationException("지원되지 않는 진료 목적입니다."))
                .calculateFee();
    }
}