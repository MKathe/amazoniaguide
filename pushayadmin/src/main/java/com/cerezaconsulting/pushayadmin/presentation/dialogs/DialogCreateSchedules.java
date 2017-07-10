package com.cerezaconsulting.pushayadmin.presentation.dialogs;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.DestinyContract;
import com.cerezaconsulting.pushayadmin.presentation.fragments.CountriesFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by junior on 08/06/16.
 */
public class DialogCreateSchedules extends DialogFragment implements Validator.ValidationListener, TimePickerDialog.OnTimeSetListener {


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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_locality)
    TextView tvLocality;
    @BindView(R.id.et_hour)
    EditText etHour;
    @BindView(R.id.btn_get_in)
    Button btnGetIn;

    private Context context;
    private DestinyContract.View viewContract;
    private Validator validator;
    private DestinyTravelEntity destinyTravelEntity;
    private SchedulesEntity schedulesEntity;
    private String daySelected;

    private TimePickerDialog datePickerDialog;

    public static DialogCreateSchedules newInstance(Bundle bundle) {
        DialogCreateSchedules fragment = new DialogCreateSchedules();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        daySelected = getArguments().getString("daySelected");
        destinyTravelEntity = (DestinyTravelEntity) getArguments().getSerializable("destinyEntity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_create_schedules, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Calendar now = Calendar.getInstance();
        datePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH));*/
        validator = new Validator(this);
        validator.setValidationListener(this);
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
            String message = error.getCollatedErrorMessage(context);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private SchedulesEntity getSchedules() {
        schedulesEntity = new SchedulesEntity();
        schedulesEntity.setDay_name(daySelected);
        schedulesEntity.setDestiny_name(destinyTravelEntity.getName());
        schedulesEntity.setMax_user(Integer.parseInt(tvQuantity.getText().toString()));
        schedulesEntity.setPrice_normal(Float.valueOf(tvPrice.getText().toString()));
        schedulesEntity.setHour(etHour.getText().toString());
        //schedulesEntity.setLocality();
        return schedulesEntity;
    }


    @OnClick({R.id.et_hour, R.id.btn_get_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_hour:
                datePickerDialog.setTitle("Elija su día de viaje");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.btn_get_in:
                validator.validate();
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
        etHour.setText(time);
    }
}
