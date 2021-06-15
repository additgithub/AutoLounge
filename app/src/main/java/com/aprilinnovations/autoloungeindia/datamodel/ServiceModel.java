package com.aprilinnovations.autoloungeindia.datamodel;

public class ServiceModel {
    String serviceDataId;
    String serviceName;
    String serviceType;

    public ServiceModel(String str) {
        this.serviceType = str;
    }

    public ServiceModel() {

    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String str) {
        this.serviceType = str;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public String getServiceDataId() {
        return this.serviceDataId;
    }

    public void setServiceDataId(String str) {
        this.serviceDataId = str;
    }
}
