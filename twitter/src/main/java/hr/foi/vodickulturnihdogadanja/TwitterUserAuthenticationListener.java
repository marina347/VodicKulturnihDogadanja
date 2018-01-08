package hr.foi.vodickulturnihdogadanja;

import android.app.Activity;

/**
 * Created by root on 07.01.18..
 */

public interface TwitterUserAuthenticationListener {
    void twitterAuthenticateSuccess(Activity activity);
    void twitterAuthenticateFailure();
}
