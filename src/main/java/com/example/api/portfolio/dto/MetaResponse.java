package com.example.api.portfolio.dto;

public class MetaResponse {
    private String message;
    private int code;
    private String status;

    public MetaResponse(String message, int code, String status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
