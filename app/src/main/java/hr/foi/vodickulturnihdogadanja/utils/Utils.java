package hr.foi.vodickulturnihdogadanja.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by marbulic on 11/13/2017.
 */

public class Utils {
    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
