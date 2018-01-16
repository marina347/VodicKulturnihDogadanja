package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.SettingsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsInteractor {
    void setSettingsListener(SettingsInteractorListener listener);
    void getSettings (int userId);
    void editSettings (SettingsModel settings);
}
