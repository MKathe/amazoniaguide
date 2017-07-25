package com.cerezaconsulting.pushay.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.UploadResponse;
import com.cerezaconsulting.pushay.data.entities.UserEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.presentation.activities.EditPasswordActivity;
import com.cerezaconsulting.pushay.presentation.activities.HistoryTravelActivity;
import com.cerezaconsulting.pushay.presentation.contracts.ProfileContract;
import com.cerezaconsulting.pushay.presentation.dialogs.EditDialog;
import com.cerezaconsulting.pushay.utils.BitmapCircleUtil;
import com.cerezaconsulting.pushay.utils.CircleTransform;
import com.cerezaconsulting.pushay.utils.ImagePicker;
import com.cerezaconsulting.pushay.utils.ProgressDialogCustom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by katherine on 19/05/17.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.View, EasyPermissions.PermissionCallbacks {


    private static final int PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 180;
    private static final int PICK_IMAGE_ID = 234;

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
    @BindView(R.id.ly_account)
    LinearLayout lyAccount;
    @BindView(R.id.photo_profile)
    ImageView photoProfile;
    @BindView(R.id.ly_action_edit)
    RelativeLayout lyActionEdit;
    @BindView(R.id.ly_image_profile)
    LinearLayout lyImageProfile;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    private SessionManager mSessionManager;
    private ProfileContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private Bitmap mBitmap;
    private UserEntity userEntity;
    //private ProgressDialogCustom mProgressDialogCustom;

    public ProfileFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Cargando datos...");
        Glide.with(getContext())
                .load(mSessionManager.getUserEntity().getPicture())
                .transform(new CircleTransform(getContext()))
                .into(photoProfile);
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
        newUserEntity.setCellphone(userEntity.getCellphone());
        mSessionManager.setUser(newUserEntity);
        EventBus.getDefault().post(newUserEntity);
        tvName.setText(newUserEntity.getFullName());
        tvNameDetail.setText(newUserEntity.getFullName());
        tvEmailDetail.setText(newUserEntity.getEmail());
        tvCelDetail.setText(newUserEntity.getCellphone());
        //showMessage("Tus datos han sido actualizados con éxito");
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
        if (getView() == null) {
            return;
        }

        if (mProgressDialogCustom != null) {

            if (active) {
                mProgressDialogCustom.show();
            } else {
                if (mProgressDialogCustom.isShowing()) {
                    mProgressDialogCustom.dismiss();
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

    @OnClick({R.id.ly_travel_history, R.id.img_edit, R.id.ly_account, R.id.ly_action_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_travel_history:
                next(getActivity(), null, HistoryTravelActivity.class, false);
                break;
            case R.id.img_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEntity", mSessionManager.getUserEntity());
                EditDialog editDialog = new EditDialog(getContext(), bundle, this);
                editDialog.show();
                break;
            case R.id.ly_account:
                next(getActivity(), null, EditPasswordActivity.class, false);
                break;
            case R.id.ly_action_edit:
                methodRequiresTwoPermission();
                break;
        }
    }

    @AfterPermissionGranted(PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
            startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_camera),
                    PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE, perms);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        methodRequiresTwoPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_ID:
                    mBitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                    ImagePicker.saveToChangesToExternalCacheDirProfile(mBitmap, getActivity(), 100);

                    File image = null;
                    if (mBitmap != null) {
                        image = ImagePicker.getTempFile(getActivity());
                    }
                    mPresenter.updatePhoto(mSessionManager.getUserEntity().getId(), image);
                    break;

            }
        }
    }
    @Override
    public void ShowSessionInformation(UserEntity userEntity) {
        if (userEntity != null) {
            this.userEntity = userEntity;
            Glide.with(getContext())
                    .load(userEntity.getPicture())
                    .transform(new CircleTransform(getContext()))
                    .into(photoProfile);
            tvName.setText(userEntity.getFullName());
            tvNameDetail.setText(userEntity.getFullName());
            tvEmailDetail.setText(userEntity.getEmail());
            tvCelDetail.setText(userEntity.getCellphone());
            /*
            if (AccessToken.getCurrentAccessToken() != null) {

                // if (isOnline()) {

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                //  response.getJSONObject();
                                try {
                                    if (isAdded()) {
                                        JSONObject json_cover = object.getJSONObject("cover");
                                        String source = (String) json_cover.get("source");
                                        Glide.with(getContext()).load(source).into(frontCover);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "cover");
                request.setParameters(parameters);
                request.executeAsync();


            }*/


        } else {
            photoProfile.setBackgroundResource(R.drawable.photo_guide);
            //frontCover.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public void updateProfileImage(UploadResponse body) {

        photoProfile.setImageBitmap(BitmapCircleUtil.getCircularBitmap(mBitmap));
        userEntity.setPicture(body.getPhoto());
        SessionManager sessionManager = new SessionManager(getActivity());
        sessionManager.setUser(userEntity);
        Intent intent =  new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

}
