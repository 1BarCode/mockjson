package com.mycompany.mockjson.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {
    // "timestamp": "2023-08-10T12:34:56.789Z",
    // "status": 404,
    // "error": "Not Found",
    // "message": "The requested resource was not found",
    // "path": "/api/resource/12345"

    private Instant timestamp;
    private int status;
    private String path;
    private List<String> errors = new ArrayList<>();

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String message) {
        this.errors.add(message);
    }

}
