package hr.foi.air.activity;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.R;
import hr.foi.air.fragments.EventFragment;
import hr.foi.air.fragments.FavoriteFragment;
import hr.foi.air.fragments.SettingsFragment;
import hr.foi.air.fragments.UserProfileEditFragment;
import hr.foi.air.fragments.UserProfileFragment;
import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.utils.Base64Coding;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.utils.LoggedUserData;


public class NavigationActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    TextView txtEmail;
    TextView txtName;
    ImageView imgUser;
    List<Integer> fragmentStack;
    int lastFragmentId;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        fragmentStack = new ArrayList<>(4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.no_title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initDrawerAndFragments();

        View headerLayout = navView.getHeaderView(0);
        txtEmail = headerLayout.findViewById(R.id.email_d);
        txtName = headerLayout.findViewById(R.id.name_surname_d);
        imgUser= headerLayout.findViewById(R.id.img_user);
        txtEmail.setText(LoggedUserData.getInstance().getEmail()+"");
        txtName.setText(LoggedUserData.getInstance().getName()+" "+LoggedUserData.getInstance().getSurname());
        if(LoggedUserData.getInstance().getImage()!=null) imgUser.setImageBitmap(Base64Coding.decodeBase64(LoggedUserData.getInstance().getImage()));
    }

    private void initDrawerAndFragments(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_event);
        lastFragmentId = R.id.nav_event;
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            showLastFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void addFragmentToStack(int id){
        if(fragmentStack.contains(id)) {
            removeFromStack(id);//remove from "previous place"
        }
        fragmentStack.add(id);//add to the last
    }

    private void showLastFragment(){
        if(fragmentStack.size() != 0){
            //get last fragment
            int fragmentId = fragmentStack.get(fragmentStack.size()-1);
            //remove from list
            fragmentStack.remove(fragmentStack.size()-1);
            //show
            displaySelectedScreen(fragmentId);
            navView.setCheckedItem(fragmentId);

        }
    }

    private void removeFromStack(int id){
        int index = fragmentStack.indexOf(new Integer(id));
        fragmentStack.remove(index);//remove from "previous place"
    }


    private void displaySelectedScreen(int itemId) {
        View headerLayout = navView.getHeaderView(0);
        imgUser= headerLayout.findViewById(R.id.img_user);
        imgUser.setImageBitmap(Base64Coding.decodeBase64(LoggedUserData.getInstance().getImage()));
        if(fragmentStack.contains(itemId)) {
            removeFromStack(itemId);
        }
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_event:
                fragment = new EventFragment();
                lastFragmentId = R.id.nav_event;
                break;
            case R.id.nav_profile:
                fragment = new UserProfileFragment();
                lastFragmentId = R.id.nav_profile;
                break;
            case R.id.nav_favorite:
                fragment = new FavoriteFragment();
                lastFragmentId = R.id.nav_favorite;
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                lastFragmentId = R.id.nav_settings;
                break;
            case R.id.nav_logout:
                UserInteractorImpl inter=new UserInteractorImpl();
                inter.logOut(this, LoggedUserData.getInstance().getTokenModel().getTokenId());
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                finish();
                return;
        }

        showFragment(fragment);
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id != lastFragmentId){
            addFragmentToStack(lastFragmentId);
            displaySelectedScreen(id);
        }
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }

}
