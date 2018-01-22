package hr.foi.air;

import android.app.Activity;
import android.content.Intent;

/**
 * Glavno sucelje preko kojeg moduli facebook i twitter ostvaruju dijeljenje
 * Created by marbulic on 12/6/2017.
 */

public interface SocialNetworkSharingManager {
    void setListener(SocialNetworkSharingManagerListener lis);
    void setContainer(SocialNetworkSharingContainer container);
    void share(Activity activity, int eventId);
    void onActivityResult(int requestCode, int resultCode, Intent intent);
}
