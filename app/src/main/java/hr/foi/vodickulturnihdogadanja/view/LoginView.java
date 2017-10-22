package hr.foi.vodickulturnihdogadanja.view;

import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface LoginView {
    void onSuccess(TokenModel token);
    void onLoginFailed(String error);
}
