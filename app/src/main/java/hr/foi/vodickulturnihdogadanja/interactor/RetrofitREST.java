package hr.foi.vodickulturnihdogadanja.interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marbulic on 10/21/2017.
 */

public class RetrofitREST {
    private static  Retrofit retrofit;
    //private final static String baseUrl = "http://192.168.43.7/air_vodic/";
    private final static String baseUrl = "http://vodickulturnihdogadanja.1e29g6m.xip.io/";

    public static Retrofit getRetrofit() {
        if(retrofit == null)
            retrofit = startRetrofit();
        return retrofit;
    }

    protected static Retrofit startRetrofit(){
        OkHttpClient client = new OkHttpClient();
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(client)
                .build();
    }

    private static Gson getGson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }


}
