package com.cerezaconsulting.pushayadmin.presentation.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katherine on 23/03/17.
 */

public class ConfirmedRegisterDialog extends AlertDialog {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_acept)
    Button btnAcept;

    UserEntity userEntity;


    //private CreateMenuContract.View mView;

    public ConfirmedRegisterDialog(Context context, Bundle bundle) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_confirmed_register, null);
        ButterKnife.bind(this, view);
        setView(view);

         userEntity = (UserEntity) bundle.getSerializable("userEntity");

        tvName.setText(userEntity.getFirst_name());

        //this.mView = mView;

    }

    @OnClick(R.id.btn_acept)
    public void onViewClicked() {
        dismiss();
    }
}

