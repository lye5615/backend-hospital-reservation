package com.example.hospitalreservation.controller;

public class ResponseMessage {

    private String message;

    public ResponseMessage() {
        // 기본 생성자 (JSON 역직렬화를 위해 필요)
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
