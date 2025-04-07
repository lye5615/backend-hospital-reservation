package com.example.hospitalreservation.dto;

public record ReservationResponse(Long reservationId, String message, int calculatedFee) {}
