package hr.foi.vodickulturnihdogadanja.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.CommentPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;
import hr.foi.voidckulturnihdogadanja.FacebookSharingManager;

/**
 * Created by LEGION Y520 on 10.1.2018..
 */

public class EventDetailsFragment extends Fragment implements EventDetailsView,SocialNetworkSharingManagerListener, SocialNetworkSharingContainer{
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
    @BindView(R.id.btn_twitter)
    Button btnTwitter;
    @BindView(R.id.img_like)
    ImageView imgLike;
    @BindView(R.id.img_dislike)
    ImageView imgDislike;

    EventDetailsPresenter dp;
    int eventId=-1;
    SocialNetworkSharingManager shareManager;
    EventModel event;
    TwitterAuthClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, null);
        FacebookSdk.sdkInitialize(this.getActivity());
        //getActivity().setContentView(R.layout.fragment_details); DA LI OVO TREBA?
        ButterKnife.bind(this, rootView);

        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(),new FavoriteInteractorImpl(), this);
        this.dp=edp;

        TwitterConfig config = new TwitterConfig.Builder(getActivity())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("v0POVP7nGnIkhBZIs3YAjt8Lr", "AakBcoLnYc4WMzEFyYihwvRZwGynP2R5PP0diMw62L4yP0nEkf"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        client = new TwitterAuthClient();
        //make the call to login

        shareManager = new FacebookSharingManager();
        shareManager.setListener(this);
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        eventId=this.getArguments().getInt("eventId",-1);
        if(eventId != -1)
        {
            dp.tryGetEventById(eventId);
        }
    }

    @OnClick(R.id.btn_share)
    public void click(){
        shareManager = new FacebookSharingManager();
        initSharing(shareManager);
    }

    private void initSharing(SocialNetworkSharingManager shareManager){
        shareManager.setListener(this);
        shareManager.setContainer(this);
        shareManager.share(getActivity(), event.getEventId());
    }

    @OnClick(R.id.btn_twitter)
    public void clickTwitter(){
        shareManager = new TwitterSharingManager();
        initSharing(shareManager);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        Toast.makeText(getActivity(),"Dogadaj dodan u favorite!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessAddedEvaluation() {
        Toast.makeText(getActivity(),"Uspjesno ocijenjeno!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedAddedEvaluation() {
        Toast.makeText(getActivity(),"Neuspjesno ocijenjeno!", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.img_like)
    public void clickLike(){
        dp.tryAddEvaluation(1,LoggedUserData.getInstance().getTokenModel().getUserId(),event.getEventId());
    }
    @OnClick(R.id.img_dislike)
    public void clickDislike(){
        dp.tryAddEvaluation(0,LoggedUserData.getInstance().getTokenModel().getUserId(),event.getEventId());
    }

    @Override
    public void shared() {
        Toast.makeText(getActivity(),"Uspjesno podijeljeno", Toast.LENGTH_LONG).show();
    }

    @Override
    public void canceled() {
        Toast.makeText(getActivity(),"Niste podijelili!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        btnTwitter.setVisibility(View.GONE);
        btnShare.setVisibility(View.GONE);
    }

    @Override
    public void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
        btnTwitter.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
    }
}
