package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marbulic on 10/21/2017.
 */

public class UserInteractorImpl implements UserInteractor {
    UserInteractorListener listener;

    @Override
    public void setListener(UserInteractorListener listener){
        this.listener = listener;
    }

    @Override
    public void createUser(UserModel userData) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<UserModel> call = calls.createUser(userData);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel user=response.body();
                    listener.OnSuccess(user);
                }
                else{
                    Log.d("Api", "fail createUser");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
        //call.enqueue();

    }
}
