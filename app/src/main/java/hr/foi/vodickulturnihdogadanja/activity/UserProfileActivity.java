package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.UserProfilePresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.UserProfilePresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.LoginView;
import hr.foi.vodickulturnihdogadanja.view.UserProfileView;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {

    @BindView(R.id.edit_name)
    EditText outputName;

    @BindView(R.id.edit_surname)
    EditText outputSurname;

    @BindView(R.id.edit_username)
    EditText outputUsername;

    @BindView(R.id.edit_email)
    EditText outputEmail;

    @BindView(R.id.edit_password)
    EditText outputPassword;

    UserProfilePresenter userProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        UserProfilePresenter userProfilePresenter = new UserProfilePresenterImpl(new UserInteractorImpl(), this);
        this.userProfilePresenter = userProfilePresenter;

        TryGetData();
        EnableEdit();

    }

    @OnClick(R.id.btn_save_profile_data)
    public void save_click (View view) {
        TryEditData();
        Toast.makeText(this,"Edit data successful",Toast.LENGTH_SHORT).show();
    }

    private void TryGetData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        userProfilePresenter.tryViewData(userId);
    }

    private void TryEditData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();

        UserModel userModel = new UserModel();

        userModel.setUserId(userId);
        userModel.setName(outputName.getText().toString());
        userModel.setSurname(outputSurname.getText().toString());
        userModel.setUsername(outputUsername.getText().toString());
        userModel.setEmail(outputEmail.getText().toString());
        userModel.setPassword(outputPassword.getText().toString());
        userModel.setPicture("");

        userProfilePresenter.tryEditData(userModel);
    }

    public void onSuccess (UserModel userModel) {
        outputName.setText(userModel.getName());
        outputSurname.setText(userModel.getSurname());
        outputUsername.setText(userModel.getUsername());
        outputEmail.setText(userModel.getEmail());
        outputPassword.setText(userModel.getPassword());
    }

    //for edit data, enable on touch
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    };

    //enable edit on touch editText
    private void EnableEdit() {
        outputName.setOnTouchListener(handleTouch);
        outputSurname.setOnTouchListener(handleTouch);
        outputUsername.setOnTouchListener(handleTouch);
        outputEmail.setOnTouchListener(handleTouch);
        outputPassword.setOnTouchListener(handleTouch);
    }
}
