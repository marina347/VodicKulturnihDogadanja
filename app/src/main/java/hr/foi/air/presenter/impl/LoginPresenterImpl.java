package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.UserInteractor;
import hr.foi.air.interactor.listener.UserInteractorLoginListener;
import hr.foi.air.model.TokenModel;
import hr.foi.air.presenter.LoginPresenter;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.LoginView;

/**
 * Klasa koja poziva interactora kako bi mu poslao podatke za prijavu i view kako bi mu javio rezultat
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

    /**
     * Implementacija metode sucelja LoginPresenter. Provjeravaju se uneseni podaci i prosljeduju interactoru.
     * @param username
     * @param password
     */
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

    /**
     * Implementacija UserInteractorListenera. View se obavjestava u neuspjesnosti prijave.
     */
    @Override
    public void onLoginFailed() {//TODO: add error number handling to show different messages -.-
        lv.onLoginFailed("Neuspješna prijava");
    }

    /**
     * Implementacija UserInteractorListenera. View se obavjestava u uspjesnosti prijave.
     * @param token
     */
    @Override
    public void onLoginSuccedded(TokenModel token) {
        LoggedUserData.getInstance().setTokenModel(token);
        tokenModel = token;
        ui.getDataForDrawer(token.getUserId());
        //LoggedUserData.getInstance().getTokenModel().getUserId();
    }

    /**
     * Metoda koja prosljeduje podatke za drawer viewu.
     */
    @Override
    public void onUserDataArrived() {
        lv.onSuccess(tokenModel);
    }
}
