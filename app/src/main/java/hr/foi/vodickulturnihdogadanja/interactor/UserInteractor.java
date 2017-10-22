package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;

/**
 * Created by marbulic on 10/21/2017.
 */

public interface UserInteractor {
    void createUser(UserModel userData);
    void setListener(UserInteractorListener listener);

}
