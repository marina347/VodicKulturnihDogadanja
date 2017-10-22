package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by marbulic on 10/21/2017.
 */

public interface CallDefinitions {
    @POST("user.php")
    Call<UserModel> createUser(@Body UserModel data);

    @POST("userLogIn.php")
    Call<TokenModel> Login(@Body RequestBody data);
}
