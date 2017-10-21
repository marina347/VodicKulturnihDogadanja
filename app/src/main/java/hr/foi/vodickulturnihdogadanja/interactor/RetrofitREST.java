package hr.foi.vodickulturnihdogadanja.interactor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marbulic on 10/21/2017.
 */

public class RetrofitREST {
    private static  Retrofit retrofit;
    private final static String baseUrl = "localhost/air_vodic";

    public static Retrofit getRetrofit() {
        if(retrofit == null)
            retrofit = startRetrofit();
        return retrofit;
    }

    protected static Retrofit startRetrofit(){
        OkHttpClient client = new OkHttpClient();
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }


}
