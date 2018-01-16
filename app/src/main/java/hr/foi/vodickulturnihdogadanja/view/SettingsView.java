package hr.foi.vodickulturnihdogadanja.view;

import hr.foi.vodickulturnihdogadanja.model.SettingsModel;

/**
 * Created by Mateja on 13-Jan-18.
 */

public interface SettingsView {
    void onSuccess(SettingsModel settings);
    void onFailed(String text);
}
