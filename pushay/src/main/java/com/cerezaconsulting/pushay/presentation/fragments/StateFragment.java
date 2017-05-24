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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushay.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.presentation.adapters.CountriesAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.PlaceItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class StateFragment extends BaseFragment implements CountriesContract.View {


    @BindView(R.id.rv_select_place)
    RecyclerView rvSelectPlace;
    @BindView(R.id.noPlacesIcon)
    ImageView noPlacesIcon;
    @BindView(R.id.noPLacesMain)
    TextView noPLacesMain;
    @BindView(R.id.noPlaces)
    LinearLayout noPlaces;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    Unbinder unbinder;

    private CountriesAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private CountriesContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    public StateFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
        mPresenter.getPlaces();
    }

    public static StateFragment newInstance() {
        return new StateFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country_cities, container, false);
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.dark_gray),
                ContextCompat.getColor(getActivity(), R.color.black)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvSelectPlace);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mPresenter.start();
               // mPresenter.loadOrdersFromPage(1);
            }
        });


        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvSelectPlace.setLayoutManager(mLayoutManager);

        mAdapter = new CountriesAdapter(new ArrayList<PlacesEntity>() , getContext(), (PlaceItem) mPresenter);
        rvSelectPlace.setAdapter(mAdapter);
    }

    @Override
    public void getCountries(ArrayList<PlacesEntity> list) {
        mAdapter.setItems(list);

        if (list !=null){
            noPlaces.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
        }
        rvSelectPlace.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }
            @Override
            public void onScrollDown() {

            }
            @Override
            public void onLoadMore() {
                mPresenter.loadfromNextPage();
            }
        });
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

    @OnClick(R.id.btn_continue)
    public void onViewClicked() {

    }
}
