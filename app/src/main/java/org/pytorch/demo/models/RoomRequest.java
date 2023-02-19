package org.pytorch.demo.models;

public class RoomRequest {
    private String nameRoom;
    private String idUser;
    private String imageRoom;

    public RoomRequest(String nameRoom, String idUser, String imageRoom) {
        this.nameRoom = nameRoom;
        this.idUser = idUser;
        this.imageRoom = imageRoom;
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

    public String getImg() {
        return imageRoom;
    }

    public void setImg(String imageRoom) {
        this.imageRoom = imageRoom;
    }
}
