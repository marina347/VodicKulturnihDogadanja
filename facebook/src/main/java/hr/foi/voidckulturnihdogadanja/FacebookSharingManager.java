package hr.foi.voidckulturnihdogadanja;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingContainer;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManager;
import hr.foi.vodickulturnihdogadanja.SocialNetworkSharingManagerListener;

/**
 * Created by marbulic on 12/6/2017.
 */

public class FacebookSharingManager implements SocialNetworkSharingManager {
    SocialNetworkSharingManagerListener listener;
    SocialNetworkSharingContainer container;
    CallbackManager callbackManager;
    @Override
    public void setListener(SocialNetworkSharingManagerListener lis) {
        this.listener = lis;
    }
    @Override
    public void setContainer(SocialNetworkSharingContainer container) {
        this.container = container;
    }
    @Override
    public void share(Activity activity, int eventId) {
            FacebookSdk.sdkInitialize(activity);
            ShareDialog sd;
            callbackManager = CallbackManager.Factory.create();
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://vodickulturnihdogadanja.1e29g6m.xip.io/webPage/events.php?eventId="+eventId))
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
        if (isAppInstalled(activity, eventId) == true) {
            if (sd.canShow(content, ShareDialog.Mode.NATIVE)) {
                sd.show(content, ShareDialog.Mode.NATIVE);
            } else {
                listener.canceled();
            }
        }
        else{
            if (sd.canShow(content, ShareDialog.Mode.FEED)) {
                sd.show(content, ShareDialog.Mode.FEED);
            } else {
                listener.canceled();
            }
        }
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        callbackManager.onActivityResult(requestCode,resultCode,intent);
    }
    public boolean isAppInstalled(Activity activity, int eventId) {
        boolean faceBook=false;
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String getPackageName = packageInfo.toString();
            if (getPackageName.contains("com.facebook.katana")) {
                Toast.makeText(activity, "Facebook App is installed on device!", Toast.LENGTH_LONG).show();
                faceBook = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(activity, "Facebook App not found on device!", Toast.LENGTH_LONG).show();
            faceBook = false;
        }
        return faceBook;
    }

}
