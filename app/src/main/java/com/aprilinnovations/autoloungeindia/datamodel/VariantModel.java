package com.aprilinnovations.autoloungeindia.datamodel;


public class VariantModel {
    String carDataId;
    String variantName;

    public VariantModel(String str) {
        this.variantName = str;
    }

    public VariantModel() {

    }

    public String getVariantName() {
        return this.variantName;
    }

    public void setVariantName(String str) {
        this.variantName = str;
    }

    public String getCarDataId() {
        return this.carDataId;
    }

    public void setCarDataId(String str) {
        this.carDataId = str;
    }
}
