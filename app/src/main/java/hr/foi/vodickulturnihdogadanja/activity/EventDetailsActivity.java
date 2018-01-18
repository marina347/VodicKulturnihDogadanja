package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import hr.foi.vodickulturnihdogadanja.fragments.CommentsFragment;
import hr.foi.vodickulturnihdogadanja.fragments.EventDetailsFragment;
import hr.foi.vodickulturnihdogadanja.fragments.MapFragment;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.CommentPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;
import hr.foi.voidckulturnihdogadanja.FacebookSharingManager;


public class EventDetailsActivity extends AppCompatActivity {
    public static EventDetailsActivity instance;
    private CommentsFragment fragmentComments;
    private EventDetailsFragment fragmentDetails;
    private MapFragment fragmentMap;
    private TabLayout allTabs;
    private Fragment lastFragment;
    public int eventId=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }
    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        Intent intent = this.getIntent();
        eventId=intent.getIntExtra("id", -1);
        Bundle bundle = new Bundle();
        bundle.putInt("eventId", eventId);
        bundle.putString("location", intent.getStringExtra("location"));
        fragmentDetails = new EventDetailsFragment();
        fragmentComments = new CommentsFragment();
        fragmentMap = new MapFragment();
        fragmentComments.setArguments(bundle);
        fragmentDetails.setArguments(bundle);
        fragmentMap.setArguments(bundle);
        allTabs.addTab(allTabs.newTab().setText("DETALJI"),true);
        allTabs.addTab(allTabs.newTab().setText("KOMENTARI"));
        allTabs.addTab(allTabs.newTab().setText("MAPA"));
    }
    private void bindWidgetsWithAnEvent()
    {
        allTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragment(fragmentDetails);
                break;
            case 1 :
                replaceFragment(fragmentComments);
                break;
            case 2:
                replaceFragment(fragmentMap);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        lastFragment = fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(lastFragment!=null&&lastFragment instanceof EventDetailsFragment){
            lastFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
