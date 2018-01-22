package hr.foi.air.view;

import hr.foi.air.model.UserModel;

/**
 * Sucelje koje definira metode za uspjeh i neuspjeh registracije.
 * Created by marbulic on 10/22/2017.
 */

public interface RegistrationView {
    void onSuccess(UserModel userModel);
    void onFailed(String text);
}
