package hr.foi.air.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import hr.foi.air.R;
import hr.foi.air.fragments.CommentsFragment;
import hr.foi.air.fragments.EventDetailsFragment;
import hr.foi.air.fragments.MapFragment;
import hr.foi.air.utils.LocalHelper;


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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        instance=this;
        fragmentComments=null;
        fragmentDetails=null;
        fragmentMap=null;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }

    /**
     * Funkcija postavlja tabove koji ce biti prikazani.
     */
    private void setupTabLayout() {
        Intent intent = this.getIntent();
        eventId=intent.getIntExtra("id", -1);
        Bundle bundle = new Bundle();
        bundle.putInt("eventId", eventId);
        bundle.putString("location", intent.getStringExtra("location"));
        if(fragmentDetails==null)fragmentDetails = new EventDetailsFragment();
        if(fragmentComments==null) {
            fragmentComments = new CommentsFragment();
        }
        if(fragmentMap==null) {
            fragmentMap = new MapFragment();
        }
        fragmentDetails = new EventDetailsFragment();
        fragmentComments = new CommentsFragment();
        fragmentMap = new MapFragment();
        fragmentComments.setArguments(bundle);
        fragmentDetails.setArguments(bundle);
        allTabs.addTab(allTabs.newTab().setText(R.string.details),true);
        allTabs.addTab(allTabs.newTab().setText(R.string.comments));
        allTabs.addTab(allTabs.newTab().setText(R.string.map));
        fragmentMap.setArguments(bundle);
    }

    /**
     * Funkcija ostvaruje interakciju s tabovima.
     */
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

    /**
     * Funkcija prikazuje odabrani tab/fragment.
     * @param tabPosition
     */
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

    /**
     * Funkcija zamjenjuje tabove/fragmente.
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        lastFragment = fragment;
    }

    /**
     * Funkcija postavlja zadnji fragment.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(lastFragment!=null&&lastFragment instanceof EventDetailsFragment && data!=null){
            lastFragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }
}
