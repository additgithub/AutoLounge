package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceNameListResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -882573779599938833L;

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

        private List<ServiceNameDetail> serviceNameDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = 8635336137435215317L;

        public List<ServiceNameDetail> getServiceNameDetails() {
            return serviceNameDetails;
        }

        public void setServiceNameDetails(List<ServiceNameDetail> serviceNameDetails) {
            this.serviceNameDetails = serviceNameDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
        public class ServiceNameDetail implements Serializable {

            private String serviceDataId;
            private String serviceType;
            private String serviceName;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = 3825133836534461676L;

            public String getServiceDataId() {
                return serviceDataId;
            }

            public void setServiceDataId(String serviceDataId) {
                this.serviceDataId = serviceDataId;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
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