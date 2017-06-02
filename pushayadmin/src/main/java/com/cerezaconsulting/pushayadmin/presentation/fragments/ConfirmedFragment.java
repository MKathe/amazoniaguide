package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;

import butterknife.ButterKnife;

/**
 * Created by katherine on 26/05/17.
 */

public class ConfirmedFragment extends BaseFragment{

    private RegisterContract.Presenter mPresenter;

    public ConfirmedFragment() {
        // Requires empty public constructor
    }

    public static ConfirmedFragment newInstance() {
        return new ConfirmedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_confirmed_travel, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        // mPresenter.start();
    }

}
