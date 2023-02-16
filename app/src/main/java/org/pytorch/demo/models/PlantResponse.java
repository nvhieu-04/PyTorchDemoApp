package org.pytorch.demo.models;

public class PlantResponse {
    private String msg;

    public PlantResponse(String message) {
        this.msg = message;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}
