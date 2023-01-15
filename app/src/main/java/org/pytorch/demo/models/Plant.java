package org.pytorch.demo.models;

public class Plant {
    private String name;
    private int idPlant;
    private int image;
    private String description;
    private int roomID;
    private String roomName;
    private String healthStatus;
    private String lastWatered;
    private int age;
    private String needCare;

    public Plant(String name, int idPlant, int image, String description, int roomID, String roomName, String healthStatus, String lastWatered, int age, String needCare) {
        this.name = name;
        this.idPlant = idPlant;
        this.image = image;
        this.description = description;
        this.roomID = roomID;
        this.roomName = roomName;
        this.healthStatus = healthStatus;
        this.lastWatered = lastWatered;
        this.age = age;
        this.needCare = needCare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(String lastWatered) {
        this.lastWatered = lastWatered;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNeedCare() {
        return needCare;
    }

    public void setNeedCare(String needCare) {
        this.needCare = needCare;
    }
}
