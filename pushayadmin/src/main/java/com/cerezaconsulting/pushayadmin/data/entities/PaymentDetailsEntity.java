package com.cerezaconsulting.pushayadmin.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 19/07/17.
 */

public class PaymentDetailsEntity implements Serializable {
    private String destiny_name;
    private String total_payment;
    private int total_quantity;
    private String date;
    private boolean payment_status;

    public String getDestiny_name() {
        return destiny_name;
    }

    public void setDestiny_name(String destiny_name) {
        this.destiny_name = destiny_name;
    }

    public String getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(String total_payment) {
        this.total_payment = total_payment;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean payment_status) {
        this.payment_status = payment_status;
    }
}
