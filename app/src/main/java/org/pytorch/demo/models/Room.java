package org.pytorch.demo.models;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("_id")
    private String _id;
    @SerializedName("nameRoom")
    private String nameRoom;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("id")
    private String id;
    @SerializedName("__v")
    private String __v;
    @SerializedName("imageRoom")
    private String imageRoom;
    @SerializedName("floor")
    private String floor;

    public Room(String _id, String nameRoom, String idUser, String createdAt, String id, String __v, String imageRoom, String floor) {
        this._id = _id;
        this.nameRoom = nameRoom;
        this.idUser = idUser;
        this.createdAt = createdAt;
        this.id = id;
        this.__v = __v;
        this.imageRoom = imageRoom;
        this.floor = floor;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getImageRoom() {
        return imageRoom;
    }

    public void setImageRoom(String imageRoom) {
        this.imageRoom = imageRoom;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
