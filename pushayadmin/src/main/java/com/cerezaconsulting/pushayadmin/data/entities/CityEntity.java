package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class CityEntity implements Serializable {
    private int id;
    private CountryEntity country;
    private String name;
    private String cover;
    private String image_1;
    private String image_2;
    private String image_3;

    public CityEntity(CountryEntity countryEntity, String name) {
        this.country = countryEntity;
        this.name = name;
    }

    public CountryEntity getCountryEntity() {
        return country;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.country = countryEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
