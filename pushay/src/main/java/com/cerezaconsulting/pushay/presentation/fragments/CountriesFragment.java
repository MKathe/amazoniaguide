package com.cerezaconsulting.pushay.presentation.fragments;

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

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushay.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushay.data.entities.CountryEntity;
import com.cerezaconsulting.pushay.presentation.activities.CitiesActivity;
import com.cerezaconsulting.pushay.presentation.adapters.CountriesAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.CountriesItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

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
    @BindView(R.id.noListIcon)
    ImageView noListIcon;
    @BindView(R.id.noListMain)
    TextView noListMain;
    @BindView(R.id.noList)
    LinearLayout noList;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private CountriesAdapter mAdapter;
    private String daySelected;
    private GridLayoutManager mLayoutManager;
    private CountriesContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private CountryEntity countryEntity;

    public CountriesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    public static CountriesFragment newInstance() {
        CountriesFragment fragment = new CountriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        //countryEntity = (CountryEntity) getArguments().getSerializable("countryEntity");
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.dark_gray),
                ContextCompat.getColor(getActivity(), R.color.black)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mPresenter.start();
                mPresenter.loadOrdersFromPage(1);
            }
        });

        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new CountriesAdapter(new ArrayList<CountryEntity>(), getContext(), (CountriesItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void getCountries(ArrayList<CountryEntity> list) {
        mAdapter.setItems(list);
        if (list != null) {
            noList.setVisibility((list.size() > 0) ? View.GONE : View.VISIBLE);
        }
        rvList.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                mPresenter.loadFromNextPage();
            }
        });

    }

    @Override
    public void clickItemCountry(CountryEntity countryEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("countryEntity", countryEntity);
        next(getActivity(), bundle, CitiesActivity.class, false);
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
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
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


}
