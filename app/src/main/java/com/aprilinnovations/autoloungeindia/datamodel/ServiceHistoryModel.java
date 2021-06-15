package com.aprilinnovations.autoloungeindia.datamodel;

public class ServiceHistoryModel {
    String date;
    String serviceName;
    String status;
    String type;
    String userServiceChatId;
    String userServiceDataId;

    public ServiceHistoryModel(String str, String str2, String str3) {
        this.type = str;
        this.date = str2;
        this.status = str3;
    }

    public ServiceHistoryModel() {

    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getUserServiceDataId() {
        return this.userServiceDataId;
    }

    public void setUserServiceDataId(String str) {
        this.userServiceDataId = str;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public String getUserServiceChatId() {
        return this.userServiceChatId;
    }

    public void setUserServiceChatId(String str) {
        this.userServiceChatId = str;
    }
}
