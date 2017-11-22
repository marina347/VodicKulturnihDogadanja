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
import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.FavoritePresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.FavoritePresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.FavoriteView;

/**
 * Created by Mateja on 22-Nov-17.
 */

public class FavoriteFragment extends Fragment implements FavoriteView {

    int userId = LoggedUserData.getInstance().getTokenModel().getUserId();

    FavoritePresenter favoritePresenter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;


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
    }

    @Override
    public void onSuccess(List<EventModel> list) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter=new RecyclerAdapter(list, getActivity());
        //adapter=new RecyclerAdapter(list,MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void noFavorites(final String error) {
        showToastOnUI(error);
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
}
