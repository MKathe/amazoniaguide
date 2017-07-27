package com.cerezaconsulting.pushayadmin.presentation.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cerezaconsulting.pushayadmin.data.entities.AccessTokenEntity;
import com.cerezaconsulting.pushayadmin.data.entities.UserEntity;
import com.cerezaconsulting.pushayadmin.data.local.SessionManager;
import com.cerezaconsulting.pushayadmin.data.remote.ServiceFactory;
import com.cerezaconsulting.pushayadmin.data.remote.request.LoginRequest;
import com.cerezaconsulting.pushayadmin.data.remote.request.RegisterRequest;
import com.cerezaconsulting.pushayadmin.presentation.contracts.LoginContract;
import com.cerezaconsulting.pushayadmin.presentation.contracts.RegisterContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 3/05/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private Context context;
    private SessionManager mSessionManager;


    public RegisterPresenter(RegisterContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }

    @Override
    public void registerUser(@NonNull final UserEntity userEntity) {

        RegisterRequest registerRequest =
                ServiceFactory.createService(RegisterRequest.class);

        Call<AccessTokenEntity> call = registerRequest.registerUser(userEntity.getEmail(),
                userEntity.getPassword(), userEntity.getFirst_name(), userEntity.getLast_name(),
                userEntity.getCellphone(), userEntity.getGender());


        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if (response.isSuccessful()) {
                    mView.setLoadingIndicator(false);
                    mView.registerSuccessful(userEntity);
                } else {
                    mView.setLoadingIndicator(false);
                    mView.errorRegister("Falló el registro, por favor inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Ocurrió un error al tratar de ingresar, por favor intente nuevamente");
            }
        });
    }

}
