package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.UserProfilePresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.UserProfilePresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.LoginView;
import hr.foi.vodickulturnihdogadanja.view.UserProfileView;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {

    @BindView(R.id.user_name)
    TextView outputName;

    @BindView(R.id.user_surname)
    TextView outputSurname;

    @BindView(R.id.user_username)
    TextView outputUsername;

    @BindView(R.id.user_email)
    TextView outputEmail;

    UserProfilePresenter userProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        UserProfilePresenter userProfilePresenter = new UserProfilePresenterImpl(new UserInteractorImpl(), this);
        this.userProfilePresenter = userProfilePresenter;

        TryGetData();
    }

    private void TryGetData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        userProfilePresenter.tryViewData(userId);
    }

    public void onSuccess (UserModel userModel) {
        outputName.setText(userModel.getName());
        outputSurname.setText(userModel.getSurname());
        outputUsername.setText(userModel.getUsername());
        outputEmail.setText(userModel.getEmail());
    }
}
