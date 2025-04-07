package com.example.hospitalreservation.fee;

import org.springframework.stereotype.Component;

@Component
public class ColdSymptomFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String reason){
        return reason.equals("감기 증상");
    }

    @Override
    public int calculateFee(){
        return 15000;
    }
}
