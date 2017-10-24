package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorLoginListener;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.LoginPresenter;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.LoginView;

/**
 * Created by marbulic on 10/22/2017.
 */

public class LoginPresenterImpl implements LoginPresenter,UserInteractorLoginListener {
    UserInteractor ui;
    LoginView lv;

    public LoginPresenterImpl(UserInteractor ui, LoginView lv) {
        this.ui = ui;
        ui.setLoginListener(this);
        this.lv = lv;
    }
    @Override
    public void tryLogin(String username, String password) {
        if(username.isEmpty()||password.isEmpty()){
            lv.onLoginFailed("Unesite sva polja!");
            return;
        }
        else{
            ui.Login(username,password);
        }
    }

    @Override
    public void onLoginFailed() {//TODO: add error number handling to show different messages -.-
        lv.onLoginFailed("Neuspješna prijava");
    }

    @Override
    public void onLoginSuccedded(TokenModel token) {
        LoggedUserData.getInstance().setTokenModel(token);
        //LoggedUserData.getInstance().getTokenModel().getUserId();
        lv.onSuccess(token);
    }
}
