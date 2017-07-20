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

import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.LoaderAdapter;
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CitiesItem;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CountriesItem;
import com.cerezaconsulting.pushayadmin.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class CitiesAdapter extends LoaderAdapter<CityEntity> implements OnClickListListener {

    private Context context;
    private CitiesItem citiesItem;

    public CitiesAdapter(ArrayList<CityEntity> cityEntities, Context context, CitiesItem citiesItem) {
        super(context);
        setItems(cityEntities);
        this.context = context;
        this.citiesItem = citiesItem;
    }

    public CitiesAdapter(ArrayList<CityEntity> cityEntities, Context context) {
        super(context);
        setItems(cityEntities);
        this.context = context;

    }

    public ArrayList<CityEntity> getItems() {
        return (ArrayList<CityEntity>) getmItems();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityEntity cityEntity = getItems().get(position);
        ((ViewHolder) holder).tvNamePlace.setText(cityEntity.getName());
        if (cityEntity.getImage_1()!=null){
            Glide.with(context)
                    .load(cityEntity.getImage_1())
                    .transform(new CircleTransform(context))
                    .into(((ViewHolder) holder).ivPlaces);
        }else{
            ((ViewHolder) holder).ivPlaces.setImageDrawable(context.getDrawable(R.drawable.circular_symbol));
        }
    }

    @Override
    public void onClick(int position) {

        CityEntity cityEntity = getItems().get(position);
        citiesItem.clickItem(cityEntity);
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
