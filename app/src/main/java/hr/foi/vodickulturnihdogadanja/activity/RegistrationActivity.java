package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import android.os.Handler;
import hr.foi.vodickulturnihdogadanja.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void OnSuccess(){
        Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT);
        final Context ctx= this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ctx, LoginActivity.class);
                startActivity(intent);
            }
        },2000);
    }
}
