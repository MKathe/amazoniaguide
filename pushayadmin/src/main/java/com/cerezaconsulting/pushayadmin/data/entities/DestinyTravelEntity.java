package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class DestinyTravelEntity implements Serializable {
    private int id_destiny_travel;
    private CityEntity city;
    private String name;
    private String cover;
    private String image_1;
    private String image_2;
    private String image_3;
    private String description;

    public DestinyTravelEntity(CityEntity city, String name, String cover, String description) {
        this.city = city;
        this.name = name;
        this.cover = cover;
        this.description = description;
    }

    public int getId_destiny_travel() {
        return id_destiny_travel;
    }

    public void setId_destiny_travel(int id_destiny_travel) {
        this.id_destiny_travel = id_destiny_travel;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
