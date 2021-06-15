package com.aprilinnovations.autoloungeindia.datamodel;

public class NotificationDataModel {
    String compressedImage;
    String date;
    String description;
    String heading;
    int image;
    String notificationDataId;
    String notificationImage;

    public NotificationDataModel(String str, String str2, int i) {
        this.heading = str;
        this.date = str2;
        this.image = i;
    }

    public NotificationDataModel() {

    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String str) {
        this.heading = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int i) {
        this.image = i;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getNotificationDataId() {
        return this.notificationDataId;
    }

    public void setNotificationDataId(String str) {
        this.notificationDataId = str;
    }

    public String getCompressedImage() {
        return this.compressedImage;
    }

    public void setCompressedImage(String str) {
        this.compressedImage = str;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }
}
