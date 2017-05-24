package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_birth)
    EditText etBirth;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_celphone)
    EditText etCelphone;
    @BindView(R.id.btn_man)
    RadioButton btnMan;
    @BindView(R.id.btn_woman)
    RadioButton btnWoman;
    @BindView(R.id.btn_create)
    Button btnCreate;
    Unbinder unbinder;
    private RegisterContract.Presenter mPresenter;

    public RegisterFragment() {
        // Requires empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, root);
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

    @Override
    public void registerSucessful() {
        showMessage("Registrado correctamente");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
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

    @OnClick({R.id.btn_man, R.id.btn_woman, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_man:
                break;
            case R.id.btn_woman:
                break;
            case R.id.btn_create:
                showMessage("Conexión con presenter");
                break;
        }
    }
}
