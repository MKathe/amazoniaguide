package com.cerezaconsulting.pushayadmin.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ScheduleContract;
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
public class EditSchedulesDialog extends AlertDialog implements Validator.ValidationListener {


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
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ScheduleContract.View viewContract;
    private Validator validator;
    private SchedulesEntity schedulesEntity;


    public EditSchedulesDialog(Context context, final ScheduleContract.View viewContract, Bundle bundle) {
        super(context);
        this.viewContract = checkNotNull(viewContract, "view cannot be null!");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_create_schedules, null);
        ButterKnife.bind(this, view);
        setView(view);

        tvTitle.setText("EDITA TU HORARIO");
        btnGetIn.setText("EDITAR");
        validator = new Validator(this);
        validator.setValidationListener(this);

        schedulesEntity = (SchedulesEntity) bundle.getSerializable("schedulesEntity");
        tvDestiny.setText(schedulesEntity.getDestiny().getName());
        tvDaySelected.setText(schedulesEntity.getDay().getName());


    }

    @Override
    public void onValidationSucceeded() {
        viewContract.sendEditSchedules(getSchedules());
        dismiss();
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
        SchedulesEntity newSchedulesEntity = new SchedulesEntity();
        newSchedulesEntity.setId(schedulesEntity.getId());
        newSchedulesEntity.setDestiny_name(schedulesEntity.getDestiny().getName());
        newSchedulesEntity.setDay_name(schedulesEntity.getDay().getName());
        newSchedulesEntity.setMax_user(Integer.parseInt(tvQuantity.getText().toString()));
        newSchedulesEntity.setPrice_normal(Float.valueOf(tvPrice.getText().toString()));
        return newSchedulesEntity;
    }

    @OnClick(R.id.btn_get_in)
    public void onViewClicked() {
        validator.validate();

    }

}
