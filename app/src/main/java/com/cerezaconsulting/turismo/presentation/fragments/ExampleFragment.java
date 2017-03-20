package com.cerezaconsulting.turismo.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cerezaconsulting.turismo.R;
import com.cerezaconsulting.turismo.core.BaseActivity;
import com.cerezaconsulting.turismo.core.BaseFragment;
import com.cerezaconsulting.turismo.presentation.activities.ExampleActivity;
import com.cerezaconsulting.turismo.presentation.contracts.MainContract;
import com.cerezaconsulting.turismo.utils.ProgressDialogCustom;

import butterknife.ButterKnife;

/**
 * Created by junior on 27/08/16.
 */
public class ExampleFragment extends BaseFragment  implements  MainContract.View{

    private static final String TAG = ExampleActivity.class.getSimpleName();


    private MainContract.Presenter mPresenter;

    private ProgressDialogCustom mProgressDialogCustom;



    public ExampleFragment() {
        // Requires empty public constructor
    }

    public static ExampleFragment newInstance() {
        return new ExampleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_special_schedules, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");


    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMessage(String msg) {
        ((BaseActivity) getActivity()).showMessage(msg);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

}
