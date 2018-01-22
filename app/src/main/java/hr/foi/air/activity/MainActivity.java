package hr.foi.air.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import hr.foi.air.R;
import hr.foi.air.adapters.RecyclerAdapter;
import hr.foi.air.interactor.impl.EventInteractorImpl;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.EventPresenter;
import hr.foi.air.presenter.impl.EventPresenterImpl;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.view.EventView;

public class MainActivity extends AppCompatActivity implements EventView {
    EventPresenter ep;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private RecyclerAdapter adapter;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle(R.string.no_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);
        EventPresenter p=new EventPresenterImpl(new EventInteractorImpl(),this);
        this.ep=p;
        ep.tryGetEvents();
        spinnerLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    /**
     * Funkcija sljuži za pretraživanje Recycler adaptera.
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
     * Funkcija šalje pristigle događaje Recycler adapteru da ih prikaže.
     * @param list
     */
    @Override
    public void Arrived(List<EventModel> list) {
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        lm= new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter=new RecyclerAdapter(list,MainActivity.this);
        rv.setAdapter(adapter);
        nDialog.dismiss();
    }

    /**
     * Funkcija ispisuje unesenu poruku.
     * @param msg
     */
    private void showToastOnUI(final String msg){
        final Context ctx = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Funkcija prikazuje učitavanje.
     */
    private void spinnerLoad(){
        nDialog = new ProgressDialog(this);
        nDialog.setMessage(this.getResources().getString(R.string.loading_events));
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

    /**
     * Funkcija ispisuje poruku kada o nepostojanju događaja.
     * @param error
     */
    @Override
    public void NoEvents(final String error) {
        showToastOnUI(error);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
