package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.SettingsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.SettingsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;
import hr.foi.vodickulturnihdogadanja.presenter.SettingsPresenter;
import hr.foi.vodickulturnihdogadanja.view.SettingsView;

/**
 * Created by Mateja on 13-Jan-18.
 */

public class SettingsPresenterImpl implements SettingsPresenter, SettingsInteractorListener {

    SettingsInteractor interactor;
    SettingsView view;

    public SettingsPresenterImpl(SettingsInteractor interactor, SettingsView view) {
        this.interactor = interactor;
        interactor.setSettingsListener(this);
        this.view = view;
    }

    @Override
    public void tryGetSettings(int userId) {
        interactor.getSettings(userId);
    }

    @Override
    public void onSuccess(SettingsModel settingsModel) {
        view.onSuccess(settingsModel);
    }

    @Override
    public void onFailed(String text) {
        view.onFailed("Greska u dohvacanju favorita!");
    }
}
