package com.example.hospitalreservation.dto;

public record ReservationRequest(Long doctorId, Long patientId, String reservationTime, String reason) {
}
