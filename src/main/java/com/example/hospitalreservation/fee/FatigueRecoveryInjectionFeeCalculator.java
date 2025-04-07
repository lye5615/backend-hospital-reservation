package com.example.hospitalreservation.fee;

public class FatigueRecoveryInjectionFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String reason) {
        return "피로 회복 주사".equals(reason);
    }

    @Override
    public int calculateFee() {
        return 25000;
    }
}
