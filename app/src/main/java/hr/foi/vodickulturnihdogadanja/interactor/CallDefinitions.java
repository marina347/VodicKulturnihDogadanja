package hr.foi.vodickulturnihdogadanja.interactor;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by marbulic on 10/21/2017.
 */

public interface CallDefinitions {
    @POST("user.php")
    Call<UserModel> createUser(@Body UserModel data);

    @POST("userLogIn.php")
    Call<TokenModel> Login(@Body RequestBody data);

    @GET("user.php")
    Call<UserModel> viewUserData(@Query("userId") int userId);

    @POST("userEdit.php")
    Call<UserModel> editUserData(@Body UserModel userData);

    @GET("eventList.php")
    Call<List<EventModel>> getEvents(@Query("begin") Long date);

    @GET("eventListAll.php")
    Call<List<EventModel>> getAllEvents();

    @GET("favoriteList.php")
    Call<List<EventModel>> getFavorites(@Query("userId") int userId);

    @GET("event.php")
    Call<EventModel> getEventById(@Query("eventId") int eventId);

    @POST("favoriteDelete.php")
    Call<String> deleteFavorite(@Body RequestBody data);

    @POST("favorite.php")
    Call<String> addFavorite(@Body RequestBody data);

    @POST("userLogOut.php")
    Call<String> logout(@Body RequestBody data);

    @POST("comment.php")
    Call<CommentModel> createNewComment(@Body CommentModel data);

    @GET("commentList.php")
    Call<List<CommentModel>> getComment(@Query("eventId") int eventId);

    @POST("evaluation.php")
    Call<String> addEvaluation(@Body RequestBody data);

    @POST("refreshToken.php")
    Call<String> updateDeviceID (@Body RequestBody data);

    @GET("settings.php")
    Call<SettingsModel> getSettings (@Query("userId") int userId);

}
