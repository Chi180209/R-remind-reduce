package com.ccmcteam.ccmcteam.Model.Firebase;

public class Notification {
    private String notifiId;
    private String notifiName;
    private String notifiFinish;
    private String notifiImage;
    private String notiCategory;
    private String thisId;

    public Notification() {
    }

    public Notification(String notiCategory, String notifiFinish, String notifiId, String notifiImage, String notifiName, String thisId) {
        this.notifiId = notifiId;
        this.notifiName = notifiName;
        this.notifiFinish = notifiFinish;
        this.notifiImage = notifiImage;
        this.notiCategory = notiCategory;
        this.thisId = thisId;
    }

    public String getNotifiId() {
        return notifiId;
    }

    public void setNotifiId(String notifiId) {
        this.notifiId = notifiId;
    }

    public String getNotifiName() {
        return notifiName;
    }

    public void setNotifiName(String notifiName) {
        this.notifiName = notifiName;
    }

    public String getNotifiFinish() {
        return notifiFinish;
    }

    public void setNotifiFinish(String notifiFinish) {
        this.notifiFinish = notifiFinish;
    }

    public String getNotifiImage() {
        return notifiImage;
    }

    public void setNotifiImage(String notifiImage) {
        this.notifiImage = notifiImage;
    }

    public String getNotiCategory() {
        return notiCategory;
    }

    public void setNotiCategory(String notiCategory) {
        this.notiCategory = notiCategory;
    }

    public String getThisId() {
        return thisId;
    }

    public void setThisId(String thisId) {
        this.thisId = thisId;
    }
}
