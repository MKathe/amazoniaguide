package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseActivity;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.presentation.activities.LoginActivity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;
import com.cerezaconsulting.pushayadmin.presentation.dialogs.ConfirmedRegisterDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View, Validator.ValidationListener {

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(max = 250, message = "Cantidad de dígitos no permitida", sequence = 1)
    @Email(message = "Email inválido")
    @BindView(R.id.et_email)
    EditText etEmail;

    @NotEmpty(message = "Este campo no puede ser vacío",sequence = 1)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 2)
    @BindView(R.id.et_password)
    EditText etPassword;

    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 3)
    @NotEmpty(message = "Este campo no puede ser vacío",sequence = 4)
    @BindView(R.id.et_firstname)
    EditText etFirstname;


    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 5)
    @NotEmpty(message = "Este campo no puede ser vacío",sequence = 6)
    @BindView(R.id.et_lastname)
    EditText etLastname;


    @NotEmpty(message = "Este campo no puede ser vacío",sequence = 7)
    @Length(max = 12, message = "No puede exceder el número de dígitos", sequence = 8)
    @BindView(R.id.et_cellphone)
    EditText etCellphone;

    @BindView(R.id.btn_man)
    RadioButton btnMan;
    @BindView(R.id.btn_woman)
    RadioButton btnWoman;
    @BindView(R.id.btn_create)
    Button btnCreate;
    Unbinder unbinder;


    private Validator validator;
    private boolean isLoading = false;
    private ProgressDialog progressDialog;
    private RegisterContract.Presenter mPresenter;
    UserEntity userEntity;
    private String gender;

    public RegisterFragment() {
        // Requires empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;    }

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

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.circle_progress));

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void registerSuccessful(final UserEntity userEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userEntity", userEntity);
        ConfirmedRegisterDialog confirmedRegisterDialog = new ConfirmedRegisterDialog(getContext(),bundle);
        confirmedRegisterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        confirmedRegisterDialog.show();
    }

    @Override
    public void errorRegister(String msg) {
        showErrorMessage(msg);
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

        if (progressDialog != null){

            if(active){
                progressDialog.show();
            }else{
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
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
    public void onValidationSucceeded() {

        userEntity = new UserEntity(etEmail.getText().toString(),
                etPassword.getText().toString(),etFirstname.getText().toString(),etLastname.getText().toString(),
                etCellphone.getText().toString(),gender);
        mPresenter.registerUser(userEntity);
        isLoading=true;
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
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.btn_man, R.id.btn_woman, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_man:
                gender = "M";
                break;
            case R.id.btn_woman:
                gender = "F";
                break;
            case R.id.btn_create:
                validator.validate();
                break;
        }
    }
}
