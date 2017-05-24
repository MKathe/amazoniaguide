package com.cerezaconsulting.pushay.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 15/05/17.
 */

public class PlacesEntity implements Serializable {

    private int id;
    private String country;
    private String city;

    public PlacesEntity(int id, String country, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
