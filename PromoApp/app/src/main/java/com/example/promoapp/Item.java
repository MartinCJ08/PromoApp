package com.example.promoapp;

public class Item {
    private int imgPromo;
    private String sTiltle;
    private String sDetails;
    private String sVigencia;

    public int getImgPromo() {
        return imgPromo;
    }

    public String getsTiltle() {
        return sTiltle;
    }

    public String getsDetails() {
        return sDetails;
    }

    public String getsVigencia() {
        return sVigencia;
    }

    public Item(int img, String title, String det, String vig){
        imgPromo = img;
        sTiltle = title;
        sDetails = det;
        sVigencia = vig;
    }

}
