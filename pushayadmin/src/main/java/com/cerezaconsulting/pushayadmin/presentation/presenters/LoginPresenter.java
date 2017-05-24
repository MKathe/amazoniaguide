package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.LoginRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.LoginContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by katherine on 10/05/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final SessionManager mSessionManager;

    public LoginPresenter(@NonNull LoginContract.View mView, Context context) {
        this.mView = mView;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);

    }

    @Override
    public void loginUser(String username, String password) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<AccessTokenEntity> call = loginService.login(username,password);
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if(!mView.isActive()){
                    return;
                }
                if (response.isSuccessful()) {
                    getPerfil(response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showMessage("login fallido");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                if(!mView.isActive()){
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("No se puede conectar al servidor");
            }
        });
    }

    public void getPerfil(final AccessTokenEntity accessTokenEntity) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginService.getUser("Token "+accessTokenEntity.getAccessToken());
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    mView.setLoadingIndicator(false);
                    mSessionManager.openSession(accessTokenEntity);
                    mSessionManager.setUser(response.body());
                    mView.loginSucessful();

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showMessage("Perfil fallido");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("No se puede conectar al servidor");
            }
        });
    }

    @Override
    public void start() {

    }
}
