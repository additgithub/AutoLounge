package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarientListResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5164273445056104693L;

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

        private List<CarVariantDetail> carVariantDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -4178309047260624188L;

        public List<CarVariantDetail> getCarVariantDetails() {
            return carVariantDetails;
        }

        public void setCarVariantDetails(List<CarVariantDetail> carVariantDetails) {
            this.carVariantDetails = carVariantDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class CarVariantDetail implements Serializable
        {

            private String carDataId;
            private String carBrand;
            private String carModel;
            private String carVariant;
            private String carImage;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = -7685703652806477621L;

            public String getCarDataId() {
                return carDataId;
            }

            public void setCarDataId(String carDataId) {
                this.carDataId = carDataId;
            }

            public String getCarBrand() {
                return carBrand;
            }

            public void setCarBrand(String carBrand) {
                this.carBrand = carBrand;
            }

            public String getCarModel() {
                return carModel;
            }

            public void setCarModel(String carModel) {
                this.carModel = carModel;
            }

            public String getCarVariant() {
                return carVariant;
            }

            public void setCarVariant(String carVariant) {
                this.carVariant = carVariant;
            }

            public String getCarImage() {
                return carImage;
            }

            public void setCarImage(String carImage) {
                this.carImage = carImage;
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