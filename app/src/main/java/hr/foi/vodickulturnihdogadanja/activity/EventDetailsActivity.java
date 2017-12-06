package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;

public class EventDetailsActivity extends AppCompatActivity implements EventDetailsView{
    @BindView(R.id.event_details_name)
    TextView txtEventName;
    @BindView(R.id.event_details_description)
    TextView txtEventDescription;
    @BindView(R.id.event_details_begin)
    TextView txtEventBegin;
    @BindView(R.id.event_details_end)
    TextView txtEventEnd;
    @BindView(R.id.event_details_link)
    TextView txtEventLink;
    @BindView(R.id.event_details_img)
    ImageView imgEvent;

    EventDetailsPresenter dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(), this);
        this.dp=edp;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        int eventId = intent.getIntExtra("id",-1);
        if(eventId != -1)
        {
            dp.tryGetEventById(eventId);
        }
    }

    @Override
    public void ArrivedEvent(EventModel event) {
        txtEventName.setText(event.getName());
        txtEventDescription.setText(event.getDescription());
        txtEventBegin.setText(DateConverter(event.getBegin()));
        txtEventEnd.setText(DateConverter(event.getEnd()));
        txtEventLink.setText(event.getLink());
        imgEvent.setImageBitmap(Base64Coding.decodeBase64(event.getPicture()));
    }
    private String DateConverter(Long date) {
        if (date == 0) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
            Date d = new Date(date);
            return df.format(d);
        }
    };
}
