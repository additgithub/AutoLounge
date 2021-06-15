package com.aprilinnovations.autoloungeindia.datamodel;

public class Car {
    String carBrand;
    int carImage;
    String carImageUrl;
    String carModel;
    String carName;
    String carNumber;
    String dateOfPurchase;
    String kmRun;
    Boolean layout;
    String userCarDataId;

    public Car(String str, String str2, String str3, int i) {
        this.carName = str;
        this.carNumber = str2;
        this.kmRun = str3;
        this.carImage = i;
    }

    public Car(String str, String str2, String str3, int i, Boolean bool) {
        this.carName = str;
        this.carNumber = str2;
        this.kmRun = str3;
        this.carImage = i;
        this.layout = bool;
    }

    public Car() {

    }


    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String str) {
        this.carName = str;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String str) {
        this.carNumber = str;
    }

    public String getKmRun() {
        return this.kmRun;
    }

    public void setKmRun(String str) {
        this.kmRun = str;
    }

    public String getCarImageUrl() {
        return this.carImageUrl;
    }

    public void setCarImageUrl(String str) {
        this.carImageUrl = str;
    }

    public int getCarImage() {
        return this.carImage;
    }

    public void setCarImage(int i) {
        this.carImage = i;
    }

    public Boolean getLayout() {
        return this.layout;
    }

    public void setLayout(Boolean bool) {
        this.layout = bool;
    }

    public String getUserCarDataId() {
        return this.userCarDataId;
    }

    public void setUserCarDataId(String str) {
        this.userCarDataId = str;
    }

    public String getCarBrand() {
        return this.carBrand;
    }

    public void setCarBrand(String str) {
        this.carBrand = str;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public void setCarModel(String str) {
        this.carModel = str;
    }

    public String getDateOfPurchase() {
        return this.dateOfPurchase;
    }

    public void setDateOfPurchase(String str) {
        this.dateOfPurchase = str;
    }
}