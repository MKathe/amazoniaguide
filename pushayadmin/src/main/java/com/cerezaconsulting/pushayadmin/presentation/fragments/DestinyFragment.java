package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.content.DialogInterface;
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

public class DestinyFragment extends BaseFragment implements DestinyContract.View, DialogInterface.OnDismissListener {

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
        daySelected = (String) getArguments().getSerializable("daySelected");
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
    }


    @Override
    public void getDestiny(ArrayList<DestinyTravelEntity> list) {
        mAdapter.setItems(list);
        if (list !=null){
            noPlaces.setVisibility((list.size()>0) ? View.GONE : View.VISIBLE);
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("destinyEntity", destinyTravelEntity);
        bundle.putString("daySelected", daySelected);
        dialogCreateSchedules = DialogCreateSchedules.newInstance(bundle);
        dialogCreateSchedules.show(getActivity().getFragmentManager(), "Registro");
        /*dialogCreateSchedules.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Bundle bundle = new Bundle();
                bundle.putString("daySelected", daySelected);
                next(getActivity(), bundle, SchedulesActivity.class, true);
            }
        });*/
    }

    @Override
    public void sendCreateSchedules(SchedulesEntity schedulesEntity) {
        mPresenter.createSchedules(schedulesEntity);
    }

    @Override
    public void createScheduleSuccesful(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("daySelected", daySelected);
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
    public void onDismiss(DialogInterface dialog) {
        if (dialog==null){
            Bundle bundle = new Bundle();
            bundle.putString("daySelected", daySelected);
            next(getActivity(), bundle, SchedulesActivity.class, true);
            getActivity().finish();
        }
    }
}
