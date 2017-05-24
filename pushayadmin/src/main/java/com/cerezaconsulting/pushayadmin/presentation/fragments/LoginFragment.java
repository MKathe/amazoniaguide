package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.presentation.activities.LoginActivity;
import com.cerezaconsulting.pushayadmin.presentation.activities.TravelActivity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.LoginContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {


    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    Unbinder unbinder;

    private LoginContract.Presenter mPresenter;
    //private ProgressDialogCustom mProgressDialogCustom;


    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
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
    public void loginSucessful() {
        showMessage("Login exitoso");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(LoginContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

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

    @OnClick({R.id.btn_login, R.id.tv_forgot_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                // mPresenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());
                nextActivity(getActivity(), null, TravelActivity.class, false);

                //showMessage("Conexión presenter");
                break;
            case R.id.tv_forgot_pass:
                //nextActivity(getActivity(), null, RegisterActivity.class, false);
                showMessage("Conexión olvidar contraseña");
                break;
        }
    }
}
