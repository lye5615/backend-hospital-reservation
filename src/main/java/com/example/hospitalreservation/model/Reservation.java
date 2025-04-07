package com.example.hospitalreservation.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalTime desiredTime;
    private String reason;

    // TODO_w2 : 필요한 메서드가 있다면 작성해주세요.
    public Reservation(Long doctorId, Long patientId, LocalTime desiredTime, String reason) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.desiredTime = desiredTime;
        this.reason = reason;
    } //refactor: setter 대신 생성자

    public static Reservation of(Long doctorId, Long patientId, LocalTime desiredTime, String reason) {
        return new Reservation(doctorId, patientId, desiredTime, reason);
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
