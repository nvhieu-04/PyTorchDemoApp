package org.pytorch.demo.models;

public class HistoryDisease {
    private String name;
    private String textPathogensPlant;
    private String textIdentificationPlant;
    private int imageDisease;
    private String description;
    private int idDisease;
    private String date;

    public HistoryDisease(String name, String textPathogensPlant, String textIdentificationPlant, int imageDisease, String description, int idDisease, String date) {
        this.name = name;
        this.textPathogensPlant = textPathogensPlant;
        this.textIdentificationPlant = textIdentificationPlant;
        this.imageDisease = imageDisease;
        this.description = description;
        this.idDisease = idDisease;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextPathogensPlant() {
        return textPathogensPlant;
    }

    public void setTextPathogensPlant(String textPathogensPlant) {
        this.textPathogensPlant = textPathogensPlant;
    }

    public String getTextIdentificationPlant() {
        return textIdentificationPlant;
    }

    public void setTextIdentificationPlant(String textIdentificationPlant) {
        this.textIdentificationPlant = textIdentificationPlant;
    }

    public int getImageDisease() {
        return imageDisease;
    }

    public void setImageDisease(int imageDisease) {
        this.imageDisease = imageDisease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdDisease() {
        return idDisease;
    }

    public void setIdDisease(int idDisease) {
        this.idDisease = idDisease;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
