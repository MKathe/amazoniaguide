package com.cerezaconsulting.pushay.presentation.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cerezaconsulting.pushay.data.entities.UserEntity;
import com.cerezaconsulting.pushay.data.remote.ServiceFactory;
import com.cerezaconsulting.pushay.data.remote.request.LoginRequest;
import com.cerezaconsulting.pushay.presentation.contracts.EditPasswordContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by junior on 01/06/16.
 */
public class EditPasswordPresenter implements EditPasswordContract.Presenter {
    private final EditPasswordContract.View view;
    private Context context;


    public EditPasswordPresenter(@NonNull EditPasswordContract.View view, @NonNull Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.view = checkNotNull(view, "view cannot be null!");
        this.view.setPresenter(this);
        }



        @Override
        public void start () {

        }

    @Override
    public void updatePassword(@NonNull String token, @NonNull String old_password, @NonNull String new_password, @NonNull String email) {
        LoginRequest loginRequest =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginRequest.changePassword("Token "+token,old_password,
                new_password,email);
        view.setLoadingIndicator(true);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(response.isSuccessful()){
                    view.showUpdateSucces();
                }else {
                    view.setLoadingIndicator(false);
                    view.showErrorMessage("Falló algo al cambiar su contraseña, por favor inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.showErrorMessage("Ocurrió un error al tratar de cambiar su contraseña, por favor intente nuevamente");
            }

        });
    }
}
