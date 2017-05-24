package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;

import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DayEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ComingSoonContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.TodayContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

/**
 * Created by katherine on 24/05/17.
 */

public class ComingSoonPresenter implements ComingSoonContract.Presenter, PlaceItem{

    private final ComingSoonContract.View mView;
    private final SessionManager mSessionManager;

    public ComingSoonPresenter(ComingSoonContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }

    @Override
    public void loadOrdersFromPage(int page) {

    }

    @Override
    public void loadfromNextPage() {

    }

    @Override
    public void loadList() {
        ArrayList<ReservationEntity> list = new ArrayList<>();
        list.add(new ReservationEntity(new SchedulesEntity(
                new UserEntity("mk@gmail.com",
                        "kath", "caillahua", "123456789", "12345678","DNI",false,"foto",false,"qr",false,
                        "20/10/92","estrellitas"),new DayEntity(2,"martes"),
                new DestinyTravelEntity(new CityEntity(new CountryEntity("Ica","cover"),"Paracas"),"Paseo a Paracas","cover","viaje a paracas"),"20.00", 5),
                "20/10/992",new UserEntity("mk@gmail.com",
                "kath", "caillahua", "123456789", "12345678","DNI",false,"foto",false,"qr",false,
                "20/10/92","estrellitas"), 2));
        list.add(new ReservationEntity(new SchedulesEntity(
                new UserEntity("mk@gmail.com",
                        "kath", "caillahua", "123456789", "12345678","DNI",false,"foto",false,"qr",false,
                        "20/10/92","estrellitas"),new DayEntity(2,"martes"),
                new DestinyTravelEntity(new CityEntity(new CountryEntity("Ica","cover"),"Paracas"),"Paseo a Paracas","cover","viaje a paracas"),"20.00", 5),
                "20/10/992",new UserEntity("mk@gmail.com",
                "kath", "caillahua", "123456789", "12345678","DNI",false,"foto",false,"qr",false,
                "20/10/92","estrellitas"), 2));

        mView.getComingSoonList(list);
    }

    @Override
    public void start() {

    }

    @Override
    public void clickItem(ReservationEntity reservationEntity) {

    }

    @Override
    public void deleteItem(ReservationEntity reservationEntity, int position) {

    }
}
