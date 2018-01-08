package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingContainer;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManager;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManagerListener;
import hr.foi.vodickulturnihdogadanja.TwitterSharingManager;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;
import hr.foi.voidckulturnihdogadanja.FacebookSharingManager;


public class EventDetailsActivity extends AppCompatActivity implements EventDetailsView,SocialNetworkSharingManagerListener, SocialNetworkSharingContainer {
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
    @BindView(R.id.new_comment_text)
    TextView txtNewComment;
    @BindView(R.id.btn_new_comment)
    Button btnNewComment;
    @BindView(R.id.event_details_img)
    ImageView imgEvent;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.favoriteCheckBox)
    CheckBox favoriteCheckBox;
    @BindView(R.id.btn_twitter)
    Button btnTwitter;

    EventDetailsPresenter dp;
    int eventId=-1;
    SocialNetworkSharingManager shareManager;
    EventModel event;
    TwitterAuthClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(),new FavoriteInteractorImpl(), this);
        this.dp=edp;

        TokenModel token = LoggedUserData.getInstance().getTokenModel();
        if (token != null){
            txtNewComment.setVisibility(View.VISIBLE);
            btnNewComment.setVisibility(View.VISIBLE);
        }
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("v0POVP7nGnIkhBZIs3YAjt8Lr", "AakBcoLnYc4WMzEFyYihwvRZwGynP2R5PP0diMw62L4yP0nEkf"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        client = new TwitterAuthClient();
        //make the call to login

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

    @OnClick(R.id.btn_new_comment)
    public void NewCommentClick(View view){
        AddNewComment();
    }

    @OnClick(R.id.btn_share)
    public void click(){
        shareManager = new FacebookSharingManager();
        initSharing(shareManager);
    }

    private void initSharing(SocialNetworkSharingManager shareManager){
        shareManager.setListener(this);
        shareManager.setContainer(this);
        shareManager.share(this, event.getEventId());
    }

    @OnClick(R.id.btn_twitter)
    public void clickTwitter(){
        shareManager = new TwitterSharingManager();
        initSharing(shareManager);
    }

    private void AddNewComment() {
        String newCommentText = txtNewComment.getText().toString();
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        if (!newCommentText.contentEquals("") && eventId!=-1 && userId!=-1){
            CommentModel commentModel = new CommentModel();
            commentModel.setText(newCommentText);
            commentModel.setTime(new Date().getTime());
            commentModel.setEventId(eventId);
            commentModel.setUserId(userId);
            dp.tryAddNewComment(commentModel);
        }
        else {
            Toast.makeText(this, "Niste unijeli tekst", Toast.LENGTH_LONG).show();
        }
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
        txtEventLink.setText(event.getLink());
        imgEvent.setImageBitmap(Base64Coding.decodeBase64(event.getPicture()));
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        Toast.makeText(this,"Uspješno komentiranje",Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
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
        //client.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        btnTwitter.setVisibility(View.GONE);
        btnShare.setVisibility(View.GONE);
    }

    @Override
    public void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
        btnTwitter.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
    }
}
