package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.RegistrationPresenter;
import hr.foi.vodickulturnihdogadanja.view.RegistrationView;

/**
 * Created by marbulic on 10/22/2017.
 */

public class RegistrationPresenterImpl implements RegistrationPresenter, UserInteractorRegistrationListener {
    UserInteractor ui;
    RegistrationView rv;

    public RegistrationPresenterImpl(UserInteractor ui, RegistrationView rv) {
        this.ui = ui;
        ui.setRegistrationListener(this);
        this.rv = rv;
    }

    @Override
    public void tryCreateUser(UserModel userModel) {
        ui.createUser(userModel);
    }

    @Override
    public void onSuccess(UserModel userModel) {
        rv.onSuccess(userModel);
    }
}
