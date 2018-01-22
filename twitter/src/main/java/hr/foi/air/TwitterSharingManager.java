package hr.foi.air;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Klasa za dijeljenje detalja o dogadaju preko Twittera.
 * Created by Mateja on 16-Dec-17.
 */

public class TwitterSharingManager extends BroadcastReceiver implements SocialNetworkSharingManager, TwitterUserAuthenticationListener{
    static TwitterFragment fragment;
    static SocialNetworkSharingManagerListener listener;
    static SocialNetworkSharingContainer container;
    int eventId;

    /**
     * Metoda koja provjerava je li Twitter aplikacija instalirana na uredaju.
     * @param activity
     * @return
     */
    public boolean isTwitterInstalled(Activity activity) {
        boolean twitterInstalled = false;
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo("com.twitter.android", 0);
            String getPackageName = packageInfo.toString();
            if (getPackageName.contains("com.twitter.android")) {
                Toast.makeText(activity, "Twitter App is installed on device!", Toast.LENGTH_LONG).show();
                twitterInstalled = false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(activity, "Twitter App not found on device!", Toast.LENGTH_LONG).show();
            twitterInstalled = false;
        }
        return twitterInstalled;
    }

    @Override
    public void setListener(SocialNetworkSharingManagerListener lis) {
        this.listener = lis;
    }

    @Override
    public void setContainer(SocialNetworkSharingContainer container) {
        this.container = container;
    }

    /**
     * Metoda koja prikazuje Twitter fragment.
     * @param activity
     * @param eventId
     */
    @Override
    public void share(Activity activity, int eventId) {
        this.eventId = eventId;
        fragment = new TwitterFragment();
        fragment.setTwitterUserAuthenticationListener(this);
        container.showFragment(fragment);
    }

    /**
     * Metoda koja prosljeduje rezultat Twitter Fragmentu.
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        fragment.onActivityResult(requestCode,resultCode,intent);
    }

    /**
     * Metoda koja dijeli sadrzaj ako je uspjela autentikacija.
     * @param activity
     */
    @Override
    public void twitterAuthenticateSuccess(Activity activity) {
        //if(isTwitterInstalled(activity)){
            final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession();
            final Intent intent = new ComposerActivity.Builder(activity)
                    .session(session)
                    .text("http://vodickulturnihdogadanja.1e29g6m.xip.io/webPage/events.php?eventId="+eventId)
                    .hashtags("#VodicKulturnihDogadanja")
                    .createIntent();
            activity.startActivity(intent);
        //}

    }

    /**
     * Metoda koja se poziva ako autentikacija nije uspjela. Gasi se Twitter Fragment"
     */
    @Override
    public void twitterAuthenticateFailure() {
        container.hideFragment(fragment);
        listener.canceled();
    }

    /**
     * Metoda koja se poziva ovisno o uspjesnosti dijeljenja sadrzaja na Twitter.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            container.hideFragment(fragment);
            listener.shared();
        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            container.hideFragment(fragment);
            listener.canceled();
        }
    }
}
