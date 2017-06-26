package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.LoginRequest;
import com.cerezaconsulting.pushayadmin.data.remote.request.RegisterRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.ProfileContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 21/06/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public ProfilePresenter(@NonNull ProfileContract.View view, @NonNull Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(view, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(context);

    }
    @Override
    public void editUser(final UserEntity userEntity, String token) {
        RegisterRequest registerRequest =
                ServiceFactory.createService(RegisterRequest.class);
        Call<UserEntity> call = registerRequest.editUser("Token "+token,userEntity.getFirst_name(),
                userEntity.getLast_name());
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(response.isSuccessful()){
                    mView.editSuccessful(userEntity);
                    //getProfile(token);
                }else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Falló algo actualizando sus datos, por favor inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Ocurrió un error al tratar de actualizar sus datos, por favor intente nuevamente");
            }
        });
    }

    @Override
    public void start() {

    }

}
