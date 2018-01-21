package hr.foi.air.presenter;

import hr.foi.air.model.SettingsModel;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsPresenter {
    void tryGetSettings(int userId);
    void tryEditSettings(SettingsModel settingsModel);
}
