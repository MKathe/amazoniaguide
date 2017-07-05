package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.presentation.activities.CountriesActivity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.SchedulesSecondAdapter;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ScheduleContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.SchedulesPresenter;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.PlaceItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 19/05/17.
 */

public class ScheduleFragment extends BaseFragment implements ScheduleContract.View {
    @BindView(R.id.monday)
    RadioButton monday;
    @BindView(R.id.tuesday)
    RadioButton tuesday;
    @BindView(R.id.wednesday)
    RadioButton wednesday;
    @BindView(R.id.thursday)
    RadioButton thursday;
    @BindView(R.id.friday)
    RadioButton friday;
    @BindView(R.id.saturday)
    RadioButton saturday;
    @BindView(R.id.sunday)
    RadioButton sunday;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.switch_disable)
    SwitchCompat switchDisable;
    @BindView(R.id.btn_get_in)
    Button btnGetIn;
    @BindView(R.id.listSchedulesRL)
    FrameLayout listSchedulesRL;
    @BindView(R.id.layout_calendar)
    LinearLayout layoutCalendar;
    Unbinder unbinder;
    @BindView(R.id.rvList)
    RecyclerView rvList;

    private ScheduleContract.Presenter mPresenter;
    private SchedulesSecondAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SessionManager mSessionManager;
    private SchedulesEntity mondayEntity, tuesdayEntity, wednesdayEntity,
            thursdayEntity, fridayEntity, saturdayEntity, sundayEntity;
    private String daySelected;

    public ScheduleFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    public static ScheduleFragment newInstance(Bundle bundle) {
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getContext());
        daySelected = getArguments().getString("daySelected");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedules, container, false);
        unbinder = ButterKnife.bind(this, root);
        setHasOptionsMenu(true);

        switchDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    tvClose.setText("Trabajo este día");
                    tvClose.setTextColor(getResources().getColor(R.color.colorPrimary));
                    listSchedulesRL.setVisibility(View.VISIBLE);


                } else {
                    tvClose.setText("No trabajo este día");
                    tvClose.setTextColor(getResources().getColor(R.color.red));
                    listSchedulesRL.setVisibility(View.GONE);
                }
            }
        });
        btnGetIn.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new SchedulesSecondAdapter(new SchedulesEntity(), getContext(), (PlaceItem) mPresenter);
        //mAdapter = new ScheduleAdapter(new ArrayList<SchedulesEntity>() , getContext());
        rvList.setAdapter(mAdapter);
        getDayButton();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.monday, R.id.tuesday, R.id.wednesday,
            R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday,
            R.id.switch_disable, R.id.btn_get_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.monday:
                disableOrEnableDay(mondayEntity);
                daySelected = "Lunes";
                break;
            case R.id.tuesday:
                disableOrEnableDay(tuesdayEntity);
                daySelected = "Martes";
                break;
            case R.id.wednesday:
                disableOrEnableDay(wednesdayEntity);
                daySelected = "Miércoles";
                break;
            case R.id.thursday:
                disableOrEnableDay(thursdayEntity);
                daySelected = "Jueves";
                break;
            case R.id.friday:
                disableOrEnableDay(fridayEntity);
                daySelected = "Viernes";
                break;
            case R.id.saturday:
                disableOrEnableDay(saturdayEntity);
                daySelected = "Sábado";
                break;
            case R.id.sunday:
                disableOrEnableDay(sundayEntity);
                daySelected = "Domingo";
                break;
            case R.id.switch_disable:
                switchDisable.setChecked(true);
                listSchedulesRL.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_get_in:
                Bundle bundle = new Bundle();
                bundle.putString("daySelected", daySelected);
                next(getActivity(), bundle, CountriesActivity.class, false);
                getActivity().finish();
                break;
        }
    }

    private void getDayButton() {
        switch (daySelected) {
            case "Lunes":
                monday.setChecked(true);
                onViewClicked(monday);
                break;
            case "Martes":
                tuesday.setChecked(true);
                onViewClicked(tuesday);
                break;
            case "Miércoles":
                wednesday.setChecked(true);
                onViewClicked(wednesday);
                break;
            case "Jueves":
                thursday.setChecked(true);
                onViewClicked(thursday);
                break;
            case "Viernes":
                friday.setChecked(true);
                onViewClicked(friday);
                break;
            case "Sábado":
                saturday.setChecked(true);
                onViewClicked(saturday);
                break;
            case "Domingo":
                sunday.setChecked(true);
                onViewClicked(sunday);
                break;
        }
    }

    private void disableOrEnableDay(SchedulesEntity schedulesEntity) {
        if (schedulesEntity == null) {
            switchDisable.setChecked(false);
            mAdapter.setPlaceItem(null);
            btnGetIn.setVisibility(View.VISIBLE);
        } else {
            switchDisable.setChecked(true);
            mAdapter.setPlaceItem(schedulesEntity);
            btnGetIn.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSchedules(ArrayList<SchedulesEntity> list) {
        if (list != null) {
            switchDisable.setChecked(true);
            listSchedulesRL.setVisibility(View.VISIBLE);
            getSchedulesByDay(list);
            getDayButton();
        } else {
        }
    }

    private void getSchedulesByDay(ArrayList<SchedulesEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getDay().getId()) {
                case 1:
                    mondayEntity = list.get(i);
                    break;
                case 2:
                    tuesdayEntity = list.get(i);
                    break;
                case 3:
                    wednesdayEntity = list.get(i);
                    break;
                case 4:
                    thursdayEntity = list.get(i);
                    break;
                case 5:
                    fridayEntity = list.get(i);
                    break;
                case 6:
                    saturdayEntity = list.get(i);
                    break;
                case 7:
                    sundayEntity = list.get(i);
                    break;
                default:
                    //mondayEntity = list.get(i);
            }
        }

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ScheduleContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
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
}
