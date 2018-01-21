package hr.foi.air.interactor;

import hr.foi.air.interactor.listener.SettingsInteractorListener;
import hr.foi.air.model.SettingsModel;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsInteractor {
    void setSettingsListener(SettingsInteractorListener listener);
    void getSettings (int userId);
    void editSettings (SettingsModel settings);
}
