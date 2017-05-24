package com.cerezaconsulting.pushayadmin.presentation.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by katherine on 23/03/17.
 */

public class ConfirmedDialog extends AlertDialog {


    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_name_detail_travel)
    TextView tvNameDetailTravel;
    @BindView(R.id.tv_descript_travel)
    TextView tvDescriptTravel;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.btn_validate_travel)
    Button btnValidateTravel;

    private ImageView im_close;
    private int num;
    //private CreateMenuContract.View mView;

    public ConfirmedDialog(Context context, Bundle bundle) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_details_travel, null);
        setView(view);

        //this.mView = mView;

        im_close = (ImageView) view.findViewById(R.id.im_close);

        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mView.orderSuccess();
                dismiss();
            }
        });

        bundle.getSerializable("travel");
        num = bundle.getInt("num");

        tvCount.setText(num);

    }

    @OnClick(R.id.btn_validate_travel)
    public void onViewClicked() {
    }
}

