package hr.foi.air.interactor.listener;

import hr.foi.air.model.UserModel;

/**
 * Created by marbulic on 10/22/2017.
 */

public interface UserInteractorRegistrationListener {
    void onSuccess(UserModel userModel);
    void onFailed(String text);

}
