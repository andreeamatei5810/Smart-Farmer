package com.example.mds.model;

public class Rating {
    String userEmail;
    String farmerEmail;
    String content;
    double noStars;

    public Rating(String userEmail, String farmerEmail, String content, double noStars) {
        this.userEmail = userEmail;
        this.farmerEmail = farmerEmail;
        this.content = content;
        this.noStars = noStars;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFarmerEmail() {
        return farmerEmail;
    }

    public void setFarmerEmail(String farmerEmail) {
        this.farmerEmail = farmerEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getNoStars() {
        return noStars;
    }

    public void setNoStars(double noStars) {
        this.noStars = noStars;
    }
}
