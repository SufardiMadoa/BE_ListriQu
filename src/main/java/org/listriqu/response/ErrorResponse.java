package org.listriqu.response;


public class ErrorResponse {
    private String status;
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.status = "error";
        this.message = message;
    }

    // Getter Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
