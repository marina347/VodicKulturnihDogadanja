package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.fragments.EventFragment;
import hr.foi.vodickulturnihdogadanja.fragments.FavoriteFragment;
import hr.foi.vodickulturnihdogadanja.fragments.SettingsFragment;
import hr.foi.vodickulturnihdogadanja.fragments.UserProfileEditFragment;
import hr.foi.vodickulturnihdogadanja.fragments.UserProfileFragment;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LocalHelper;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;


public class NavigationActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    TextView txtEmail;
    TextView txtName;
    ImageView imgUser;
    Menu menu;
    @BindView(R.id.nav_view)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.no_title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_event);

        View headerLayout = navView.getHeaderView(0);
        txtEmail = headerLayout.findViewById(R.id.email_d);
        txtName = headerLayout.findViewById(R.id.name_surname_d);
        imgUser= headerLayout.findViewById(R.id.img_user);
        txtEmail.setText(LoggedUserData.getInstance().getEmail()+"");
        txtName.setText(LoggedUserData.getInstance().getName()+" "+LoggedUserData.getInstance().getSurname());
        if(LoggedUserData.getInstance().getImage()!=null) imgUser.setImageBitmap(Base64Coding.decodeBase64(LoggedUserData.getInstance().getImage()));
    }

    @Override
    public void onBackPressed() {

        EventFragment eventFragment = (EventFragment)getSupportFragmentManager().findFragmentByTag("event_fragment");
        Fragment editProfileFragment = (UserProfileEditFragment)getSupportFragmentManager().findFragmentByTag("edit_profile_fragment");
        //Fragment eventFragment = new EventFragment();
        //Fragment editProfileFragment = new UserProfileEditFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // if drawer open, close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //if event fragment visible, got out from app
        else if (eventFragment != null && eventFragment.isVisible()){
            finishAffinity();
        }
        //go on event fragment
        /*else {
            Fragment someFragment = new EventFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, someFragment, "event");
            transaction.addToBackStack("event");
            transaction.commit();
            super.onBackPressed();
        }*/
    }

    private void f() {
        Fragment someFragment = new EventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment, "event");
        transaction.addToBackStack("event");
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu=menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        if(menu != null) {
            menu.findItem(R.id.search).setVisible(false);
        }
        switch (itemId) {
            case R.id.nav_event:
                fragment = new EventFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment, "event_fragment");
                transaction.commit();
                break;
            case R.id.nav_profile:
                fragment = new UserProfileFragment();
                break;
            case R.id.nav_favorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_logout:
                UserInteractorImpl inter=new UserInteractorImpl();
                inter.logOut(this, LoggedUserData.getInstance().getTokenModel().getTokenId());
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                finish();
                return;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }

}
