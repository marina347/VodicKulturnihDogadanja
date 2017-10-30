package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.adapters.RecyclerAdapter;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.EventView;

public class MainActivity extends AppCompatActivity implements EventView {
    EventPresenter ep;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private List<EventModel> listOfEvents;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventPresenter p=new EventPresenterImpl(new EventInteractorImpl(),this);
        this.ep=p;
        ep.tryGetEvents();
    }

    @Override
    public void Arrived(List<EventModel> list) {
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        lm= new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter=new RecyclerAdapter(list,MainActivity.this);
        rv.setAdapter(adapter);
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
    @Override
    public void NoEvents(final String error) {
        showToastOnUI(error);
    }
}
