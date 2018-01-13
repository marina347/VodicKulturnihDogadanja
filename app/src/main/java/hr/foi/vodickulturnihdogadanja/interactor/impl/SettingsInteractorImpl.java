package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.SettingsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.SettingsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mateja on 13-Jan-18.
 */

public class SettingsInteractorImpl implements SettingsInteractor{

    SettingsInteractorListener listener;

    @Override
    public void setSettingsListener(SettingsInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void getSettings(int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<SettingsModel> call = calls.getSettings(userId);
        call.enqueue(new Callback<SettingsModel>() {
            @Override
            public void onResponse(Call<SettingsModel> call, Response<SettingsModel> response) {
                if(response.isSuccessful()){
                    SettingsModel settings=response.body();
                    listener.onSuccess(settings);
                }
                else{
                    Log.d("Api", "fail getSettings");
                }
            }

            @Override
            public void onFailure(Call<SettingsModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
                listener.onFailed("Postavke nisu dohvacene");
            }
        });
    }
}
