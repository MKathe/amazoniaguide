package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by katherine on 24/05/17.
 */

public class ReservationEntity implements Serializable {
    private int id;
    private SchedulesEntity schedules;
    private String date;
    private UserEntity user_client;
    private int num_coupons;
    private boolean is_confirm;
    private String status;
    private boolean payment_status;
    private int compare;


    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean payment_status) {
        this.payment_status = payment_status;
    }

    public UserEntity getUserEntity() {
        return user_client;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.user_client = userEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SchedulesEntity getSchedules() {
        return schedules;
    }

    public void setSchedules(SchedulesEntity schedules) {
        this.schedules = schedules;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int compareTo(){
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd'/'MMMM'/'yyyy", new Locale("es","ES"));

        try {
            tempDate = parseDateFromServer.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd'/'MMMM'/'yyyy", new Locale("es","ES"));
        String today = format.format(date);


        if(parseDateForShowDetail.format(tempDate).compareTo(today)==0){
            compare = 1;
        }else{
            if(parseDateForShowDetail.format(tempDate).compareTo(today)>0){
                compare = 3;
            }else{
                compare = 2;
            }

        }
        return  compare;
    }
    public boolean isEquals(){
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd'/'MMMM'/'yyyy", new Locale("es","ES"));

        try {
            tempDate = parseDateFromServer.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd'/'MMMM'/'yyyy", new Locale("es","ES"));
        String today = format.format(date);


        if(parseDateForShowDetail.format(tempDate).compareTo(today)==0){
            return true;
        }else{
            return false;
        }
    }


}
