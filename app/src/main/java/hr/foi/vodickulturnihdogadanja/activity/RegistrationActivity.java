package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import android.os.Handler;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.RegistrationPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.RegistrationPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.RegistrationView;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{
    RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        RegistrationPresenter presenter = new RegistrationPresenterImpl(new UserInteractorImpl(), this);
        this.presenter = presenter;
    }

    private void TryCreateUser(){
        /*
        UserModel um = new UserModel();
        um.setName("NAZIV_NEKI");
        um.setPassword("asdddd");
        um.setPicture("ddddd");
        um.setSurname("surnejm");
        um.setUsername("ffff");
        um.setEmail("moj@ffff.com");

        presenter.tryCreateUser(um);
        */
    }

    public void onSuccess(UserModel userModel){
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
