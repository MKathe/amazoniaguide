package com.cerezaconsulting.pushay.data.entities;

import java.io.Serializable;

/**
 * Created by junior on 30/09/16.
 */
public class UserEntity implements Serializable {

    private int id;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName(){

        return "";
    }
}
