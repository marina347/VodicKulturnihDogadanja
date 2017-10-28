package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorLoginListener;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorUserProfileListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by marbulic on 10/21/2017.
 */

public interface UserInteractor {
    void createUser(UserModel userData);
    void setRegistrationListener(UserInteractorRegistrationListener listener);
    void Login(String username, String password);
    void setLoginListener(UserInteractorLoginListener listener);
    void viewUserData(int userId);
    void setUserProfileListener(UserInteractorUserProfileListener listener);
    void editUserData(UserModel userDataEdit);
}
