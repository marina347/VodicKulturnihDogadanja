package hr.foi.air.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Mateja on 13-Jan-18.
 */

public class SettingsModel {
    @Expose
    private int userId;
    @Expose
    private int pushUpNotification;
    @Expose
    private int languageId;

    public int getPushUpNotification() {
        return pushUpNotification;
    }

    public void setPushUpNotification(int pushUpNotification) {
        this.pushUpNotification = pushUpNotification;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
