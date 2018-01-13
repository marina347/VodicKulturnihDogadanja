package hr.foi.vodickulturnihdogadanja.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Mateja on 13-Jan-18.
 */

public class SettingsModel {
    @Expose(serialize = false)
    private int settingsId;
    @Expose
    private int pushUpNotification;
    @Expose
    private int sound;
    @Expose
    private int languageId;

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public int getPushUpNotification() {
        return pushUpNotification;
    }

    public void setPushUpNotification(int pushUpNotification) {
        this.pushUpNotification = pushUpNotification;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
}
