package hr.foi.air.interactor.impl;

import android.util.Log;

import hr.foi.air.interactor.CallDefinitions;
import hr.foi.air.interactor.RetrofitREST;
import hr.foi.air.interactor.SettingsInteractor;
import hr.foi.air.interactor.listener.SettingsInteractorListener;
import hr.foi.air.model.SettingsModel;
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

    @Override
    public void editSettings(SettingsModel settings) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<SettingsModel> call = calls.editSettings(settings);
        call.enqueue(new Callback<SettingsModel>() {
            @Override
            public void onResponse(Call<SettingsModel> call, Response<SettingsModel> response) {
                if(response.isSuccessful()){
                    SettingsModel settings=response.body();
                    listener.onSuccess(settings);
                }
                else{
                    Log.d("Api", "fail editSettings");
                }
            }

            @Override
            public void onFailure(Call<SettingsModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
                listener.onFailed("Postavke nisu promjenjene");
            }
        });
    }
}
