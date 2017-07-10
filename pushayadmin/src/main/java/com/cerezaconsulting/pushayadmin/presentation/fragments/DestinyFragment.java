package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushayadmin.data.entities.CityEntity;
import com.cerezaconsulting.pushayadmin.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushayadmin.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushayadmin.presentation.activities.SchedulesActivity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.DestinyAdapter;
import com.cerezaconsulting.pushayadmin.presentation.contracts.DestinyContract;
import com.cerezaconsulting.pushayadmin.presentation.dialogs.DialogCreateSchedules;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.DestinyItem;
import com.cerezaconsulting.pushayadmin.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class DestinyFragment extends BaseFragment implements DestinyContract.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noPlacesIcon)
    ImageView noPlacesIcon;
    @BindView(R.id.noPLacesMain)
    TextView noPLacesMain;
    @BindView(R.id.noPlaces)
    LinearLayout noPlaces;
    Unbinder unbinder;

    private String daySelected;
    private CityEntity cityEntity;
    private DestinyAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private DestinyContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private DialogCreateSchedules dialogCreateSchedules;

    public DestinyFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    public static DestinyFragment newInstance(Bundle bundle) {
        DestinyFragment fragment = new DestinyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityEntity = (CityEntity) getArguments().getSerializable("cityEntity");
        daySelected = (String) getArguments().getSerializable("daySelected");
        //idCountry =  (int) getArguments().getSerializable("id_country");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.listDestiny(cityEntity.getId());
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new DestinyAdapter(new ArrayList<DestinyTravelEntity>(), getContext(), (DestinyItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }


    @Override
    public void getDestiny(ArrayList<DestinyTravelEntity> list) {
        mAdapter.setItems(list);
        if (list !=null){
            noPlaces.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void clickItemDestiny(DestinyTravelEntity destinyTravelEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("destinyEntity", destinyTravelEntity);
        bundle.putString("daySelected", daySelected);

        dialogCreateSchedules = DialogCreateSchedules.newInstance(bundle);
        dialogCreateSchedules.show(getActivity().getFragmentManager(), "Registro");
    }

    @Override
    public void sendCreateSchedules(SchedulesEntity schedulesEntity) {
        mPresenter.createSchedules(schedulesEntity);
    }

    @Override
    public void createScheduleSuccesful(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("daySelected", daySelected);
        dialogCreateSchedules.dismiss();

        next(getActivity(), bundle, SchedulesActivity.class, false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(DestinyContract.Presenter mPresenter) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
