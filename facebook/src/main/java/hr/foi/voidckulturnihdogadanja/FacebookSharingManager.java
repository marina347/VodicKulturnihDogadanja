package hr.foi.voidckulturnihdogadanja;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManager;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManagerListener;

/**
 * Created by marbulic on 12/6/2017.
 */

public class FacebookSharingManager implements SocialNetworkSharingManager {
    SocialNetworkSharingManagerListener listener;
    @Override
    public void setListener(SocialNetworkSharingManagerListener lis) {
        this.listener = lis;
    }
    CallbackManager callbackManager;
    @Override
    public void share(Activity activity, int eventId) {
        ShareDialog sd;
        callbackManager = CallbackManager.Factory.create();
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        sd = new ShareDialog(activity);
        sd.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {


            @Override
            public void onSuccess(Sharer.Result result) {
                listener.shared();
            }

            @Override
            public void onCancel() {
                listener.canceled();

            }

            @Override
            public void onError(FacebookException error) {
                listener.canceled();
            }
        });
        if (sd.canShow(content, ShareDialog.Mode.FEED)) {
            sd.show(content, ShareDialog.Mode.FEED);
        } else {
            listener.canceled();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        callbackManager.onActivityResult(requestCode,resultCode,intent);
    }


}
