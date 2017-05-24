package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class SchedulesEntity implements Serializable {
    private int id_schedules;
    private UserEntity userEntity;
    private DayEntity dayEntity;
    private DestinyTravelEntity destinyTravelEntity;
    private String price_normal;
    private int max_user;

    public SchedulesEntity(UserEntity userEntity, DayEntity dayEntity,
                           DestinyTravelEntity destinyTravelEntity, String price_normal, int max_user) {
        this.userEntity = userEntity;
        this.dayEntity = dayEntity;
        this.destinyTravelEntity = destinyTravelEntity;
        this.price_normal = price_normal;
        this.max_user = max_user;
    }

    public int getId_schedules() {
        return id_schedules;
    }

    public void setId_schedules(int id_schedules) {
        this.id_schedules = id_schedules;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public DayEntity getDayEntity() {
        return dayEntity;
    }

    public void setDayEntity(DayEntity dayEntity) {
        this.dayEntity = dayEntity;
    }

    public DestinyTravelEntity getDestinyTravelEntity() {
        return destinyTravelEntity;
    }

    public void setDestinyTravelEntity(DestinyTravelEntity destinyTravelEntity) {
        this.destinyTravelEntity = destinyTravelEntity;
    }

    public String getPrice_normal() {
        return price_normal;
    }

    public void setPrice_normal(String price_normal) {
        this.price_normal = price_normal;
    }

    public int getMax_user() {
        return max_user;
    }

    public void setMax_user(int max_user) {
        this.max_user = max_user;
    }
}
