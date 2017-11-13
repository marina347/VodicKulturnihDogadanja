package hr.foi.vodickulturnihdogadanja.view;

import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface RegistrationView {
    void onSuccess(UserModel userModel);
    void onFailed(String text);
}
