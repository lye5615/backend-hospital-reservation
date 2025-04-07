package com.example.hospitalreservation.fee;

import org.springframework.stereotype.Component;

@Component
public class NormalCheckupFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String reason) {
        return "일반 검진".equals(reason);
    }

    @Override
    public int calculateFee() {
        return 10000;
    }
}
