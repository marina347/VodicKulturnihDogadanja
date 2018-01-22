package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.SettingsInteractor;
import hr.foi.air.interactor.listener.SettingsInteractorListener;
import hr.foi.air.model.SettingsModel;
import hr.foi.air.presenter.SettingsPresenter;
import hr.foi.air.view.SettingsView;

/**
 * Klasa koja poziva interactora kako bi mu poslao podatke za postavke i view kako bi mu javio rezultat.
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

    /**
     * Implementacija sucelja SettingsPresenter. Uneseni podaci se prosljeduju interactoru.
     * @param userId
     */
    @Override
    public void tryGetSettings(int userId) {
        interactor.getSettings(userId);
    }

    /**
     * Implementacija sucelja SettingsPresenter. Uneseni podaci se prosljeduju interactoru.
     * @param settingsModel
     */
    @Override
    public void tryEditSettings(SettingsModel settingsModel) {
        interactor.editSettings(settingsModel);
    }

    /**
     * Implementacija SettingsInteractorListener. View se obavjestava u uspjesnosti dohvacanja favorita.
     * @param settingsModel
     */
    @Override
    public void onSuccess(SettingsModel settingsModel) {
        view.onSuccess(settingsModel);
    }

    /**
     * Implementacija SettingsInteractorListener. View se obavjestava u neuspjesnosti dohvacanja favorita.
     * @param text
     */
    @Override
    public void onFailed(String text) {
        view.onFailed("Greska u dohvacanju favorita!");
    }
}
