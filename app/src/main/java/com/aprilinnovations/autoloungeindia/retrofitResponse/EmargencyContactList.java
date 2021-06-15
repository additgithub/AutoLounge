package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmargencyContactList implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = 1489106482664524962L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable
    {

        @SerializedName("emergencyDataList")
        @Expose
        private List<EmergencyDataList> emergencyDataList = null;
        private final static long serialVersionUID = 9067967705488291914L;

        public List<EmergencyDataList> getEmergencyDataList() {
            return emergencyDataList;
        }

        public void setEmergencyDataList(List<EmergencyDataList> emergencyDataList) {
            this.emergencyDataList = emergencyDataList;
        }

        public class EmergencyDataList implements Serializable
        {

            @SerializedName("emergencyDataId")
            @Expose
            private String emergencyDataId;
            @SerializedName("contactName")
            @Expose
            private String contactName;
            @SerializedName("phoneNumber")
            @Expose
            private String phoneNumber;
            private final static long serialVersionUID = -3332964442458111642L;

            public String getEmergencyDataId() {
                return emergencyDataId;
            }

            public void setEmergencyDataId(String emergencyDataId) {
                this.emergencyDataId = emergencyDataId;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

        }

    }

}


