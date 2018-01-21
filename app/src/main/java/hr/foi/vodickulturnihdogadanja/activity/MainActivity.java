package hr.foi.vodickulturnihdogadanja.activity;

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

import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.adapters.RecyclerAdapter;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LocalHelper;
import hr.foi.vodickulturnihdogadanja.view.EventView;

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
    private void showToastOnUI(final String msg){
        final Context ctx = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void spinnerLoad(){
        nDialog = new ProgressDialog(this);
        nDialog.setMessage(this.getResources().getString(R.string.loading_events));
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

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
