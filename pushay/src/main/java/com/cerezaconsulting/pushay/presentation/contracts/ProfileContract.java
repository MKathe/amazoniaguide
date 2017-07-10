package com.cerezaconsulting.pushay.presentation.contracts;

import com.cerezaconsulting.pushay.core.BasePresenter;
import com.cerezaconsulting.pushay.core.BaseView;
import com.cerezaconsulting.pushay.data.entities.UploadResponse;
import com.cerezaconsulting.pushay.data.entities.UserEntity;

import java.io.File;

/**
 * Created by katherine on 21/06/17.
 */

public interface ProfileContract {
    interface View extends BaseView<Presenter> {
        void updateUser(UserEntity userEntity);
        void editSuccessful(UserEntity userEntity);

        void ShowSessionInformation(UserEntity userEntity);
        void updateProfileImage(UploadResponse body);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void editUser(UserEntity userEntity, String token);
        void updatePhoto(int id, File image);
    }
}
