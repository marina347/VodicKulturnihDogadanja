package hr.foi.vodickulturnihdogadanja;

import android.app.Activity;

/**
 * Created by marbulic on 12/6/2017.
 */

public interface SocialNetworkSharingManager {
    void setListener(SocialNetworkSharingManagerListener lis);
    void Share(Activity activity, int eventId);
}
