package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.FavoriteInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.listener.FavoriteInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mateja on 22-Nov-17.
 */

public class FavoriteInteractorImpl implements FavoriteInteractor {
    FavoriteInteractorListener listener;

    @Override
    public void setFavoriteListener(FavoriteInteractorListener listener) {
        this.listener=listener;
    }

    @Override
    public void getFavorite(int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<List<EventModel>> call = calls.getFavorites(userId);
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                List<EventModel> list = response.body();
                if(list== null) {
                    listener.noFavorites();
                }
                else {
                    listener.onSuccess(list);
                }

                /*if(response.isSuccessful()){
                    List<EventModel> favorite=response.body();
                    listener.onSuccess(favorite);
                }
                else{
                    Log.d("Api", "fail getFavorites");
                }*/
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }
}
