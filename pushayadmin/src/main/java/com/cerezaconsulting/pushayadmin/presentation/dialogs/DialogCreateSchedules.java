package com.cerezaconsulting.pushayadmin.presentation.dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.data.entities.DayEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CreateSchedulesContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.CreateSchedulesPresenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by junior on 08/06/16.
 */
public class DialogCreateSchedules extends DialogFragment implements Validator.ValidationListener, TimePickerDialog.OnTimeSetListener, CreateSchedulesContract.View{


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
    @NotEmpty
    @BindView(R.id.et_hour)
    EditText etHour;
    @BindView(R.id.btn_get_in)
    Button btnGetIn;
    @NotEmpty
    @BindView(R.id.tv_locality)
    EditText tvLocality;

    private Context context;
    private Validator validator;
    private CreateSchedulesContract.Presenter mPresenter;
    private DestinyTravelEntity destinyTravelEntity;
    private SchedulesEntity schedulesEntity;
    private String daySelected;
    private ConfirmedDialog confirmedDialog;
    private TimePickerDialog timePickerDialog;

    public static DialogCreateSchedules newInstance(Bundle bundle) {
        DialogCreateSchedules fragment = new DialogCreateSchedules();
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPresenter = new CreateSchedulesPresenter(this, getContext());
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
        Calendar now = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true);
        validator = new Validator(this);
        validator.setValidationListener(this);
        tvDestiny.setText(destinyTravelEntity.getName());
        tvDaySelected.setText(daySelected);
    }
    @Subscribe()
    public void onUpdateList(String daySelected) {
    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.createSchedules(getSchedules());
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
        schedulesEntity.setLocality(tvLocality.getText().toString());
        return schedulesEntity;
    }


    @OnClick({R.id.et_hour, R.id.btn_get_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_hour:
                timePickerDialog.setTitle("ELija su hora de salida");
                timePickerDialog.show(getActivity().getFragmentManager(), "TimePickerDialog");
                break;
            case R.id.btn_get_in:
                validator.validate();
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String min;
        String time;
        if (minute < 10) {
            min = "0" + minute;
            time = hourOfDay + ":" + min;
        } else {
            time = hourOfDay + ":" + minute;
        }

        if(hourOfDay<12){
            time = time + " am";
        }else {
            time = time + " pm";
        }

        etHour.setText(time);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void sendCreateSchedules(SchedulesEntity schedulesEntity) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void createScheduleSuccesful(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        /*confirmedDialog = new ConfirmedDialog(getContext(), bundle);
        confirmedDialog.show();
        this.dismiss();*/
        EventBus.getDefault().post(daySelected);
        /*confirmedDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(daySelected);
            }
        });*/
        getActivity().finish();

    }
    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CreateSchedulesContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
    }

    @Override
    public void showMessage(String message) {
        ((BaseActivity) getActivity()).showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
