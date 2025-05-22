package com.example.hospitalreservation.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime desiredTime;
    private String reason;
    private int calculatedFee;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    protected Reservation() {}

    public static Reservation of(Doctor doctor, Patient patient, LocalTime desiredTime, String reason, int calculatedFee) {
        Reservation reservation = new Reservation();
        reservation.doctor = doctor;
        reservation.patient = patient;
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



    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public String getReason() {return reason;}
}
