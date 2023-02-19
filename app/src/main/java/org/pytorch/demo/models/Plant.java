package org.pytorch.demo.models;

import com.google.gson.annotations.SerializedName;

public class Plant {
    @SerializedName("_id")
    private String _id;
    @SerializedName("namePlant")
    private String namePlant;
    @SerializedName("nameRoom")
    private String nameRoom;
    @SerializedName("healthStatus")
    private String healthStatus;
    @SerializedName("userID")
    private String userID;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("id")
    private String id;
    @SerializedName("__v")
    private String __v;
    @SerializedName("imagePlant")
    private String imagePlant;

    public Plant(String _id, String namePlant, String nameRoom, String healthStatus, String userID, String createdAt, String id, String __v, String imagePlant) {
        this._id = _id;
        this.namePlant = namePlant;
        this.nameRoom = nameRoom;
        this.healthStatus = healthStatus;
        this.userID = userID;
        this.createdAt = createdAt;
        this.id = id;
        this.__v = __v;
        this.imagePlant = imagePlant;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getImagePlant() {
        return imagePlant;
    }

    public void setImagePlant(String imagePlant) {
        this.imagePlant = imagePlant;
    }
}
