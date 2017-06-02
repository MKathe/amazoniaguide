package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.cerezaconsulting.pushay.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushay.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushay.data.entities.PlacesEntity;
import com.cerezaconsulting.pushay.data.entities.TicketEntity;
import com.cerezaconsulting.pushay.presentation.activities.TicketsActivity;
import com.cerezaconsulting.pushay.presentation.activities.TicketsDetailActivity;
import com.cerezaconsulting.pushay.presentation.adapters.CountriesAdapter;
import com.cerezaconsulting.pushay.presentation.adapters.TicketsAdapter;
import com.cerezaconsulting.pushay.presentation.contracts.CountriesContract;
import com.cerezaconsulting.pushay.presentation.contracts.TicketsContract;
import com.cerezaconsulting.pushay.presentation.presenters.commons.PlaceItem;
import com.cerezaconsulting.pushay.presentation.presenters.commons.TicketItem;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 31/05/17.
 */

public class TicketsFragment extends BaseFragment implements TicketsContract.View {

    @BindView(R.id.rv_tickets)
    RecyclerView rvTickets;
    @BindView(R.id.noTicketsIcon)
    ImageView noTicketsIcon;
    @BindView(R.id.noTicketsMain)
    TextView noTicketsMain;
    @BindView(R.id.noTickets)
    LinearLayout noTickets;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    Unbinder unbinder;

    private TicketsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TicketsContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    public TicketsFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
        mPresenter.loadList();
    }

    public static TicketsFragment newInstance() {
        return new TicketsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tickets, container, false);
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.dark_gray),
                ContextCompat.getColor(getActivity(), R.color.black)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvTickets);
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
        mLayoutManager = new LinearLayoutManager(getContext());
        rvTickets.setLayoutManager(mLayoutManager);
        mAdapter = new TicketsAdapter(new ArrayList<TicketEntity>(), getContext(), (TicketItem) mPresenter);
        rvTickets.setAdapter(mAdapter);
    }

    @Override
    public void getTickets(ArrayList<TicketEntity> list) {
        mAdapter.setItems(list);

        if (list !=null){
            noTickets.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
        }
        rvTickets.addOnScrollListener(new RecyclerViewScrollListener() {
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
    public void showDetailsTickets(TicketEntity reservationEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("reservation", reservationEntity);
        next(getActivity(),bundle, TicketsDetailActivity.class, false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(TicketsContract.Presenter mPresenter) {
        this.mPresenter=mPresenter;
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
}
