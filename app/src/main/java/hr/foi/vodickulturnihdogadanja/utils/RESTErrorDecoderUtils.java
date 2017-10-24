package hr.foi.vodickulturnihdogadanja.utils;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by marbulic on 10/22/2017.
 */

public class RESTErrorDecoderUtils {
    public static final int SERVER_INTERNAL_EXCEPTION_ERRR  = 950;

    public static int decodeError(ResponseBody body){
        //decode
        //switch return

        return SERVER_INTERNAL_EXCEPTION_ERRR;
    }
}
