package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ModelListResponse implements Serializable
{

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1645228564187174231L;

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

        private List<String> carModelDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -3811362771144089402L;

        public List<String> getCarModelDetails() {
            return carModelDetails;
        }

        public void setCarModelDetails(List<String> carModelDetails) {
            this.carModelDetails = carModelDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}