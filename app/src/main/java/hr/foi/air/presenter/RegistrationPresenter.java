package hr.foi.air.presenter;

import hr.foi.air.model.UserModel;

/**
 * Sucelje koje definira metodu kojom ce RegistrationPresenterImpl pozvati interactora da pokusa kreirati novg korisnika.
 * Created by marbulic on 10/22/2017.
 */

public interface RegistrationPresenter {
    public void tryCreateUser(UserModel userModel);
}
