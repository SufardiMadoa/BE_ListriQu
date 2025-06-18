package org.listriqu.response;

public class ApiResponse {
    public String status;
    public Object data;

    public ApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
