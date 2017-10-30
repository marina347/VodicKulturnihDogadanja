package hr.foi.vodickulturnihdogadanja.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by marbulic on 10/22/2017.
 */

public class RESTErrorDecoderUtils {


    public static final int POST_JSON_DATA_FORMAT_ERROR=10001;
    public static final int POST_MISSING_PARAMETERS=10002;
    public static final int POST_INVALID_DATA_VALUES=10003;
    public static final int INVALID_TOKEN=10004;
    public static final int SERVER_INTERNAL_EXCEPTION_ERR  = 10005;
    public static final int GET_MISSING_PARAMETERS=10006;
    public static final int GET_INVALID_DATA_VALUES=10007;
    public static final int GET_ID_NOT_FOUND=10008;
    public static final int POST_VALIDATION_FAILED=10009;
    public static final int REGISTRATION_EXISTING_USERNAME=10010;
    public static final int REGISTRATION_EXISTING_EMAIL=10011;
    public static final int LOGIN_WRONG_CREDENTIALS  = 10012;
    public static final int GET_RESURCE_NOT_FOUND=10013;



    public static RESTError decodeError(ResponseBody body){
        Gson gson = new Gson();
        String json = null;
        try {
            json = body.string();
            return gson.fromJson(json, RESTError.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
