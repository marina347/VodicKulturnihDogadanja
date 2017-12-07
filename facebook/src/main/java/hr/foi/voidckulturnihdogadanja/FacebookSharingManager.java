package hr.foi.voidckulturnihdogadanja;

import android.app.Activity;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManager;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManagerListener;

/**
 * Created by marbulic on 12/6/2017.
 */

public class FacebookSharingManager implements SocialNetworkSharingManager {
    @Override
    public void setListener(SocialNetworkSharingManagerListener lis) {

    }

    @Override
    public void Share(Activity activity, int eventId) {
        ShareDialog sd;
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        sd = new ShareDialog(activity);
        if (sd.canShow(content, ShareDialog.Mode.NATIVE)) {
            sd.show(content, ShareDialog.Mode.NATIVE);
        } else {

        }
    }


}
