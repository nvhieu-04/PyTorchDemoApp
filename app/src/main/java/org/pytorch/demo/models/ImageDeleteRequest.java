package org.pytorch.demo.models;

public class ImageDeleteRequest {
    private String filename;

    public ImageDeleteRequest(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
