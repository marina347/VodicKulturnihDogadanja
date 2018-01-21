package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.SettingsInteractor;
import hr.foi.air.interactor.listener.SettingsInteractorListener;
import hr.foi.air.model.SettingsModel;
import hr.foi.air.presenter.SettingsPresenter;
import hr.foi.air.view.SettingsView;

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
    public void tryEditSettings(SettingsModel settingsModel) {
        interactor.editSettings(settingsModel);
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
