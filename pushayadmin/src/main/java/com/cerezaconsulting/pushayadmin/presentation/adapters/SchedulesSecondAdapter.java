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
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 26/06/17.
 */

public class SchedulesSecondAdapter extends RecyclerView.Adapter<SchedulesSecondAdapter.ViewHolder> implements OnClickListListener {


    private ArrayList<SchedulesEntity> list;
    private SchedulesEntity item;
    private ArrayList<Boolean> status;
    private Context context;
    private PlaceItem placeItem;

    public SchedulesSecondAdapter(ArrayList<SchedulesEntity> list, Context context, PlaceItem placeItem) {
        this.list = list;
        //setStatus();
        this.context = context;
        this.placeItem = placeItem;
    }

    public SchedulesSecondAdapter(SchedulesEntity item, Context context, PlaceItem placeItem) {
        this.list =  new ArrayList<>();
        this.item = item;
        this.context = context;
        this.placeItem = placeItem;
    }

    @Override
    public SchedulesSecondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_schedules, parent, false);
        return new ViewHolder(root, this);
    }

    @Override
    public void onBindViewHolder(SchedulesSecondAdapter.ViewHolder holder, int position) {
        SchedulesEntity  schedulesEntity =  list.get(position);

        if(schedulesEntity==null){
            return;
        }
        holder.tvNamePlace.setText(schedulesEntity.getDestiny_name());
        holder.tvPrice.setText(schedulesEntity.getPriceNormal());
        holder.tvQuantity.setText(schedulesEntity.getMaxUser());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setPlaceItem(SchedulesEntity schedulesEntity){

        this.list.clear();
        if (schedulesEntity != null) {
            this.list.add(schedulesEntity);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_places)
        ImageView ivPlaces;
        @BindView(R.id.tv_name_place)
        TextView tvNamePlace;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
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