package com.example.hospitalreservation.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalTime desiredTime;
    private String reason;
    private int calculatedFee;

    public static Reservation of(Long doctorId, Long patientId, LocalTime desiredTime, String reason, int calculatedFee) {
        Reservation reservation = new Reservation();
        reservation.doctorId = doctorId;
        reservation.patientId = patientId;
        reservation.desiredTime = desiredTime;
        reservation.reason = reason;
        reservation.calculatedFee = calculatedFee;
        return reservation;
    }//정적 팩토리 메소드

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public String getReason() {return reason;}
}
