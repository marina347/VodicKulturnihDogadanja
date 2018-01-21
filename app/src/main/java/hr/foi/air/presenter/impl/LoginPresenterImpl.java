package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.UserInteractor;
import hr.foi.air.interactor.listener.UserInteractorLoginListener;
import hr.foi.air.model.TokenModel;
import hr.foi.air.presenter.LoginPresenter;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.LoginView;

/**
 * Created by marbulic on 10/22/2017.
 */

public class LoginPresenterImpl implements LoginPresenter,UserInteractorLoginListener {
    UserInteractor ui;
    LoginView lv;
    TokenModel tokenModel;
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
        tokenModel = token;
        ui.getDataForDrawer(token.getUserId());
        //LoggedUserData.getInstance().getTokenModel().getUserId();
    }

    @Override
    public void onUserDataArrived() {
        lv.onSuccess(tokenModel);
    }
}
