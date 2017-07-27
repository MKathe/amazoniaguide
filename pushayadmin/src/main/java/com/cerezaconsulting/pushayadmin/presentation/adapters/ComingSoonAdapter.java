package com.cerezaconsulting.pushayadmin.presentation.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.LoaderAdapter;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 24/05/17.
 */

public class ComingSoonAdapter extends LoaderAdapter<ReservationEntity> implements OnClickListListener {


    private Context context;
    private PlaceItem placeItem;

    public ComingSoonAdapter(ArrayList<ReservationEntity> reservationEntities, Context context,
                             PlaceItem placeItem) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
        this.placeItem = placeItem;
    }

    public ComingSoonAdapter(ArrayList<ReservationEntity> reservationEntities, Context context) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
    }

    public ArrayList<ReservationEntity> getItems() {
        return (ArrayList<ReservationEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationEntity reservationEntities = getmItems().get(position);
        ((ViewHolder) holder).tvName.setText(reservationEntities.getUserEntity().getFullName());
        ((ViewHolder) holder).tvTravel.setText(reservationEntities.getSchedules().getDestiny().getName());
        ((ViewHolder) holder).tvLocality.setText(reservationEntities.getSchedules().getLocality());

        ((ViewHolder) holder).titleTvDate.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).tvDate.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).tvDate.setText(reservationEntities.getDay());

        ((ViewHolder) holder).tvHour.setText(reservationEntities.getSchedules().getHour());
        if (reservationEntities.is_confirm()) {
            ((ViewHolder) holder).tvStatus.setText("VALIDADO");
            ((ViewHolder) holder).tvStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            ((ViewHolder) holder).tvStatus.setText("NO VALIDADO");
        }
    }

    @Override
    public void onClick(int position) {
        ReservationEntity reservationEntity = getmItems().get(position);
        placeItem.clickItem(reservationEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_travel)
        TextView tvTravel;
        @BindView(R.id.im_photo)
        ImageView imPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.title_tv_locality)
        TextView titleTvLocality;
        @BindView(R.id.tv_locality)
        TextView tvLocality;
        @BindView(R.id.title_tc_hour)
        TextView titleTcHour;
        @BindView(R.id.tv_hour)
        TextView tvHour;
        @BindView(R.id.title_tv_date)
        TextView titleTvDate;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.card_view)
        CardView cardView;
        private OnClickListListener onClickListListener;

        ViewHolder(View itemView, OnClickListListener onClickListListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onClickListListener = onClickListListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListListener.onClick(getAdapterPosition());
        }
    }

}