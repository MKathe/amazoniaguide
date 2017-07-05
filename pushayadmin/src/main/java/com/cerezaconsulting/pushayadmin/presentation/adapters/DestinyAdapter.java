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
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CitiesItem;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.DestinyItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class DestinyAdapter extends LoaderAdapter<DestinyTravelEntity> implements OnClickListListener {

    private Context context;
    private DestinyItem destinyItem;

    public DestinyAdapter(ArrayList<DestinyTravelEntity> destinyTravelEntities, Context context, DestinyItem destinyItem) {
        super(context);
        setItems(destinyTravelEntities);
        this.context = context;
        this.destinyItem = destinyItem;
    }

    public DestinyAdapter(ArrayList<DestinyTravelEntity> destinyTravelEntities, Context context) {
        super(context);
        setItems(destinyTravelEntities);
        this.context = context;

    }

    public ArrayList<DestinyTravelEntity> getItems() {
        return (ArrayList<DestinyTravelEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_places, parent, false);
        return new ViewHolder(root, this);
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        DestinyTravelEntity destinyTravelEntity = getItems().get(position);
        ((ViewHolder) holder).tvNamePlace.setText(destinyTravelEntity.getName());
    }

    @Override
    public void onClick(int position) {

        DestinyTravelEntity destinyTravelEntity = getItems().get(position);
        destinyItem.clickItem(destinyTravelEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_places)
        ImageView ivPlaces;
        @BindView(R.id.tv_name_place)
        TextView tvNamePlace;
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
