package com.cerezaconsulting.pushayadmin.presentation.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.presentation.activities.ValidateActivity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.NoValidatedTravelContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.NoValidatedTravelPresenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katherine on 23/03/17.
 */

public class TravelDetailsDialog extends AlertDialog implements Validator.ValidationListener{

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

    @NotEmpty(message = "Por favor ingresa tu código")
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.im_validate_code)
    ImageView imValidateCode;

    private ImageView im_close;
    private int num;
    private ReservationEntity reservationEntity;
    private Validator validator;
    private NoValidatedTravelContract.View mView;

    public TravelDetailsDialog(Context context, Bundle bundle, NoValidatedTravelContract.View mView) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_details_travel, null);
        ButterKnife.bind(this, view);
        this.mView = mView;
        setView(view);

        validator = new Validator(this);
        validator.setValidationListener(this);
        //this.mView = mView;

       /* im_close = (ImageView) view.findViewById(R.id.im_close);

        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mView.orderSuccess();
                dismiss();
            }
        });*/

        reservationEntity = (ReservationEntity) bundle.getSerializable("travel");
        tvUserName.setText(reservationEntity.getUserEntity().getFullName());
        tvDescriptTravel.setText(reservationEntity.getSchedules().getDestiny().getDescription());
        tvNameDetailTravel.setText(reservationEntity.getSchedules().getDestiny().getName());
        tvCount.setText(reservationEntity.numCoupons());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.et_code, R.id.im_validate_code,R.id.btn_validate_travel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_code:
                imValidateCode.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
                break;
            case R.id.im_validate_code:
                validator.validate();
                break;
            case R.id.btn_validate_travel:
                dismiss();
                Intent intent = new Intent(getContext(), ValidateActivity.class);
                getContext().startActivity(intent);
        }
    }

    @Override
    public void onValidationSucceeded() {
        mView.sendValidateTravelWithCode(etCode.getText().toString(), true);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

