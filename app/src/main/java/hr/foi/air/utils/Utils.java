package hr.foi.air.utils;



import java.util.regex.Pattern;

/**
 * Pomocna klasa
 * Created by marbulic on 11/13/2017.
 */

public class Utils {
    public final static int DISLIKE=0;
    public final static int LIKE=1;
    public final static int NOR_LIKED_NOR_DISLIKED = 2;
    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /**
     * Metoda koja provjerava je li email u pravilnom formatu.
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern =EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
