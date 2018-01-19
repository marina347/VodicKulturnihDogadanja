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
import android.view.MenuItem;

import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.fragments.EventFragment;
import hr.foi.vodickulturnihdogadanja.fragments.FavoriteFragment;
import hr.foi.vodickulturnihdogadanja.fragments.SettingsFragment;
import hr.foi.vodickulturnihdogadanja.fragments.UserProfileFragment;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.utils.LocalHelper;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;


public class NavigationActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //ButterKnife.bind(this);

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_event:
                fragment = new EventFragment();
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
                break;
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
