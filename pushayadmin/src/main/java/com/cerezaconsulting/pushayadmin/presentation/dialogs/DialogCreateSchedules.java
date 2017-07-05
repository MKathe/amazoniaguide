package com.cerezaconsulting.pushayadmin.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.DestinyContract;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by junior on 08/06/16.
 */
public class DialogCreateSchedules extends AlertDialog implements Validator.ValidationListener {


    @BindView(R.id.tv_destiny)
    TextView tvDestiny;
    @BindView(R.id.tv_day_selected)
    TextView tvDaySelected;
    @NotEmpty
    @BindView(R.id.tv_quantity)
    EditText tvQuantity;
    @NotEmpty
    @BindView(R.id.tv_price)
    EditText tvPrice;
    @BindView(R.id.btn_get_in)
    Button btnGetIn;

    private DestinyContract.View viewContract;
    private Validator validator;
    private DestinyTravelEntity destinyTravelEntity;
    private SchedulesEntity schedulesEntity;
    private String daySelected;

    public DialogCreateSchedules(Context context, final DestinyContract.View viewContract, Bundle bundle) {
        super(context);
        this.viewContract = checkNotNull(viewContract, "view cannot be null!");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_create_schedules, null);
        ButterKnife.bind(this,view);
        setView(view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        daySelected = bundle.getString("daySelected");
        destinyTravelEntity = (DestinyTravelEntity) bundle.getSerializable("destinyEntity");

        tvDestiny.setText(destinyTravelEntity.getName());
        tvDaySelected.setText(daySelected);


    }

    @Override
    public void onValidationSucceeded() {
        viewContract.sendCreateSchedules(getSchedules());
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
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private SchedulesEntity getSchedules() {
        schedulesEntity = new SchedulesEntity();
        schedulesEntity.setDay_name(daySelected);
        schedulesEntity.setDestiny_name(destinyTravelEntity.getName());
        schedulesEntity.setMax_user(Integer.parseInt(tvQuantity.getText().toString()));
        schedulesEntity.setPrice_normal(Float.valueOf(tvPrice.getText().toString()));
        return  schedulesEntity;
    }

    @OnClick(R.id.btn_get_in)
    public void onViewClicked() {
        validator.validate();

    }

}
