package com.cerezaconsulting.pushayadmin.presentation.presenters.commons;

import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;

/**
 * Created by katherine on 5/07/17.
 */

public interface SchedulesItem {

    void clickItem(SchedulesEntity schedulesEntity);

    void deleteItem(SchedulesEntity schedulesEntity, int position);
}
