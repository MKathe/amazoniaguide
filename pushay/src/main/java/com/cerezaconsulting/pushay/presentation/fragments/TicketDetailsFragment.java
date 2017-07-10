package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 2/06/17.
 */

public class TicketDetailsFragment extends BaseFragment {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_destiny)
    TextView tvDestiny;
    @BindView(R.id.tv_guide)
    TextView tvGuide;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_service_detail)
    TextView tvServiceDetail;
    @BindView(R.id.tv_cell)
    TextView tvCell;
    Unbinder unbinder;

    private ReservationEntity reservationEntity;
    private SessionManager  mSessionManager;

    public TicketDetailsFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
    }

    public static TicketDetailsFragment newInstance(Bundle bundle) {
        TicketDetailsFragment fragment = new TicketDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getContext());
        reservationEntity = (ReservationEntity) getArguments().getSerializable("reservationEntity");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_paid_ticket, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(mSessionManager.getUserEntity().getFirst_name());
        tvDestiny.setText(reservationEntity.getSchedules().getDestiny().getName());
        tvDate.setText(reservationEntity.getDay());
        tvGuide.setText(reservationEntity.getSchedules().getGuide().getFullName());
        tvCell.setText(reservationEntity.getSchedules().getGuide().getCellphone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
