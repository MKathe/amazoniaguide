package com.cerezaconsulting.pushay.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 24/05/17.
 */

public class DayEntity implements Serializable{
    private int id;
    private String name;
    private String name_esp;

    public DayEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_sp() {
        return name_esp;
    }

    public void setName_sp(String name_esp) {
        this.name_esp = name_esp;
    }
}
