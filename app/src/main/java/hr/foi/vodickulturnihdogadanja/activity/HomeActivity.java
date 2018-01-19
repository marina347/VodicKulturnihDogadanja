package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.utils.LocalHelper;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;

public class HomeActivity extends AppCompatActivity {
    @OnClick(R.id.btn_login)
    public void LoginLinkClick(){NavigateToLoginActivity();}
    @OnClick(R.id.guest_tv)
    public void GuestLinkClick(){
        LoggedUserData.setInstanceToNull();
        NavigateToMainActivity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }



    private void NavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



    private void NavigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }
}
