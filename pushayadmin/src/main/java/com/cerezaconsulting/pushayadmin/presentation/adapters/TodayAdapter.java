package com.cerezaconsulting.pushayadmin.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.LoaderAdapter;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 24/05/17.
 */

public class TodayAdapter extends LoaderAdapter<ReservationEntity> implements OnClickListListener {

    private Context context;
    private PlaceItem placeItem;

    public TodayAdapter(ArrayList<ReservationEntity> reservationEntities, Context context,
                        PlaceItem placeItem) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
        this.placeItem = placeItem;
    }

    public TodayAdapter(ArrayList<ReservationEntity> reservationEntities, Context context) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
    }

    public ArrayList<ReservationEntity> getItems() {
        return (ArrayList<ReservationEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId_reservation();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel, parent, false);
        return new ViewHolder(root, this);
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationEntity reservationEntities = getmItems().get(position);
        ((ViewHolder) holder).tvName.setText(reservationEntities.getUserEntity().getFullName());
        ((ViewHolder) holder).tvPackage.setText(reservationEntities.getScheludes().getDestinyTravelEntity().getDescription());
    }

    @Override
    public void onClick(int position) {
        ReservationEntity reservationEntity = getmItems().get(position);
        placeItem.clickItem(reservationEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.im_photo)
        ImageView imPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_package)
        TextView tvPackage;
        @BindView(R.id.tv_descript)
        TextView tvDescript;
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
