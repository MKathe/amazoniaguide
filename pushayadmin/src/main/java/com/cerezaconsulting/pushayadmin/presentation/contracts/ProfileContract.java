package com.cerezaconsulting.pushayadmin.presentation.contracts;
import com.cerezaconsulting.pushayadmin.core.BasePresenter;
import com.cerezaconsulting.pushayadmin.core.BaseView;
import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UploadResponse;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;

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
