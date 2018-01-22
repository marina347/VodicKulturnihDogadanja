package hr.foi.air;

import android.app.Activity;

/**
 * Sucelje koje definira metode za uspjesnu i neuspjesnu autentikaciju.
 * Created by root on 07.01.18..
 */

public interface TwitterUserAuthenticationListener {
    void twitterAuthenticateSuccess(Activity activity);
    void twitterAuthenticateFailure();
}
