package org.pytorch.demo.models;

public class Room {
    private String name;
    private int id;
    private int image;
    private String totalPlant;

    public Room(String name, int id, int image, String totalPlant) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.totalPlant = totalPlant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTotalPlant() {
        return totalPlant;
    }

    public void setTotalPlant(String totalPlant) {
        this.totalPlant = totalPlant;
    }


}
