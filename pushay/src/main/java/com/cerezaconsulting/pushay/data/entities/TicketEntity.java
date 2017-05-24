package com.cerezaconsulting.pushay.data.entities;

import java.io.Serializable;

/**
 * Created by katherine on 15/05/17.
 */

public class TicketEntity implements Serializable{

    private String qr_code;
    private PlacesEntity placesEntity;
    private String guide;
    private String date;
    private String package_name;

}
