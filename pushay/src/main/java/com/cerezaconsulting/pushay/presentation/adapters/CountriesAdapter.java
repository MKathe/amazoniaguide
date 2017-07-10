package com.cerezaconsulting.pushay.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.LoaderAdapter;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushay.presentation.presenters.commons.CountriesItem;
import com.cerezaconsulting.pushay.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class CountriesAdapter extends LoaderAdapter<CountryEntity> implements OnClickListListener {

    private Context context;
    private CountriesItem countriesItem;

    public CountriesAdapter(ArrayList<CountryEntity> countryEntities, Context context, CountriesItem countriesItem) {
        super(context);
        setItems(countryEntities);
        this.context = context;
        this.countriesItem = countriesItem;
    }

    public CountriesAdapter(ArrayList<CountryEntity> countryEntities, Context context) {
        super(context);
        setItems(countryEntities);
        this.context = context;

    }

    public ArrayList<CountryEntity> getItems() {
        return (ArrayList<CountryEntity>) getmItems();
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
        CountryEntity countryEntity = getItems().get(position);
        ((ViewHolder) holder).tvNamePlace.setText(countryEntity.getName());
        Glide.with(context)
                .load(countryEntity.getImage_1())
                .transform(new CircleTransform(context))
                .into(((ViewHolder) holder).ivPlaces);
    }

    @Override
    public void onClick(int position) {

        CountryEntity countryEntity = getItems().get(position);
        countriesItem.clickItem(countryEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_places)
        ImageView ivPlaces;
        @BindView(R.id.tv_name_place)
        TextView tvNamePlace;
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
