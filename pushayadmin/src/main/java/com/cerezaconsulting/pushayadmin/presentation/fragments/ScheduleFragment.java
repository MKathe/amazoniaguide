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
import com.cerezaconsulting.pushayadmin.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 19/05/17.
 */

public class ScheduleFragment extends BaseFragment {
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
    //private ScheduleFragment.Presenter mPresenter;

    // private CountriesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    //private ProgressDialogCustom mProgressDialogCustom;

    public ScheduleFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // mPresenter.start();
    }

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedules, container, false);
        unbinder = ButterKnife.bind(this, root);


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
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday, R.id.switch_disable, R.id.btn_get_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.monday:
                break;
            case R.id.tuesday:
                break;
            case R.id.wednesday:
                break;
            case R.id.thursday:
                break;
            case R.id.friday:
                break;
            case R.id.saturday:
                break;
            case R.id.sunday:
                break;
            case R.id.switch_disable:
                switchDisable.setChecked(true);
                listSchedulesRL.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_get_in:
                break;
        }
    }
}
