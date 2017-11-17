package hr.foi.vodickulturnihdogadanja.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.adapters.RecyclerAdapter;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.EventView;

/**
 * Created by Mateja on 17-Nov-17.
 */

public class EventFragment extends Fragment implements EventView {

    EventPresenter ep;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private List<EventModel> listOfEvents;
    private RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        EventPresenter p=new EventPresenterImpl(new EventInteractorImpl(),this);
        this.ep=p;
        ep.tryGetEvents();
    }

    @Override
    public void Arrived(List<EventModel> list) {
        rv = (RecyclerView) getView().findViewById(R.id.recycler_view);
        lm= new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter=new RecyclerAdapter(list, getActivity());
        //adapter=new RecyclerAdapter(list,MainActivity.this);
        rv.setAdapter(adapter);
    }
    private void showToastOnUI(final String msg){
        final Context ctx = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void NoEvents(final String error) {
        showToastOnUI(error);
    }
}
