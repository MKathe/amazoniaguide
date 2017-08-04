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
import android.widget.LinearLayout;
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

public class HistoryTravelAdapter extends LoaderAdapter<ReservationEntity> implements OnClickListListener {

    private Context context;
    private PlaceItem placeItem;

    public HistoryTravelAdapter(ArrayList<ReservationEntity> reservationEntities, Context context,
                                PlaceItem placeItem) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
        this.placeItem = placeItem;
    }

    public HistoryTravelAdapter(ArrayList<ReservationEntity> reservationEntities, Context context) {
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

        ((ViewHolder) holder).tvDate.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).lyDateTravel.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).tvDate.setText(reservationEntities.getDay());

        ((ViewHolder) holder).lyLocality.setVisibility(View.GONE);
        ((ViewHolder) holder).lyHour.setVisibility(View.GONE);
        ((ViewHolder) holder).tvName.setVisibility(View.GONE);
        ((ViewHolder) holder).lyName.setVisibility(View.GONE);
        ((ViewHolder) holder).tvLocality.setVisibility(View.GONE);
        ((ViewHolder) holder).tvHour.setVisibility(View.GONE);

        if (reservationEntities.is_confirm()) {
            ((ViewHolder) holder).imValidate.setImageDrawable(context.getDrawable(R.drawable.ic_valid));
        }
    }

    @Override
    public void onClick(int position) {
        ReservationEntity reservationEntity = getmItems().get(position);
        placeItem.clickItem(reservationEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.im_photo)
        ImageView imPhoto;
        @BindView(R.id.ly_image)
        LinearLayout lyImage;
        @BindView(R.id.title_travel)
        TextView titleTravel;
        @BindView(R.id.tv_travel)
        TextView tvTravel;
        @BindView(R.id.ly_name)
        LinearLayout lyName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ly_locality)
        LinearLayout lyLocality;
        @BindView(R.id.tv_locality)
        TextView tvLocality;
        @BindView(R.id.ly_hour)
        LinearLayout lyHour;
        @BindView(R.id.tv_hour)
        TextView tvHour;
        @BindView(R.id.title_travel_date)
        TextView titleTravelDate;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.ly_center)
        LinearLayout lyCenter;
        @BindView(R.id.im_validate)
        ImageView imValidate;
        @BindView(R.id.ly_date_travel)
        LinearLayout lyDateTravel;
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