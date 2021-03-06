package hr.foi.air.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.model.UserModel;
import hr.foi.air.presenter.RegistrationPresenter;
import hr.foi.air.presenter.impl.RegistrationPresenterImpl;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.view.RegistrationView;

/**
 * Aktivnost za registraciju korisnika.
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    @BindView(R.id.btnRegister)
    Button button1;

    @BindView(R.id.reg_username_et)
    EditText usernameEditText;

    @BindView(R.id.reg_password_et)
    EditText passwordEditText;

    @BindView(R.id.reg_email_et)
    EditText emailEditText;

    @BindView(R.id.reg_name_et)
    EditText nameEditText;

    @BindView(R.id.reg_surname_et)
    EditText surnameEditText;


    RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ColoredHandleCursorTheme);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        RegistrationPresenter presenter = new RegistrationPresenterImpl(new UserInteractorImpl(), this);
        this.presenter = presenter;
    }

    /**
     * Metoda koja pokusava stvoriti novog korisnika.
     * @param view
     */
    @OnClick(R.id.btnRegister)
    public void login_click(View view) {
        TryCreateUser();
    }

    private void TryCreateUser(){

        UserModel um = new UserModel();
        um.setUsername(usernameEditText.getText().toString());
        um.setName(nameEditText.getText().toString());
        um.setPassword(passwordEditText.getText().toString());
        um.setPicture("");
        um.setSurname(surnameEditText.getText().toString());
        um.setUsername(usernameEditText.getText().toString());
        um.setEmail(emailEditText.getText().toString());

        presenter.tryCreateUser(um);

    }

    /**
     * Metoda koja se poziva nakon uspjesne prijave. Korisniku se ispisuje poruka i prebacuje se na aktivnost za prijavu.
     * @param userModel
     */
    public void onSuccess(UserModel userModel){
        Toast.makeText(this,R.string.succesful_registration,Toast.LENGTH_SHORT).show();
        final Context ctx= this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ctx, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    /**
     * U slucaju neuspjesne registracije ispisuje se greska.
     * @param text
     */
    @Override
    public void onFailed(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }
}
