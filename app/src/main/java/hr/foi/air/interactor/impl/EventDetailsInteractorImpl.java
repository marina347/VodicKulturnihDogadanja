package hr.foi.air.interactor.impl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import hr.foi.air.interactor.CallDefinitions;
import hr.foi.air.interactor.EventDetailsInteractor;
import hr.foi.air.interactor.RetrofitREST;
import hr.foi.air.interactor.listener.EventDetailsInteractorListener;
import hr.foi.air.model.EventModel;
import hr.foi.air.utils.LoggedUserData;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public class EventDetailsInteractorImpl implements EventDetailsInteractor {
    EventDetailsInteractorListener eventDetailsInteractorListener;
    @Override
    public void setEventDetailsListener(EventDetailsInteractorListener listener) {
        this.eventDetailsInteractorListener=listener;
    }

    @Override
    public void getEventById(int eventId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Date currentDate=new Date();
        Call<EventModel> call;
        if( LoggedUserData.getInstance().getTokenModel()!=null){
            int userId=LoggedUserData.getInstance().getTokenModel().getUserId();
            call=calls.getEventById(eventId,userId);
        }
        else call=calls.getEventById(eventId);
        call.enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                EventModel event=response.body();
                eventDetailsInteractorListener.ArrivedEventById(event);
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });

    }

    @Override
    public void addEvaluation(int mark, int userId, int eventId) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("mark",mark);
            jObj.put("userId", userId);
            jObj.put("eventId", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), data);
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<String> call = calls.addEvaluation(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    eventDetailsInteractorListener.successAddedEvaluation();
                } else {
                    eventDetailsInteractorListener.failedAddedEvaluation();
                    Log.d("Api", "fail addEvaluation");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    @Override
    public void deleteEvaluation(int userId, int eventId) {
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
        Call<String> call = calls.deleteEvaluation(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    eventDetailsInteractorListener.onSuccessDeletedEvaluation();
                } else {
                    eventDetailsInteractorListener.onFailedDeletedEvaluation();
                    Log.d("Api", "fail addEvaluation");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }
}
