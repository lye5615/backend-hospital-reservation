package com.example.hospitalreservation.fee;

public interface FeeCaculator {
    /**
     * 해당 진료 목적(reason)을 지원하는지 판단
     */
    boolean supports(String reason);

    /**
     * 해당 진료 목적에 따른 진료비 계산
     */
    int calculateFee();
}
