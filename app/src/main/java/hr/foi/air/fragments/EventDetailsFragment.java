package hr.foi.air.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.SocialNetworkSharingContainer;
import hr.foi.air.SocialNetworkSharingManager;
import hr.foi.air.SocialNetworkSharingManagerListener;
import hr.foi.air.TwitterSharingManager;
import hr.foi.air.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.air.interactor.impl.FavoriteInteractorImpl;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.EventDetailsPresenter;
import hr.foi.air.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.air.utils.Base64Coding;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.utils.Utils;
import hr.foi.air.view.EventDetailsView;
import hr.foi.air.FacebookSharingManager;

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
    @BindView(R.id.event_details_location)
    TextView txtEventLocation;
    @BindView(R.id.event_details_price)
    TextView txtEventPrice;
    @BindView(R.id.event_details_img)
    ImageView imgEvent;
    @BindView(R.id.btn_share)
    ImageButton btnShare;
    @BindView(R.id.favoriteCheckBox)
    CheckBox favoriteCheckBox;
    @BindView(R.id.btn_twitter)
    ImageButton btnTwitter;
    @BindView(R.id.img_like)
    ImageButton imgLike;
    @BindView(R.id.img_dislike)
    ImageButton imgDislike;
    @BindView(R.id.btn_link)
    Button btnLink;
    @BindView(R.id.num_of_dislike)
    TextView txtNumOfDislike;
    @BindView(R.id.num_of_like)
    TextView txtNumOfLike;

    EventDetailsPresenter dp;
    int eventId=-1;
    SocialNetworkSharingManager shareManager;
    EventModel event;
    ProgressDialog nDialog;
    int provjera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        spinnerLoad();
        View rootView = inflater.inflate(R.layout.fragment_details, null);
        //getActivity().setContentView(R.layout.fragment_details); DA LI OVO TREBA?
        ButterKnife.bind(this, rootView);
        LoggedUserData.getInstance();
        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(),new FavoriteInteractorImpl(), this);
        this.dp=edp;
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
        txtEventDescription.setText(getResources().getString(R.string.description) + " " + event.getDescription());
        txtEventBegin.setText(getResources().getString(R.string.begin_end) + " " + DateConverter(event.getBegin()));
        if(event.getEnd()>0){
            txtEventEnd.setText("-"+DateConverter(event.getEnd()));
        }
        else {
            txtEventEnd.setText("");
        }
        if(event.getLink().isEmpty()){
            btnLink.setVisibility(View.INVISIBLE);
        }
        txtEventLocation.setText(getResources().getString(R.string.location) + " " + event.getLocation());
        txtEventPrice.setText(getResources().getString(R.string.price) + " " + String.valueOf(event.getPrice()) + " " + getResources().getString(R.string.valute));
        imgEvent.setImageBitmap(Base64Coding.decodeBase64(event.getPicture()));
        if(LoggedUserData.getInstance().getTokenModel()==null || event.getBegin()>=System.currentTimeMillis() || event.getEnd()>=System.currentTimeMillis()){
            imgLike.setVisibility(View.INVISIBLE);
            imgDislike.setVisibility(View.INVISIBLE);
            txtNumOfDislike.setVisibility(View.INVISIBLE);
            txtNumOfLike.setVisibility(View.INVISIBLE);

        }
        if(LoggedUserData.getInstance().getTokenModel()==null){
            favoriteCheckBox.setVisibility(View.INVISIBLE);
        }
        if(event.getIsFavorite()==1){
            favoriteCheckBox.setChecked(true);
            favoriteCheckBox.setClickable(false);
            favoriteCheckBox.setAlpha(1F);
        }
        else favoriteCheckBox.setAlpha(0.5F);
        setLikeDislikeButtonsAlpha();
        nDialog.dismiss();
        showNumberOfLikes();
    }

    private void setLikeDislikeButtonsAlpha(){

        if(event.getUserEval()==Utils.DISLIKE){
            imgLike.setAlpha(0.2f);
            imgDislike.setAlpha(1f);
        }
        else if(event.getUserEval()==Utils.LIKE){
            imgDislike.setAlpha(0.2f);
            imgLike.setAlpha(1f);
        }
        else{
            imgLike.setAlpha(1f);
            imgDislike.setAlpha(1f);
        }
        showNumberOfLikes();
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
        favoriteCheckBox.setAlpha(1F);
    }

    @Override
    public void onSuccessAddedFavorite() {
        Toast.makeText(getActivity(),R.string.event_added_in_favorite, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessAddedEvaluation() {
        Toast.makeText(getActivity(),R.string.successfully_rated, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedAddedEvaluation() {
        Toast.makeText(getActivity(),R.string.unsuccessfully_rated, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessDeletedEvaluation() {
        //Toast.makeText(getActivity(),"Uspješno uklonjena ocjena!" Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedDeletedEvaluation() {
        //Toast.makeText(getActivity(),"Neuspješno uklonjena ocjena!" Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.img_like)
    public void clickLike(){
        if(this.event.getUserEval()== Utils.LIKE){
            dp.tryDeleteEvaluation(LoggedUserData.getInstance().getTokenModel().getUserId(),eventId);
            this.event.setUserEval(Utils.NOR_LIKED_NOR_DISLIKED);
            event.setNumOfLikes(event.getNumOfLikes()-1);
        }
        else {
            if(event.getUserEval()==Utils.DISLIKE){
                event.setNumOfDislikes(event.getNumOfDislikes()-1);
            }
            this.event.setUserEval(Utils.LIKE);
            dp.tryAddEvaluation(1, LoggedUserData.getInstance().getTokenModel().getUserId(), event.getEventId());
            event.setNumOfLikes(event.getNumOfLikes()+1);
        }
        setLikeDislikeButtonsAlpha();
        showNumberOfLikes();
        //dp.tryGetEventById(eventId);


    }
    @OnClick(R.id.img_dislike)
    public void clickDislike(){

        if(this.event.getUserEval()== Utils.DISLIKE){
            dp.tryDeleteEvaluation(LoggedUserData.getInstance().getTokenModel().getUserId(),eventId);
            this.event.setUserEval(Utils.NOR_LIKED_NOR_DISLIKED);
            event.setNumOfDislikes(event.getNumOfDislikes()-1);
        }
        else{
            if (event.getUserEval() == Utils.LIKE){
                event.setNumOfLikes(event.getNumOfLikes()-1);
            }
            this.event.setUserEval(Utils.DISLIKE);
            dp.tryAddEvaluation(0,LoggedUserData.getInstance().getTokenModel().getUserId(),event.getEventId());
            event.setNumOfDislikes(event.getNumOfDislikes()+1);
        }
        setLikeDislikeButtonsAlpha();
        showNumberOfLikes();
        //dp.tryGetEventById(eventId);


    }

    @Override
    public void shared() {
        Toast.makeText(getActivity(),R.string.successfully_shared, Toast.LENGTH_LONG).show();
    }

    @Override
    public void canceled() {
        Toast.makeText(getActivity(),R.string.unsuccessfully_shared, Toast.LENGTH_LONG).show();
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
    
    @OnClick(R.id.btn_link)
    public void openLink(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLink()));
        startActivity(intent);
    }
    private void spinnerLoad(){
        nDialog = new ProgressDialog( getActivity());
        nDialog.setMessage("Učitavam...");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }
    private void showNumberOfLikes(){
        txtNumOfDislike.setText(String.valueOf(event.getNumOfDislikes()));
        txtNumOfLike.setText(String.valueOf(event.getNumOfLikes()));
    }
}
