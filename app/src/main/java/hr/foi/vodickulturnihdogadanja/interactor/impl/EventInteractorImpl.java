package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import java.util.Date;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.EventDetailsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.listener.EventDetailsInteractorListener;
import hr.foi.vodickulturnihdogadanja.interactor.listener.EventInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class EventInteractorImpl implements EventInteractor{
    EventInteractorListener eventInteractorListener;

    @Override
    public void setEventListener(EventInteractorListener listener) {
        this.eventInteractorListener=listener;
    }

    @Override
    public void getEvent() {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Date currentDate=new Date();
        Call<List<EventModel>> call = calls.getEvents(currentDate.getTime());
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                List<EventModel> list=response.body();
                if(list==null){
                    eventInteractorListener.NoEvents();
                }else {
                    eventInteractorListener.ArrivedEvents(list);
                }

            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });

    }
}
