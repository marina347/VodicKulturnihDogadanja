package hr.foi.air.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.utils.LoggedUserData;

/**
 * Klasa prikazuje početnu aktivnost aplikacije
 */

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

    /**
     * Metoda za otvaranje aktivnosti za login
     */
    private void NavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    /**
     * Metoda za otvaranje aktivnosti za neregistriranog korisnika
     */
    private void NavigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Metoda koja dohvaća lokalno stanje (jezik) koje je prethodno odabrano pomoću LocalHelper klase
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }
}
