package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class SchedulesEntity implements Serializable {

    private int id_schedules;
    private UserEntity guide;
    private DayEntity day;
    private String daytravel_name;
    private DestinyTravelEntity destiny;
    private String name;
    private float price_normal;
    private int max_user;

    public int getId_schedules() {
        return id_schedules;
    }

    public void setId_schedules(int id_schedules) {
        this.id_schedules = id_schedules;
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

    public String getDaytravel_name() {
        return daytravel_name;
    }

    public void setDaytravel_name(String daytravel_name) {
        this.daytravel_name = daytravel_name;
    }

    public DestinyTravelEntity getDestiny() {
        return destiny;
    }

    public void setDestiny(DestinyTravelEntity destiny) {
        this.destiny = destiny;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float isPrice_normal() {
        return price_normal;
    }

    public String getPriceNormal(){
        return "S/. "+price_normal;
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
