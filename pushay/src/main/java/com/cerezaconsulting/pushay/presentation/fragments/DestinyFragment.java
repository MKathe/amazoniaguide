package com.cerezaconsulting.pushay.presentation.fragments;
import android.app.ListActivity;
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

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.core.RecyclerViewScrollListener;
import com.cerezaconsulting.pushay.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.pushay.data.entities.CityEntity;
import com.cerezaconsulting.pushay.data.entities.DestinyTravelEntity;
import com.cerezaconsulting.pushay.presentation.activities.ListSchedulesActivity;
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
    @BindView(R.id.noListIcon)
    ImageView noListIcon;
    @BindView(R.id.noListMain)
    TextView noListMain;
    @BindView(R.id.noList)
    LinearLayout noList;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private CityEntity cityEntity;
    private DestinyAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private DestinyContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private DatePickerDialog datePickerDialog;

    private String date;
    private DestinyTravelEntity sendDestinyTravelEntity;


    public DestinyFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(cityEntity.getId());
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                mPresenter.loadOrdersFromPage(cityEntity.getId(), 1);
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
        mAdapter = new DestinyAdapter(new ArrayList<DestinyTravelEntity>(), getContext(), (DestinyItem) mPresenter);
        rvList.setAdapter(mAdapter);

        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public void getDestiny(ArrayList<DestinyTravelEntity> list) {
        mAdapter.setItems(list);
        if (list != null) {
            noList
                    .setVisibility((list.size() > 0) ? View.GONE : View.VISIBLE);
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
                mPresenter.loadfromNextPage(cityEntity.getId());
            }
        });

    }

    @Override
    public void clickItemDestiny(DestinyTravelEntity destinyTravelEntity) {

        sendDestinyTravelEntity = destinyTravelEntity;

        datePickerDialog.setTitle("ELija su fecha de viaje");
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putSerializable("destinyTravelEntity", sendDestinyTravelEntity);
        next(getActivity(),bundle, ListSchedulesActivity.class , true);
    }

}
