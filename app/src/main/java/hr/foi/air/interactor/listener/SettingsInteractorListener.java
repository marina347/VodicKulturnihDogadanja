package hr.foi.air.interactor.listener;

import hr.foi.air.model.SettingsModel;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsInteractorListener {
    void onSuccess(SettingsModel settingsModel);
    void onFailed(String text);
}
