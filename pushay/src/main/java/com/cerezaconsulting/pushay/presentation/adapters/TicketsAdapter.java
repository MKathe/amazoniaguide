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
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.presentation.adapters.listener.OnClickListListener;
import com.cerezaconsulting.pushay.presentation.presenters.commons.TicketItem;
import com.cerezaconsulting.pushay.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 31/05/17.
 */

public class TicketsAdapter extends LoaderAdapter<ReservationEntity> implements OnClickListListener {


    private Context context;
    private TicketItem ticketItem;

    public TicketsAdapter(ArrayList<ReservationEntity> ticketEntities, Context context, TicketItem ticketItem) {
        super(context);
        setItems(ticketEntities);
        this.context = context;
        this.ticketItem = ticketItem;
    }

    public TicketsAdapter(ArrayList<ReservationEntity> ticketEntities, Context context) {
        super(context);
        setItems(ticketEntities);
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
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tickets, parent, false);
        return new ViewHolder(root, this);
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationEntity ticketEntity = getItems().get(position);
        //((ViewHolder) holder).cardView.setBackgroundResource(ticketEntity.getSchedules().getDestiny().getImage_1());

        ((ViewHolder) holder).tvPackageName.setText(ticketEntity.getSchedules().getLocality());
        ((ViewHolder) holder).tvPackageDate.setText(ticketEntity.getSchedules().getHour());
        ((ViewHolder) holder).tvDate.setText(ticketEntity.getDay());
        ((ViewHolder) holder).tvNameCity.setText(ticketEntity.getSchedules().getDestiny().getName());
        ((ViewHolder) holder).tvDescriptCity.setText(ticketEntity.getSchedules().getDestiny().getCity().getName());

        if (ticketEntity.getSchedules().getGuide().getPicture() != null) {
            Glide.with(context)
                    .load(ticketEntity.getSchedules().getGuide().getPicture())
                    .transform(new CircleTransform(context))
                    .into(((ViewHolder) holder).imPerfil);
        }
        if (ticketEntity.getSchedules().getDestiny().getImage_1()!= null) {
            Glide.with(context)
                    .load(ticketEntity.getSchedules().getDestiny().getImage_1())
                    .into(((ViewHolder) holder).imBackground);
        }

    }

    @Override
    public void onClick(int position) {

        ReservationEntity ticketEntity = getItems().get(position);
        ticketItem.clickItem(ticketEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.im_perfil)
        ImageView imPerfil;
        @BindView(R.id.tv_name_city)
        TextView tvNameCity;
        @BindView(R.id.tv_descript_city)
        TextView tvDescriptCity;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_package_name)
        TextView tvPackageName;
        @BindView(R.id.tv_package_date)
        TextView tvPackageDate;
        @BindView(R.id.im_background)
        ImageView imBackground;
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
