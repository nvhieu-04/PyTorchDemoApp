package org.pytorch.demo.models;

public class RoomRequest {
    private String nameRoom;
    private String idUser;

    public RoomRequest(String nameRoom, String idUser) {
        this.nameRoom = nameRoom;
        this.idUser = idUser;
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
}
