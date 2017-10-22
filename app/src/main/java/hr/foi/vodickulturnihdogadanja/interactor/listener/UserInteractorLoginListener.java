package hr.foi.vodickulturnihdogadanja.interactor.listener;

import hr.foi.vodickulturnihdogadanja.model.TokenModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface UserInteractorLoginListener {
    void onLoginSuccedded(TokenModel token);
    void onLoginFailed();//add error number later
}
