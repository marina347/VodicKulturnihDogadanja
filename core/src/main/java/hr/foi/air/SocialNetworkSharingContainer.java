package hr.foi.air;

import android.support.v4.app.Fragment;

/**
 * Sucelje koje definira metode za prikaz i skrivanje fragmenta
 * Created by root on 07.01.18..
 */

public interface SocialNetworkSharingContainer {
    void showFragment(Fragment fragment);
    void hideFragment(Fragment fragment);
}
