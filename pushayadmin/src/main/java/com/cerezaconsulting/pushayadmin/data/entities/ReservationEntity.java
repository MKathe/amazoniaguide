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
    private float pay_total;


    public float getPay_total() {
        return pay_total;
    }

    public void setPay_total(float pay_total) {
        this.pay_total = pay_total;
    }

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

    public String getDay(){
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es","ES"));
        try {
            tempDate = parseDateFromServer.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }

    public String numCoupons(){
       return String.valueOf(num_coupons);
    }

}
