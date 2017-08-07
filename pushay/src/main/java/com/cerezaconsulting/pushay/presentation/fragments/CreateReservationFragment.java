package com.cerezaconsulting.pushay.presentation.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.activities.TicketsActivity;
import com.cerezaconsulting.pushay.presentation.contracts.CreateReservationContract;
import com.cerezaconsulting.pushay.presentation.dialogs.ConfirmedDialog;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 24/07/17.
 */

public class CreateReservationFragment extends BaseFragment implements CreateReservationContract.View, Validator.ValidationListener {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_destiny_name)
    TextView tvDestinyName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_day_detail)
    TextView tvDayDetail;
    @BindView(R.id.tv_hour)
    TextView tvHour;

    @NotEmpty
    @BindView(R.id.et_quantity)
    EditText etQuantity;

    @BindView(R.id.title_locality)
    TextView titleLocality;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.et_expiration)
    EditText etExpiration;
    @BindView(R.id.et_ccv)
    EditText etCcv;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    Unbinder unbinder;

    private SessionManager mSessionManager;
    private SchedulesEntity schedulesEntity;
    private String date;
    private ProgressDialogCustom mProgressDialogCustom;
    private Validator validator;
    private CreateReservationContract.Presenter mPresenter;

    public CreateReservationFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
    }

    public static CreateReservationFragment newInstance(Bundle bundle) {
        CreateReservationFragment fragment = new CreateReservationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getContext());
        schedulesEntity = (SchedulesEntity) getArguments().getSerializable("schedulesEntity");
        date = getArguments().getString("date");
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buy, container, false);

        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Realizando compra...");

        tvName.setText(mSessionManager.getUserEntity().getFullName());
        tvDayDetail.setText(getDay());

        tvDestinyName.setText(schedulesEntity.getDestiny().getName());
        tvHour.setText(schedulesEntity.getHour());
        tvCityName.setText(schedulesEntity.getDestiny().getCity().getName());
        tvDay.setText(schedulesEntity.getDay().getName_sp());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public String getDay() {
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es", "ES"));
        try {
            tempDate = parseDateFromServer.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }

    @OnClick(R.id.btn_buy)
    public void onViewClicked() {
        validator.validate();
        /*
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putSerializable("schedulesEntity", schedulesEntity);
        next(getActivity(), bundle, PaymentActivity.class, false);*/
    }

    @Override
    public void onValidationSucceeded() {
        System.out.println("FECHAAA----->" + date);
        mPresenter.createReservation(mSessionManager.getUserToken(),
                Integer.valueOf(etQuantity.getText().toString()), false, schedulesEntity.getName(), date);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }

    @Override
    public void createReservationResponse(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        ConfirmedDialog confirmedDialog = new ConfirmedDialog(getContext(), bundle);
        confirmedDialog.show();
        confirmedDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent refresh = new Intent(getContext(), TicketsActivity.class);
                startActivity(refresh);//Start the same Activity
                getActivity().finish();
            }
        });
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CreateReservationContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        if (active) {
            mProgressDialogCustom.show();
        } else {
            if (mProgressDialogCustom.isShowing()) {
                mProgressDialogCustom.dismiss();
            }
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
}
