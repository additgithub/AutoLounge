package com.aprilinnovations.autoloungeindia.datamodel;

public class ContactsModel {
    String contactName;
    String emergencyDataId;
    String phoneNumber;

    public String getEmergencyDataId() {
        return this.emergencyDataId;
    }

    public void setEmergencyDataId(String str) {
        this.emergencyDataId = str;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String str) {
        this.contactName = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public ContactsModel(String str, String str2, String str3) {
        this.emergencyDataId = str;
        this.contactName = str2;
        this.phoneNumber = str3;
    }
}
