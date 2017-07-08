package com.cerezaconsulting.pushay.presentation.presenters.commons;

import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;

/**
 * Created by katherine on 5/07/17.
 */

public interface SchedulesItem {

    void clickItem(SchedulesEntity schedulesEntity);

    void deleteItem(SchedulesEntity schedulesEntity, int position);
}
