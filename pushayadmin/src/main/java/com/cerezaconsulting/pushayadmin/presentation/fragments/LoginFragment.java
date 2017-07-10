package com.cerezaconsulting.pushayadmin.presentation.fragments;

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
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.presentation.activities.LoginActivity;
import com.cerezaconsulting.pushayadmin.presentation.activities.TravelActivity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.LoginContract;
import com.cerezaconsulting.pushayadmin.presentation.dialogs.DialogForgotPassword;
import com.cerezaconsulting.pushayadmin.utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View,Validator.ValidationListener {

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Email(message = "Email inválido")
    @BindView(R.id.et_email)
    EditText etEmail;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    Unbinder unbinder;

    private LoginContract.Presenter mPresenter;
    private DialogForgotPassword dialogForgotPassword;
    private ProgressDialogCustom mProgressDialogCustom;
    private Validator validator;



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
        dialogForgotPassword = new DialogForgotPassword(getContext(), this);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();
         mPresenter.start();
    }

    @Override
    public void loginSuccessful(UserEntity userEntity) {

        newActivityClearPreview(getActivity(), null, TravelActivity.class);
        showMessage("Login exitoso");
    }

    @Override
    public void errorLogin(String msg) {
        showErrorMessage(msg);

    }

    @Override
    public void showDialogForgotPassword() {
        dialogForgotPassword.show();
    }

    @Override
    public void showSendEmail(String email) {
        mPresenter.sendEmail(email);
        dialogForgotPassword.dismiss();
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
        if (getView() == null) {
            return;
        }
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

    @OnClick({R.id.btn_login, R.id.tv_forgot_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_forgot_pass:
                //nextActivity(getActivity(), null, RegisterActivity.class, false);
                showDialogForgotPassword();
                break;
        }
    }


    @Override
    public void onValidationSucceeded() {
        mPresenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), "Por favor ingrese lo campos correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
