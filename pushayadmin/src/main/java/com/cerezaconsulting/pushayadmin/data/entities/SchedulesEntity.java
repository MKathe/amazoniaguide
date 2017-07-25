package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class SchedulesEntity implements Serializable {

    private int id;
    private UserEntity guide;
    private DayEntity day;
    private String day_name;
    private DestinyTravelEntity destiny;
    private String destiny_name;
    private float price_normal;
    private int max_user;
    private String locality;
    private String hour;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice_normal() {
        return price_normal;
    }

    public UserEntity getGuide() {
        return guide;
    }

    public void setGuide(UserEntity guide) {
        this.guide = guide;
    }

    public DayEntity getDay() {
        return day;
    }

    public void setDay(DayEntity day) {
        this.day = day;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public DestinyTravelEntity getDestiny() {
        return destiny;
    }

    public void setDestiny(DestinyTravelEntity destiny) {
        this.destiny = destiny;
    }

    public String getDestiny_name() {
        return destiny_name;
    }

    public void setDestiny_name(String destiny_name) {
        this.destiny_name = destiny_name;
    }

    public float isPrice_normal() {
        return price_normal;
    }

    public String getPriceNormal(){
        return "S/. "+price_normal + "c/u.";
    }

    public void setPrice_normal(float price_normal) {
        this.price_normal = price_normal;
    }

    public int getMax_user() {
        return max_user;
    }

    public String getMaxUser(){
        return max_user + " Personas";
    }

    public void setMax_user(int max_user) {
        this.max_user = max_user;
    }
}
