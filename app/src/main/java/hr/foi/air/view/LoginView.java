package hr.foi.air.view;

import hr.foi.air.model.TokenModel;

/**
 * Sucelje koje definira metode za uspjeh i neuspjeh prijave.
 * Created by marbulic on 10/22/2017.
 */

public interface LoginView {
    void onSuccess(TokenModel token);
    void onLoginFailed(String error);
}
