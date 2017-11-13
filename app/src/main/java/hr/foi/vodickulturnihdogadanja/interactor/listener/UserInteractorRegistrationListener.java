package hr.foi.vodickulturnihdogadanja.interactor.listener;

import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface UserInteractorRegistrationListener {
    void onSuccess(UserModel userModel);
    void onFailed(String text);

}
