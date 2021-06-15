package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmergencyContactsResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6135753543328865260L;

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public class Data implements Serializable
    {

        private List<EmergencyDataList> emergencyDataList = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -8864311230403563632L;

        public List<EmergencyDataList> getEmergencyDataList() {
            return emergencyDataList;
        }

        public void setEmergencyDataList(List<EmergencyDataList> emergencyDataList) {
            this.emergencyDataList = emergencyDataList;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class EmergencyDataList implements Serializable {

            private String emergencyDataId;
            private String contactName;
            private String phoneNumber;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = -5131618534141754932L;

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

            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }

            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }

        }

    }

}


