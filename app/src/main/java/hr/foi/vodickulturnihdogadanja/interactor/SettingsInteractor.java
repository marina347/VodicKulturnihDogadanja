package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.SettingsInteractorListener;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsInteractor {
    void setSettingsListener(SettingsInteractorListener listener);
    void getSettings (int userId);
}
