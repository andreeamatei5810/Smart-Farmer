package com.example.mds;

import android.graphics.Bitmap;

public class ProductClass {
    String prodName;
    int prodPirce;
    String prodDescription;
    Bitmap image;



    public ProductClass(String prodName, int prodPirce, String prodDescription, Bitmap image) {
        this.prodPirce = prodPirce;
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

    public int getProdPirce() {
        return prodPirce;
    }

    public void setProdPirce(int prodPirce) {
        this.prodPirce = prodPirce;
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
