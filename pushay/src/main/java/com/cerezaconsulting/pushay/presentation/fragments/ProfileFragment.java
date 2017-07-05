package com.cerezaconsulting.pushay.presentation.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.UserEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.contracts.ProfileContract;
import com.cerezaconsulting.pushay.presentation.dialogs.EditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 19/05/17.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.View {


    @BindView(R.id.im_profile)
    ImageView imProfile;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.ly_travel_history)
    LinearLayout lyTravelHistory;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.img_edit)
    RelativeLayout imgEdit;
    @BindView(R.id.ly_personal_edit)
    LinearLayout lyPersonalEdit;
    @BindView(R.id.im_name)
    ImageView imName;
    @BindView(R.id.tv_name_detail)
    EditText tvNameDetail;
    @BindView(R.id.ly_personal)
    LinearLayout lyPersonal;
    @BindView(R.id.im_email)
    ImageView imEmail;
    @BindView(R.id.tv_email_detail)
    EditText tvEmailDetail;
    @BindView(R.id.ly_email)
    LinearLayout lyEmail;
    @BindView(R.id.im_cel)
    ImageView imCel;
    @BindView(R.id.tv_cel_detail)
    EditText tvCelDetail;
    @BindView(R.id.ly_cel)
    LinearLayout lyCel;
    Unbinder unbinder;

    private SessionManager mSessionManager;
    private ProfileContract.Presenter mPresenter;
    private ProgressDialog progressDialog;
    //private ProgressDialogCustom mProgressDialogCustom;

    public ProfileFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // mPresenter.start();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Subscribe
    public void onUpdateProfile(UserEntity userEntity) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getContext());
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(mSessionManager.getUserEntity().getFullName());
        tvNameDetail.setText(mSessionManager.getUserEntity().getFullName());
        tvEmailDetail.setText(mSessionManager.getUserEntity().getEmail());
        tvCelDetail.setText(mSessionManager.getUserEntity().getCellphone());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void updateUser(UserEntity userEntity) {
        mPresenter.editUser(userEntity, mSessionManager.getUserToken());
    }

    @Override
    public void editSuccessful(UserEntity userEntity) {

        UserEntity newUserEntity = mSessionManager.getUserEntity();
        newUserEntity.setFirst_name(userEntity.getFirst_name());
        newUserEntity.setLast_name(userEntity.getLast_name());
        mSessionManager.setUser(newUserEntity);
        EventBus.getDefault().post(newUserEntity);
        tvName.setText(newUserEntity.getFullName());
        tvNameDetail.setText(newUserEntity.getFullName());
        tvEmailDetail.setText(newUserEntity.getEmail());
        tvCelDetail.setText(newUserEntity.getCellphone());
        showMessage("Tus datos han sido actualizados con éxito");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter mPresenter) {
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

    @OnClick({R.id.ly_travel_history, R.id.img_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_travel_history:
                break;
            case R.id.img_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEntity", mSessionManager.getUserEntity());
                EditDialog editDialog = new EditDialog(getContext(),bundle,this);
                editDialog.show();
                break;
        }
    }
}