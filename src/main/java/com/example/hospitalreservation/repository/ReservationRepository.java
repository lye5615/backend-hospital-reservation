package com.example.hospitalreservation.repository;

import com.example.hospitalreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDoctorId(Long doctorId);
    Optional<Reservation> findByDoctorIdAndDesiredTime(Long doctorId, LocalTime desiredTime);
}

