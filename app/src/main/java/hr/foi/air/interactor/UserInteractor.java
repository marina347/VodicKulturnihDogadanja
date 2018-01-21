package hr.foi.air.interactor;

import hr.foi.air.interactor.listener.UserInteractorLoginListener;
import hr.foi.air.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.air.interactor.listener.UserInteractorUserProfileListener;
import hr.foi.air.model.UserModel;

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
    void editUserData(UserModel userData);
    void updateDeviceId(String deviceId, int userId);
    void getDataForDrawer(int userId);
}
