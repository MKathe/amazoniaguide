package com.cerezaconsulting.pushayadmin.presentation.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cerezaconsulting.pushayadmin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katherine on 23/03/17.
 */

public class ConfirmedDialog extends AlertDialog {


    @BindView(R.id.im_close)
    ImageView imClose;
    private ImageView im_close;
    private int num;

    //private CreateMenuContract.View mView;

    public ConfirmedDialog(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_confirmed_travel, null);
        ButterKnife.bind(this, view);
        setView(view);

        //this.mView = mView;

    }
    @OnClick(R.id.im_close)
    public void onViewClicked() {
        dismiss();
    }
}

