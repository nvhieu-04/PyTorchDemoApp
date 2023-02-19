package org.pytorch.demo.models;

public class PlantRequest {
    private String namePlant;
    private String nameRoom;
    private String healthStatus;
    private String userID;
    private String imagePlant;

    public PlantRequest(String namePlant, String nameRoom, String healthStatus, String userID, String imagePlant) {
        this.namePlant = namePlant;
        this.nameRoom = nameRoom;
        this.healthStatus = healthStatus;
        this.userID = userID;
        this.imagePlant = imagePlant;
    }

    public String getNamePlant() {
        return namePlant;
    }

    public void setNamePlant(String namePlant) {
        this.namePlant = namePlant;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImg() {
        return imagePlant;
    }

    public void setImg(String imagePlant) {
        this.imagePlant = imagePlant;
    }
}
