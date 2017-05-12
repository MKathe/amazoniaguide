package com.cerezaconsulting.pushay.presentation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.presentation.activities.LoginActivity;
import com.cerezaconsulting.pushay.presentation.activities.RegisterActivity;
import com.cerezaconsulting.pushay.presentation.contracts.LoginContract;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by junior on 27/08/16.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View,FacebookCallback<LoginResult> {

    private static final String TAG = LoginActivity.class.getSimpleName();


    CallbackManager mCallbackManager;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_button)
    Button loginButton;
    Unbinder unbinder;
    @BindView(R.id.et_register)
    TextView etRegister;


    private LoginContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;


    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");

    }


    @Override
    public void setPresenter(LoginContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
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
    public void loginSucessful() {
        showMessage("Login realizado correctamente");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.btn_login, R.id.login_button,R.id.et_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
               // mPresenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());
               showMessage("Conexión presenter");
                break;
            case R.id.login_button:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.et_register:
                nextActivity(getActivity(), null, RegisterActivity.class, false);
                break;
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        String access_token_facebook = loginResult.getAccessToken().getToken();
        if (access_token_facebook != null || !access_token_facebook.equals("")) {
            // mPresenter.loginUserFacebook(access_token_facebook);
            showMessage("Login con facebook correcto");

        } else {
            showErrorMessage("Algo sucedió mal al intentar loguearse");
        }
    }

    @Override
    public void onCancel() {
        showErrorMessage("El login a facebook se a cancelado, intente más tarde");
    }

    @Override
    public void onError(FacebookException error) {
        showErrorMessage("Error al intentar loguearse");
    }
}
