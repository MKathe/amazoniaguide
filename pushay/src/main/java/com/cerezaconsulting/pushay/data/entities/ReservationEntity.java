package com.cerezaconsulting.pushay.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 15/05/17.
 */

public class ReservationEntity implements Serializable{

        //RESERVATION ENTITY

    private int id_reservation;
    private SchedulesEntity scheludes;
    private String date;
    private UserEntity userEntity;
    private int num_coupons;
    private boolean is_confirm;
    private String status;

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public SchedulesEntity getScheludes() {
        return scheludes;
    }

    public void setScheludes(SchedulesEntity scheludes) {
        this.scheludes = scheludes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public int getNum_coupons() {
        return num_coupons;
    }

    public void setNum_coupons(int num_coupons) {
        this.num_coupons = num_coupons;
    }

    public boolean is_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(boolean is_confirm) {
        this.is_confirm = is_confirm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}