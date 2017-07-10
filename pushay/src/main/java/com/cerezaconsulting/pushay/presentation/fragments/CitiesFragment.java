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

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.presentation.activities.DestinyActivity;
import com.cerezaconsulting.pushay.presentation.adapters.CitiesAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.CitiesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.CitiesItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class CitiesFragment extends BaseFragment implements CitiesContract.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noPlacesIcon)
    ImageView noPlacesIcon;
    @BindView(R.id.noPLacesMain)
    TextView noPLacesMain;
    @BindView(R.id.noPlaces)
    LinearLayout noPlaces;
    Unbinder unbinder;
    private CountryEntity countryEntity;
    private CitiesAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private CitiesContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    public CitiesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    public static CitiesFragment newInstance(Bundle bundle) {
        CitiesFragment fragment = new CitiesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countryEntity = (CountryEntity) getArguments().getSerializable("countryEntity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_simple_list, container, false);
        mPresenter.getCities(countryEntity.getId());
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo ciudades...");
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new CitiesAdapter(new ArrayList<CityEntity>(), getContext(), (CitiesItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void getCities(final ArrayList<CityEntity> list) {
        mAdapter.setItems(list);
        if (list !=null){
            noPlaces.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
        }

    }

    @Override
    public void clickItemCities(CityEntity cityEntity) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("cityEntity", cityEntity);
        next(getActivity(),bundle, DestinyActivity.class,false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(CitiesContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        if (mProgressDialogCustom != null) {

            if (active) {
                mProgressDialogCustom.show();
            } else {
                if (mProgressDialogCustom.isShowing()) {
                    mProgressDialogCustom.dismiss();
                }
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

}
