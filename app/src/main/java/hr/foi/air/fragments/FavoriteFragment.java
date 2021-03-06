package hr.foi.air.fragments;

import android.app.ProgressDialog;
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

import hr.foi.air.R;
import hr.foi.air.adapters.FavoriteEventsAdapter;
import hr.foi.air.interactor.impl.FavoriteInteractorImpl;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.FavoritePresenter;
import hr.foi.air.presenter.impl.FavoritePresenterImpl;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.FavoriteView;

/**
 * Klasa za prikaz liste favorita
 * Created by Mateja on 22-Nov-17.
 */

public class FavoriteFragment extends Fragment implements FavoriteView {

    int userId = LoggedUserData.getInstance().getTokenModel().getUserId();

    FavoritePresenter favoritePresenter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteEventsAdapter recyclerAdapter;
    private List<EventModel> eventList;
    ProgressDialog nDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        FavoritePresenter presenter = new FavoritePresenterImpl(new FavoriteInteractorImpl(), this);
        this.favoritePresenter = presenter;
        favoritePresenter.tryGetFavorites(userId);
        spinnerLoad();
    }

    /**
     * Metoda za slanje liste događaja adapteru kako bi ih on ispisao
     * @param list
     */
    @Override
    public void onSuccess(List<EventModel> list) {
        eventList = list;
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter=new FavoriteEventsAdapter(list, getActivity(), this);
        //adapter=new RecyclerAdapter(list,MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);
        nDialog.dismiss();
    }

    @Override
    public void noFavorites(final String error) {
        showToastOnUI(error);
    }

    @Override
    public void onSuccessDelete() {

    }

    private void showToastOnUI(final String msg) {
        final Context ctx = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metoda za ucitavanje dogadaja
     */
    private void spinnerLoad(){
        nDialog = new ProgressDialog( getActivity());
        nDialog.setMessage(getContext().getResources().getString(R.string.loading_events));
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

    /**
     * Metoda za brisanje događaja iz favorita
     * @param eventModel
     * @param clickedItemPosition
     */
    public void onEventRemoveFavorite(EventModel eventModel, int clickedItemPosition){
        int eventId = eventModel.getEventId();
        recyclerView.removeViewAt(clickedItemPosition);
        eventList.remove(clickedItemPosition);
        recyclerView.getAdapter().notifyItemRemoved(clickedItemPosition);
        recyclerView.getAdapter().notifyItemRangeChanged(clickedItemPosition, eventList.size());
        favoritePresenter.tryDeleteFavorite(LoggedUserData.getInstance().getTokenModel().getUserId(), eventId);
    }
}
