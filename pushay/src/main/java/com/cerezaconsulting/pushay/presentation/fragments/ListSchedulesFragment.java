package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushay.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.presentation.activities.GuideDetailsActivity;
import com.cerezaconsulting.pushay.presentation.activities.TicketsDetailActivity;
import com.cerezaconsulting.pushay.presentation.adapters.ListSchedulesAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.ListSchedulesContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.SchedulesItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 31/05/17.
 */

public class ListSchedulesFragment extends BaseFragment implements ListSchedulesContract.View {

    @BindView(R.id.btn_price)
    RadioButton btnPrice;
    @BindView(R.id.btn_stars)
    RadioButton btnStars;
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
    @BindView(R.id.tv_destiny_name)
    TextView tvDestinyName;
    private ListSchedulesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ListSchedulesContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private DestinyTravelEntity destinyTravelEntity;
    private String date;

    public ListSchedulesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(destinyTravelEntity.getName(), date);

    }

    public static ListSchedulesFragment newInstance(Bundle bundle) {
        ListSchedulesFragment fragment = new ListSchedulesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinyTravelEntity = (DestinyTravelEntity) getArguments().getSerializable("destinyTravelEntity");
        date = getArguments().getString("date");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guides, container, false);

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
                mPresenter.loadOrdersFromPage(destinyTravelEntity.getName(), date, 1);
            }
        });



        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new ListSchedulesAdapter(new ArrayList<SchedulesEntity>(), getContext(), (SchedulesItem) mPresenter);
        rvList.setAdapter(mAdapter);
        tvDestinyName.setText(destinyTravelEntity.getName());
    }


    @Override
    public void getListGuideByDestiny(ArrayList<SchedulesEntity> list) {
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
                mPresenter.loadfromNextPage(destinyTravelEntity.getName(), date);
            }
        });
    }


    @Override
    public void showDetailsTickets(SchedulesEntity schedulesEntity) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("schedulesEntity", schedulesEntity);
        bundle.putString("date", date);
        next(getActivity(), bundle, GuideDetailsActivity.class, false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(ListSchedulesContract.Presenter mPresenter) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.btn_price, R.id.btn_stars})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_price:
                btnPrice.setTextColor(getResources().getColor(R.color.white));
                btnStars.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                break;
            case R.id.btn_stars:
                btnStars.setTextColor(getResources().getColor(R.color.white));
                btnPrice.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                break;
        }
    }
}
