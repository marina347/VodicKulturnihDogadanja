package hr.foi.air.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import hr.foi.air.R;
import hr.foi.air.adapters.RecyclerAdapter;
import hr.foi.air.interactor.impl.EventInteractorImpl;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.EventPresenter;
import hr.foi.air.presenter.impl.EventPresenterImpl;
import hr.foi.air.view.EventView;

/**
 * Created by Mateja on 17-Nov-17.
 */

public class ActiveEventFragment extends Fragment implements EventView {

    EventPresenter ep;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private List<EventModel> listOfEvents;
    private RecyclerAdapter adapter;

    public void setDataArrived(boolean dataArrived) {
        this.dataArrived = dataArrived;
    }

    public boolean dataArrived;
    List<EventModel> eventsList;
    ProgressDialog nDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(dataArrived){
            setView(eventsList);
        }
        else{
            spinnerLoad();
            ep=new EventPresenterImpl(new EventInteractorImpl(),this);
            ep.tryGetEvents();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    /**
     * Funkcija sluzi za pretrazivanje aktivnih dogadaja.
     * @param searchView
     */
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    /**
     * Funkcija salje listu dogadaju Recycler Adapteru da ih prikaze.
     * @param list
     */
    public void setView(List<EventModel> list){
        rv = (RecyclerView) getView().findViewById(R.id.recycler_view);
        lm= new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter=new RecyclerAdapter(list, getActivity());
        //adapter=new RecyclerAdapter(list,MainActivity.this);
        rv.setAdapter(adapter);
    }
    private void spinnerLoad(){
        nDialog = new ProgressDialog( getActivity());
        nDialog.setMessage(getContext().getResources().getString(R.string.loading_events));
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

    /**
     * Funkcija prihvaca pristigle dogadaje.
     * @param list
     */
    @Override
    public void Arrived(List<EventModel> list) {
        nDialog.dismiss();
        this.eventsList= list;
        setView(this.eventsList);
        dataArrived = true;

    }

    /**
     * Funkcija prikazuje unesenu poruku.
     * @param msg
     */
    private void showToastOnUI(final String msg){
        final Context ctx = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Funkcija ispisuje poruku o nepostojanju dogadaja.
     * @param error
     */
    @Override
    public void NoEvents(final String error) {
        showToastOnUI(error);
    }
}
