package hr.foi.air.interactor.listener;

import hr.foi.air.model.TokenModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface UserInteractorLoginListener {
    void onLoginSuccedded(TokenModel token);
    void onLoginFailed();//add error number later
    void onUserDataArrived();
}
