package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.EventDetailsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.listener.EventDetailsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
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
        Call<EventModel> call = calls.getEventById(eventId);
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
}
