package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.cerezaconsulting.pushayadmin.data.entities.ReservationEntity;
import com.cerezaconsulting.pushayadmin.presentation.contracts.NoValidatedTravelContract;
import com.cerezaconsulting.pushayadmin.presentation.dialogs.ConfirmedDialog;
import com.cerezaconsulting.pushayadmin.presentation.presenters.NoValidatedTravelPresenter;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by katherine on 26/05/17.
 */

public class ValidateFragment extends BaseFragment implements ZXingScannerView.ResultHandler, EasyPermissions.PermissionCallbacks, NoValidatedTravelContract.View{
    private static final int CAMERA_PERMISSIONS = 123;
    @BindView(R.id.fl_content_frame)
    FrameLayout flContentFrame;
    Unbinder unbinder;


    private boolean isOpenCamera = false;
    private ZXingScannerView mScannerView;

    private NoValidatedTravelContract.Presenter mPresenter;

    public static ValidateFragment newInstance() {
        return new ValidateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_validate, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScannerView = new ZXingScannerView(getContext());
        flContentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        methodRequiresPermissionCamera();
    }

    @AfterPermissionGranted(CAMERA_PERMISSIONS)
    private void methodRequiresPermissionCamera() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            if (!isOpenCamera) {
                initScanner();
                isOpenCamera = true;
            }
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_camera),
                    CAMERA_PERMISSIONS, perms);
        }

    }

    private void initScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == CAMERA_PERMISSIONS) {
            if (!isOpenCamera) {
                initScanner();
                isOpenCamera = true;
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> permsfragment_validate) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, permsfragment_validate)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void handleResult(Result result) {
        //
        System.out.println("RESULT------------------------->" + result.toString());
        Log.e("RESULT", result.toString());
        sendValidateTravelWithQr(Integer.valueOf(result.toString()), true);
        //confirmedDialog();
    }

    private void confirmedDialog(String msg){
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        ConfirmedDialog confirmedDialog = new ConfirmedDialog(getContext(), bundle);
        confirmedDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        confirmedDialog.show();
    }

    @Override
    public void getListTravel(ArrayList<ReservationEntity> list) {

    }

    @Override
    public void showDetailsTravel(ReservationEntity reservationEntity) {

    }

    @Override
    public void sendValidateTravelWithCode(String code, boolean is_confirm) {

    }

    @Override
    public void sendValidateTravelWithQr(int id, boolean is_confirm) {
        mPresenter.validatedTravelWithQr(id, is_confirm);
    }

    @Override
    public void showDetailsValidate(String msg) {
        confirmedDialog(msg);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(NoValidatedTravelContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
