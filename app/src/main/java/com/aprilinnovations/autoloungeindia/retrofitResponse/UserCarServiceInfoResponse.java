package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserCarServiceInfoResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -422297414049672224L;

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

        private CarDetails carDetails;
        private List<ServiceDetail> serviceDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -4805398393047779666L;

        public CarDetails getCarDetails() {
            return carDetails;
        }

        public void setCarDetails(CarDetails carDetails) {
            this.carDetails = carDetails;
        }

        public List<ServiceDetail> getServiceDetails() {
            return serviceDetails;
        }

        public void setServiceDetails(List<ServiceDetail> serviceDetails) {
            this.serviceDetails = serviceDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class CarDetails implements Serializable {

            private String userCarDataId;
            private String carBrand;
            private String carImage;
            private String carModel;
            private String carRegistrationNumber;
            private String dateOfPurchase;
            private String kilometers;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = -6406289730310440054L;

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

        public class ServiceDetail implements Serializable {

            private String userServiceDataId;
            private String serviceName;
            private String serviceType;
            private String serviceStatus;
            private String createTime;
            private String userServiceChatId;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = -2164873434164688510L;

            public String getUserServiceDataId() {
                return userServiceDataId;
            }

            public void setUserServiceDataId(String userServiceDataId) {
                this.userServiceDataId = userServiceDataId;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceStatus() {
                return serviceStatus;
            }

            public void setServiceStatus(String serviceStatus) {
                this.serviceStatus = serviceStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserServiceChatId() {
                return userServiceChatId;
            }

            public void setUserServiceChatId(String userServiceChatId) {
                this.userServiceChatId = userServiceChatId;
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