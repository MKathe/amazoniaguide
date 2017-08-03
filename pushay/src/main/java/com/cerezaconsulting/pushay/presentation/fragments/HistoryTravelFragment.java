package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.cerezaconsulting.pushay.data.entities.ReservationEntity;
import com.cerezaconsulting.pushay.presentation.adapters.HistoryTravelAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.HistoryTravelContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.PlaceItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 19/07/17.
 */

public class HistoryTravelFragment extends BaseFragment implements HistoryTravelContract.View {

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
    private HistoryTravelContract.Presenter mPresenter;
    private HistoryTravelAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ProgressDialogCustom mProgressDialogCustom;


    public HistoryTravelFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public static HistoryTravelFragment newInstance() {
        return new HistoryTravelFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
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
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new HistoryTravelAdapter(new ArrayList<ReservationEntity>(), getContext(), (PlaceItem) mPresenter);
        rvList.setAdapter(mAdapter);

    }

   /* @Override
    public void showDetailsTravel(ReservationEntity reservationEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("travel", reservationEntity);
        TravelDetailsDialog travelDetailsDialog = new TravelDetailsDialog(getContext(), bundle);
        travelDetailsDialog.show();
    }*/

    @Override
    public void getTravelList(ArrayList<ReservationEntity> list) {
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
               // mPresenter.loadFromNextPage();
            }
        });
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(HistoryTravelContract.Presenter mPresenter) {
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
