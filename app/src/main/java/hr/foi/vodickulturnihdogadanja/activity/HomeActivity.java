package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;

public class HomeActivity extends AppCompatActivity {
    @OnClick(R.id.logintv)
    public void LoginLinkClick(){NavigateToLoginActivity();}
    @OnClick(R.id.guest_tv)
    public void GuestLinkClick(){NavigateToMainActivity();}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
