package com.example.mds.model;

import android.graphics.Bitmap;

public class Product {
    String farmerId;
    int prodId;
    String prodName;
    int prodPrice;
    String prodDescription;
    Bitmap image;



    public Product(String prodName, int prodPrice, String prodDescription, Bitmap image) {
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.prodName = prodName;
        this.image = image;
    }

    public Product(String prodName, int prodPrice, Bitmap image) {
        this.prodPrice = prodPrice;
        this.prodName = prodName;
        this.image = image;
    }

    public Product(String farmerId, int prodId, String prodName, int prodPrice, String prodDescription, Bitmap image) {
        this.farmerId = farmerId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.image = image;
    }

    public Product(String farmerId, String prodName, int prodPrice, String prodDescription, Bitmap image) {
        this.farmerId = farmerId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.image = image;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }
}
