package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.presentation.adapters.DestinyAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.DestinyContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.DestinyItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class DestinyFragment extends BaseFragment implements DestinyContract.View, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noPlacesIcon)
    ImageView noPlacesIcon;
    @BindView(R.id.noPLacesMain)
    TextView noPLacesMain;
    @BindView(R.id.noPlaces)
    LinearLayout noPlaces;
    Unbinder unbinder;

    private CityEntity cityEntity;
    private DestinyAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private DestinyContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    private DatePickerDialog datePickerDialog;
    //private DialogCreateSchedules dialogCreateSchedules;

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
        //idCountry =  (int) getArguments().getSerializable("id_country");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_simple_list, container, false);
        mPresenter.listDestiny(cityEntity.getId());
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo destinos...");
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
        datePickerDialog.setTitle("Elija su día de viaje");
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String msg = year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth);
        Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();
    }
}
