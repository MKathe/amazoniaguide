package com.cerezaconsulting.pushay.presentation.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.LoaderAdapter;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushay.presentation.presenters.commons.SchedulesItem;
import com.cerezaconsulting.pushay.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 31/05/17.
 */

public class ListSchedulesAdapter extends LoaderAdapter<SchedulesEntity> implements OnClickListListener {


    private Context context;
    private SchedulesItem schedulesItem;

    public ListSchedulesAdapter(ArrayList<SchedulesEntity> schedulesEntities, Context context, SchedulesItem schedulesItem) {
        super(context);
        setItems(schedulesEntities);
        this.context = context;
        this.schedulesItem = schedulesItem;
    }

    public ListSchedulesAdapter(ArrayList<SchedulesEntity> schedulesEntities, Context context) {
        super(context);
        setItems(schedulesEntities);
        this.context = context;

    }

    public ArrayList<SchedulesEntity> getItems() {
        return (ArrayList<SchedulesEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guides, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        SchedulesEntity schedulesEntity = getItems().get(position);
        ((ViewHolder) holder).tvName.setText(schedulesEntity.getGuide().getFullName());
        //((ViewHolder) holder).tvDay.setText(schedulesEntity.getDay_name());
        ((ViewHolder) holder).tvPrice.setText(schedulesEntity.getPriceNormal());

        if (schedulesEntity.getGuide().getPicture() != null) {
            Glide.with(context)
                    .load(schedulesEntity.getGuide().getPicture())
                    .transform(new CircleTransform(context))
                    .into(((ViewHolder) holder).imageView);
        }
        ((ViewHolder) holder).rating.setRating(Float.valueOf(schedulesEntity.getGuide().getClasification()));
    }

    @Override
    public void onClick(int position) {

        SchedulesEntity schedulesEntity = getItems().get(position);
        schedulesItem.clickItem(schedulesEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.title_tv_price)
        TextView titleTvPrice;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.rating)
        RatingBar rating;
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
