package com.cerezaconsulting.pushayadmin.presentation.fragments;

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

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushayadmin.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushayadmin.data.entities.CountryEntity;
import com.cerezaconsulting.pushayadmin.presentation.activities.CitiesActivity;
import com.cerezaconsulting.pushayadmin.presentation.adapters.CountriesAdapter;
import com.cerezaconsulting.pushayadmin.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushayadmin.presentation.presenters.CountriesPresenter;
import com.cerezaconsulting.pushayadmin.presentation.presenters.TodayPresenter;
import com.cerezaconsulting.pushayadmin.presentation.presenters.commons.CountriesItem;
import com.cerezaconsulting.pushayadmin.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class CountriesFragment extends BaseFragment implements CountriesContract.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    ;
    @BindView(R.id.noPlacesIcon)
    ImageView noPlacesIcon;
    @BindView(R.id.noPLacesMain)
    TextView noPLacesMain;
    @BindView(R.id.noPlaces)
    LinearLayout noPlaces;
    Unbinder unbinder;

    private CountriesAdapter mAdapter;
    private String daySelected;
    private GridLayoutManager mLayoutManager;
    private CountriesContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    public CountriesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    public static CountriesFragment newInstance(Bundle bundle) {
        CountriesFragment fragment = new CountriesFragment();
        fragment.setArguments(bundle);
        return fragment;    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        daySelected = getArguments().getString("daySelected");

        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new CountriesAdapter(new ArrayList<CountryEntity>(), getContext(), (CountriesItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void getCountries(ArrayList<CountryEntity> list) {

        mAdapter.setItems(list);
        if (list !=null){
            noPlaces.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void clickItemCountry(CountryEntity countryEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("countryEntity", countryEntity);
        bundle.putString("daySelected", daySelected);
        next(getActivity(),bundle, CitiesActivity.class,false);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CountriesContract.Presenter mPresenter) {
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
