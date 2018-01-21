package hr.foi.air.interactor.impl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hr.foi.air.interactor.CallDefinitions;
import hr.foi.air.interactor.FavoriteInteractor;
import hr.foi.air.interactor.RetrofitREST;
import hr.foi.air.interactor.listener.AddFavoriteInteractorListener;
import hr.foi.air.interactor.listener.FavoriteInteractorListener;
import hr.foi.air.model.EventModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mateja on 22-Nov-17.
 */

public class FavoriteInteractorImpl implements FavoriteInteractor {
    FavoriteInteractorListener listener;
    AddFavoriteInteractorListener addListener;

    @Override
    public void setAddFavoriteListener(AddFavoriteInteractorListener listener) {
        this.addListener = listener;
    }

    @Override
    public void addFavorite(int userId, int eventId) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("userId", userId);
            jObj.put("eventId", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), data);
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<String> call = calls.addFavorite(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    addListener.onAddSuccess();
                } else {
                    Log.d("Api", "fail deletefavorite");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    @Override
    public void setFavoriteListener(FavoriteInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void getFavorite(int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<List<EventModel>> call = calls.getFavorites(userId);
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                List<EventModel> list = response.body();
                if (list == null) {
                    listener.noFavorites();
                } else {
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

    @Override
    public void deleteFavorite(final int userId, final int eventId) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("userId", userId);
            jObj.put("eventId", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), data);
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<String> call = calls.deleteFavorite(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    listener.onSuccessDelete();
                } else {
                    Log.d("Api", "fail deletefavorite");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }



}
