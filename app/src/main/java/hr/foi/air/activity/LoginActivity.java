package hr.foi.air.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.model.TokenModel;
import hr.foi.air.presenter.LoginPresenter;
import hr.foi.air.presenter.impl.LoginPresenterImpl;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView{
    LoginPresenter presenter;
    @BindView(R.id.log_username_et)
    EditText usernameEditText;

    @BindView(R.id.log_password_et)
    EditText passwordEditText;
    @OnClick(R.id.btnReg)
    public void RegisterLinkClick(View view){
        NavigateToRegistration();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ColoredHandleCursorTheme);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoginPresenter presenter = new LoginPresenterImpl(new UserInteractorImpl(), this);
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private void NavigateToRegistration(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnLogin)
    public void login_click(View view) {
        TryLogIn();
    }//
    private void TryLogIn(){

       String name=usernameEditText.getText().toString();
       String pass=passwordEditText.getText().toString();
       presenter.tryLogin(name,pass);
   }

   private void showToastOnUI(final String msg){
       final Context ctx = this;
       this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
           }
       });
   }

    @Override
    public void onSuccess(final TokenModel model) {
        //showToastOnUI("Uspješna prijava");
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(final String error) {
        showToastOnUI(error);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base));
    }
}
