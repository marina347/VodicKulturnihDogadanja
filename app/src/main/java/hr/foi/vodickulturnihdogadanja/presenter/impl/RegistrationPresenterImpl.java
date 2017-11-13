package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.RegistrationPresenter;
import hr.foi.vodickulturnihdogadanja.utils.Utils;
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
        if(userModel.getName().isEmpty()||userModel.getSurname().isEmpty() ||userModel.getUsername().isEmpty()
                || userModel.getPassword().isEmpty() || userModel.getEmail().isEmpty()){
            rv.onFailed("Unesite sva polja!");
            return;
        }
        else if(userModel.getUsername().length()<6){
            rv.onFailed("Korisničko ime mora imati najmanje 6 znakova!");
        }
        else if(userModel.getPassword().length()<6){
            rv.onFailed("Lozinka mora imati najmanje 6 znakova!");
        }
        else if(!Utils.isValidEmail((userModel.getEmail()))){
            rv.onFailed("Email adresa nije u dobrom formatu!");
        }
        else{
            ui.createUser(userModel);
        }

    }

    @Override
    public void onSuccess(UserModel userModel) {
        rv.onSuccess(userModel);
    }

    @Override
    public void onFailed(String text) {
        rv.onFailed(text);
    }
}
