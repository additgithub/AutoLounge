package com.aprilinnovations.autoloungeindia.datamodel;



public class ChatModel {
    String id;
    String Message;
    String type;
    String time;
    String image;
    String hasImage="";


    public ChatModel(String id,String Message,String type,String time,String image,String hasImage) {
        this.id = id;
        this.Message = Message;
        this.type = type;
        this.time= time;
        this.image= image;
        this.hasImage= hasImage;

    }


    public String getHasImage() {
        return hasImage;
    }

    public void setHasImage(String hasImage) {
        this.hasImage = hasImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

/*
public class ChatModel {
    String chatDataId;
    String createTime;
    Boolean layout;
    String message;
    String preference;
    String profileImage;
    String userName;

    public ChatModel(String str, String str2) {
        this.message = str;
        this.preference = str2;
    }

    public String getChatDataId() {
        return this.chatDataId;
    }

    public void setChatDataId(String str) {
        this.chatDataId = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getPreference() {
        return this.preference;
    }

    public void setPreference(String str) {
        this.preference = str;
    }

    public String getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(String str) {
        this.profileImage = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public Boolean getLayout() {
        return this.layout;
    }

    public void setLayout(Boolean bool) {
        this.layout = bool;
    }
}
*/
