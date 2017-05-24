package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class CountryEntity implements Serializable {
    private int id_country;
    private String name;
    private String cover;
    private String image_1;
    private String image_2;
    private String image_3;

    public CountryEntity(String name, String cover) {
        this.name = name;
        this.cover = cover;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getImage_3() {
        return image_3;
    }

    public void setImage_3(String image_3) {
        this.image_3 = image_3;
    }
}
