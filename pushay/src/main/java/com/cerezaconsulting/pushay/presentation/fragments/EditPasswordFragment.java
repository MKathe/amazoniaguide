package com.cerezaconsulting.pushay.presentation.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.contracts.EditPasswordContract;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by junior on 01/06/16.
 */
public class EditPasswordFragment extends Fragment implements EditPasswordContract.View, Validator.ValidationListener {


    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 1)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 2)
    @BindView(R.id.et_current_password)
    EditText etCurrentPassword;
    @BindView(R.id.input_current_password)
    TextInputLayout inputCurrentPassword;
    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 3)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 4)
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.input_new_password)
    TextInputLayout inputNewPassword;
    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 5)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 6)
    @BindView(R.id.et_new_password_again)
    EditText etNewPasswordAgain;
    @BindView(R.id.input_new_password_again)
    TextInputLayout inputNewPasswordAgain;
    @BindView(R.id.btn_change_pass)
    Button btnChangePass;
    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    Unbinder unbinder;

    private EditPasswordContract.Presenter mPresenter;
    private Validator validator;
    private boolean isLoading = false;
    private ProgressDialog mProgressDialog;

    public static EditPasswordFragment newInstance() {
        EditPasswordFragment fragment = new EditPasswordFragment();
        return fragment;
    }

    public EditPasswordFragment() {

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Cambiado su contraseña...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.circle_progress));

    }


    @Override
    public void updatePassword() {
        if (etNewPassword.getText().toString().equals(etNewPasswordAgain.getText().toString())) {
            if (!isLoading) {
                validator.validate();
            }

        } else {

            showErrorMessage("Las contraseñas nuevas deben ser iguales");
        }

    }

    @Override
    public void showUpdateSucces() {
        Toast.makeText(getContext(), "Su cambio de contraseña fue exitoso", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(EditPasswordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {


        if (mProgressDialog != null) {

            if (active) {
                mProgressDialog.show();
            } else {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        }
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
    public void onValidationSucceeded() {
        SessionManager mSessionManager = new SessionManager(getContext());
        mPresenter.updatePassword(mSessionManager.getUserToken(),
                etCurrentPassword.getText().toString(),
                etNewPassword.getText().toString(),
                mSessionManager.getUserEntity().getEmail());
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
                showErrorMessage(message);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick(R.id.btn_change_pass)
    public void onViewClicked() {
        updatePassword();
    }
}
