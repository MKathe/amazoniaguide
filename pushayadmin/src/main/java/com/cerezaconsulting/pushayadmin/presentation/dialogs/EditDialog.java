package com.cerezaconsulting.pushayadmin.presentation.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ProfileContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katherine on 21/06/17.
 */

public class EditDialog extends AlertDialog {

    UserEntity userEntity;
    UserEntity newUser;
    @BindView(R.id.et_firstname)
    EditText etFirstname;
    @BindView(R.id.et_lastname)
    EditText etLastname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.btn_create)
    Button btnCreate;


    private ProfileContract.View mView;

    public EditDialog(Context context, Bundle bundle, ProfileContract.View mView) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_edit_profile, null);
        ButterKnife.bind(this, view);
        this.mView = mView;
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        setView(view);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etFirstname.setText(userEntity.getFirst_name());
        etLastname.setText(userEntity.getLast_name());
        etEmail.setText(userEntity.getEmail());
        etCellphone.setText(userEntity.getCellphone());
    }

    private void updateUser(){
        userEntity.setFirst_name(etFirstname.getText().toString());
        userEntity.setLast_name(etLastname.getText().toString());
        userEntity.setEmail(etEmail.getText().toString());
        userEntity.setCellphone(etCellphone.getText().toString());
        mView.updateUser(userEntity);
    }

    @OnClick(R.id.btn_create)
    public void onViewClicked() {
        updateUser();
        dismiss();
    }
}
