package hr.foi.vodickulturnihdogadanja.fragments;

//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.vodickulturnihdogadanja.R;

/**
 * Created by Mateja on 11-Jan-18.
 */

public class EventsFragment extends Fragment {
    public static EventsFragment instance;
    private ActiveEventFragment activeEventFragment;
    private AllEventFragment allEventFragment;
    private TabLayout allTabs;
    public int eventId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_events);
        instance = this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }

    private void getAllWidgets() {
        allTabs = (TabLayout) getActivity().findViewById(R.id.tabs_event);
    }

    private void setupTabLayout() {
        Intent intent = this.getActivity().getIntent();
        eventId = intent.getIntExtra("id", -1);
        Bundle bundle = new Bundle();
        bundle.putInt("eventId", eventId);
        activeEventFragment = new ActiveEventFragment();
        allEventFragment = new AllEventFragment();
        activeEventFragment.setArguments(bundle);
        allEventFragment.setArguments(bundle);
        allTabs.addTab(allTabs.newTab().setText("TRENUTNI DOGAĐAJI"), true);
        allTabs.addTab(allTabs.newTab().setText("SVI DOGAĐAJI"));
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_event, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void setCurrentTabFragment (int tabPosition) {
        //Fragment fragment = null;
        switch (tabPosition)
        {
            case 0 :
                replaceFragment(activeEventFragment);
                //fragment = new ActiveEventFragment();
                break;
            case 1 :
                replaceFragment(allEventFragment);
                break;
        }
    }

    private void bindWidgetsWithAnEvent() {
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
}
