package org.pytorch.demo.models;

public class ImageResultResponse {
    private String msg;

    public ImageResultResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
