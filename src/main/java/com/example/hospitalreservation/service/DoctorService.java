package com.example.hospitalreservation.service;

import com.example.hospitalreservation.model.Doctor;
import com.example.hospitalreservation.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctor() {
        // 예: ID가 1인 의사 반환
        return doctorRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("ID가 1인 의사가 없습니다."));
    }
}