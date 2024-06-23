package com.example.api.portfolio.dto;

public class ApiResponse<T> {
    private MetaResponse meta;
    private T data;

    public ApiResponse(MetaResponse meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    // Getters and Setters

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
