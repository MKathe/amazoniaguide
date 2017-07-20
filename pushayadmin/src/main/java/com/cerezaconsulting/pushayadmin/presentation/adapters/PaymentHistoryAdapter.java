package com.cerezaconsulting.pushayadmin.presentation.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PaymentHistoryAdapter extends LoaderAdapter<ReservationEntity> implements OnClickListListener {


    private Context context;
    private PlaceItem placeItem;

    public PaymentHistoryAdapter(ArrayList<ReservationEntity> reservationEntities, Context context,
                                 PlaceItem placeItem) {
        super(context);
        setItems(reservationEntities);
        this.context = context;
        this.placeItem = placeItem;
    }

    public PaymentHistoryAdapter(ArrayList<ReservationEntity> reservationEntities, Context context) {
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
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationEntity reservationEntities = getmItems().get(position);
        ((ViewHolder) holder).tvNamePlace.setText(reservationEntities.getSchedules().getDestiny_name());

        if (reservationEntities.isPayment_status()) {
            ((ViewHolder) holder).tvPayment.setText("PAGO REALIZADO");
            ((ViewHolder) holder).tvPayment.setTextColor(context.getResources().getColor(R.color.colorAccent, null));
        } else {
            ((ViewHolder) holder).tvPayment.setText("SU PAGO ESTÁ EN PROCESO");
        }
    }

    @Override
    public void onClick(int position) {
        ReservationEntity reservationEntity = getmItems().get(position);
        placeItem.clickItem(reservationEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name_place)
        TextView tvNamePlace;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.date_travel)
        TextView dateTravel;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_payment)
        TextView tvPayment;
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