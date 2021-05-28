package com.example.mds;

import android.graphics.Bitmap;

public class ProductClass {
    String prodName;
    int prodPrice;
    String prodDescription;
    Bitmap image;



    public ProductClass(String prodName, int prodPrice, String prodDescription, Bitmap image) {
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.prodName = prodName;
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
}
