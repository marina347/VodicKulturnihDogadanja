package hr.foi.air.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Klasa koja sluzi za kodiranje i dekodiranje slike.
 * Created by marbulic on 12/6/2017.
 */

public class Base64Coding {
    /**
     * Metoda za enkodiranje slike u string.
     * @param image
     * @param compressFormat
     * @param quality
     * @return
     */
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        byte[] imageBytes = byteArrayOS.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Metoda za dekodiranje slike iz stringa.
     * @param input
     * @return
     */
    public static Bitmap decodeBase64(String input)
    {
        //byte[] decodedBytes = Base64.decode(input, 0);
        byte[] decodedBytes = Base64.decode(input, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
