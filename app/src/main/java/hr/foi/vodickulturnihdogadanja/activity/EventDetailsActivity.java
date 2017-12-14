package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManager;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManagerListener;
import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;
import hr.foi.voidckulturnihdogadanja.FacebookSharingManager;

public class EventDetailsActivity extends AppCompatActivity implements EventDetailsView,SocialNetworkSharingManagerListener {
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
    @BindView(R.id.event_details_location)
    TextView txtEventLocation;
    @BindView(R.id.event_details_price)
    TextView txtEventPrice;
    @BindView(R.id.event_details_img)
    ImageView imgEvent;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.favoriteCheckBox)
    CheckBox favoriteCheckBox;

    EventDetailsPresenter dp;
    int eventId=-1;
    SocialNetworkSharingManager shareManager;
    EventModel event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(),new FavoriteInteractorImpl(), this);
        this.dp=edp;

        shareManager = new FacebookSharingManager();
        shareManager.setListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        eventId = intent.getIntExtra("id",-1);
        if(eventId != -1)
        {
            dp.tryGetEventById(eventId);
        }
    }

    @OnClick(R.id.btn_share)
    public void click(){
        //get event id
        shareManager.share(this, event.getEventId());
    }

    @Override
    public void ArrivedEvent(EventModel event) {
        this.event=event;
        txtEventName.setText(event.getName());
        txtEventDescription.setText(event.getDescription());
        txtEventBegin.setText(DateConverter(event.getBegin()));
        if(event.getEnd()>0){
            txtEventEnd.setText("-"+DateConverter(event.getEnd()));
        }
        else {
            txtEventEnd.setText("");
        }

        txtEventLocation.setText(event.getLocation());
        txtEventPrice.setText(String.valueOf(event.getPrice()));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareManager.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.favoriteCheckBox)
    public void favoriteCheckBoxClick(){
        this.dp.tryAddFavorite(this.event.getEventId());
    }

    @Override
    public void onSuccessAddedFavorite() {
        Toast.makeText(this,"Dogadaj dodan u favorite!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void shared() {
        Toast.makeText(this,"Uspjesno podijeljeno", Toast.LENGTH_LONG).show();
    }

    @Override
    public void canceled() {
        Toast.makeText(this,"Niste podijelili!", Toast.LENGTH_LONG).show();
    }
}
