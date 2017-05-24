package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ProfileFragment extends BaseFragment {
    @BindView(R.id.im_profile)
    ImageView imProfile;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.ly_travel_history)
    LinearLayout lyTravelHistory;
    @BindView(R.id.ly_payment_history)
    LinearLayout lyPaymentHistory;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.im_name)
    ImageView imName;
    @BindView(R.id.tv_name_detail)
    EditText tvNameDetail;
    @BindView(R.id.ly_personal)
    LinearLayout lyPersonal;
    @BindView(R.id.im_email)
    ImageView imEmail;
    @BindView(R.id.tv_email_detail)
    EditText tvEmailDetail;
    @BindView(R.id.ly_email)
    LinearLayout lyEmail;
    @BindView(R.id.im_cel)
    ImageView imCel;
    @BindView(R.id.tv_cel_detail)
    EditText tvCelDetail;
    @BindView(R.id.ly_cel)
    LinearLayout lyCel;
    Unbinder unbinder;
    //private ScheduleFragment.Presenter mPresenter;

    // private CountriesAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    //private ProgressDialogCustom mProgressDialogCustom;

    public ProfileFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // mPresenter.start();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, root);
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

    @OnClick({R.id.ly_travel_history, R.id.ly_payment_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_travel_history:
                break;
            case R.id.ly_payment_history:
                break;
        }
    }
}
