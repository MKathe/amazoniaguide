package com.cerezaconsulting.pushay.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.LoaderAdapter;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushay.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class CountriesAdapter extends LoaderAdapter<PlacesEntity> implements OnClickListListener {

    private Context context;
    private PlaceItem placeItem;

    public CountriesAdapter(ArrayList<PlacesEntity> placesEntities, Context context, PlaceItem placeItem) {
        super(context);
        setItems(placesEntities);
        this.context = context;
        this.placeItem = placeItem;
    }

    public CountriesAdapter(ArrayList<PlacesEntity> orderEntities, Context context) {
        super(context);
        setItems(orderEntities);
        this.context = context;

    }

    public ArrayList<PlacesEntity> getItems() {
        return (ArrayList<PlacesEntity>) getmItems();
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
        PlacesEntity placesEntity = getItems().get(position);
        ((ViewHolder) holder).tvNamePlace.setText(placesEntity.getCountry());
    }

    @Override
    public void onClick(int position) {

        PlacesEntity placesEntity = getItems().get(position);
        placeItem.clickItem(placesEntity);
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
