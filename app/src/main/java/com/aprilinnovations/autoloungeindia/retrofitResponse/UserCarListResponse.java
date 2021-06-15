package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserCarListResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3567781545204640170L;

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

    public class Data implements Serializable {

        private List<UserCarDetail> userCarDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -6139175631074876269L;

        public List<UserCarDetail> getUserCarDetails() {
            return userCarDetails;
        }

        public void setUserCarDetails(List<UserCarDetail> userCarDetails) {
            this.userCarDetails = userCarDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class UserCarDetail implements Serializable {

            private String userCarDataId;
            private String carBrand;
            private String carImage;
            private String carModel;
            private String carRegistrationNumber;
            private String dateOfPurchase;
            private String kilometers;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = 756482243287117400L;

            public String getUserCarDataId() {
                return userCarDataId;
            }

            public void setUserCarDataId(String userCarDataId) {
                this.userCarDataId = userCarDataId;
            }

            public String getCarBrand() {
                return carBrand;
            }

            public void setCarBrand(String carBrand) {
                this.carBrand = carBrand;
            }

            public String getCarImage() {
                return carImage;
            }

            public void setCarImage(String carImage) {
                this.carImage = carImage;
            }

            public String getCarModel() {
                return carModel;
            }

            public void setCarModel(String carModel) {
                this.carModel = carModel;
            }

            public String getCarRegistrationNumber() {
                return carRegistrationNumber;
            }

            public void setCarRegistrationNumber(String carRegistrationNumber) {
                this.carRegistrationNumber = carRegistrationNumber;
            }

            public String getDateOfPurchase() {
                return dateOfPurchase;
            }

            public void setDateOfPurchase(String dateOfPurchase) {
                this.dateOfPurchase = dateOfPurchase;
            }

            public String getKilometers() {
                return kilometers;
            }

            public void setKilometers(String kilometers) {
                this.kilometers = kilometers;
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