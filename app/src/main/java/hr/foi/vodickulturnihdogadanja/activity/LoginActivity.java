package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void NavigateToRegistration(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.registerlink_tv)
    public void RegisterLinkClick(View view){
        NavigateToRegistration();
    }

}
