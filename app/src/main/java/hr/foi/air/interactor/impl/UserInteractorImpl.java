package hr.foi.air.interactor.impl;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.interactor.CallDefinitions;
import hr.foi.air.interactor.RetrofitREST;
import hr.foi.air.interactor.UserInteractor;
import hr.foi.air.interactor.listener.LogoutListener;
import hr.foi.air.interactor.listener.UserInteractorLoginListener;
import hr.foi.air.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.air.interactor.listener.UserInteractorUserProfileListener;
import hr.foi.air.model.TokenModel;
import hr.foi.air.model.UserModel;
import hr.foi.air.utils.LoggedUserData;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Klasa za dohvaćanje podataka sa servera i slanje podatataka serveru za profil korisnika
 * Created by marbulic on 10/21/2017.
 */

public class UserInteractorImpl implements UserInteractor,LogoutListener {
    UserInteractorRegistrationListener listener;
    UserInteractorLoginListener listenerLogin;
    UserInteractorUserProfileListener listenerProfile;

    /**
     * Implementacija metode sucelja UserInteractor. Metoda se koristi za postavljanje
     * UserInteractorRegistrationListenera
     * @param listener
     */
    @Override
    public void setRegistrationListener(UserInteractorRegistrationListener listener){
        this.listener = listener;
    }

    /**
     * Implementacija metode sucelja UserInteractor. Metoda se koristi za postavljanje
     * UserInteractorLoginListenera
     * @param listener
     */
    @Override
    public void setLoginListener(UserInteractorLoginListener listener) {
        this.listenerLogin = listener;
    }

    /**
     * Implementacija metode sucelja UserInteractor. Metoda se koristi za postavljanje
     * UserInteractorUserProfileListenera
     * @param listener
     */
    @Override
    public void setUserProfileListener(UserInteractorUserProfileListener listener) {
        this.listenerProfile = listener;
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi za prijavu korisnika u aplikaciju
     * @param username
     * @param password
     */
    @Override
    public void Login(String username, String password) {
        JSONObject jObj= new JSONObject();
        try {
            jObj.put("username", username);
            jObj.put("password", password);
            jObj.put("deviceId", FirebaseInstanceId.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
            listenerLogin.onLoginFailed();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"),data);
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<TokenModel> call = calls.Login(body);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if(response.isSuccessful()){
                    TokenModel token=response.body();

                    listenerLogin.onLoginSuccedded(token);
                }
                else{
                    //int errorNumber = RESTErrorDecoderUtils.decodeError(response.raw().body());
                    //if(errorNumber == RESTErrorDecoderUtils.SERVER_INTERNAL_EXCEPTION_ERRR)
                    //    listenerLogin."Wrong credentials"
                    Log.d("Api","fail login");
                    listenerLogin.onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                listenerLogin.onLoginFailed();
                Log.d("Api", t.getMessage());
            }
        });
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi registraciju korisnika,
     * salje serveru podatke o korisniku
     * @param userData
     */
    @Override
    public void createUser(UserModel userData) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<UserModel> call = calls.createUser(userData);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel user=response.body();
                    listener.onSuccess(user);
                }
                else{
                    Log.d("Api", "fail createUser");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
                listener.onFailed("Unsuccessful registration. Try again!");
            }
        });
        //call.enqueue();

    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi za dohvacanje podataka o
     * korisniku sa servera
     * @param userId
     */
    @Override
    public void viewUserData(int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<UserModel> call = calls.viewUserData(userId);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel user=response.body();
                    listenerProfile.onSuccess(user);
                }
                else{
                    Log.d("Api", "fail viewUserData");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi za slanje promjenjenih
     * podataka na server
     * @param userData
     */
    @Override
    public void editUserData(UserModel userData) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<UserModel> call = calls.editUserData(userData);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel user=response.body();
                    listenerProfile.onSuccess(user);
                }
                else{
                    Log.d("Api", "fail editUserData");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi osvjezavanje tokena
     * @param deviceId
     * @param userId
     */
    @Override
    public void updateDeviceId(String deviceId, int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        JSONObject jObj= new JSONObject();
        try {
            jObj.put("deviceId", deviceId);
            jObj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"),data);
        Call<String> call = calls.updateDeviceID(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                }
                else{
                    Log.d("Api", "fail updateDeviceId");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi za odjavu korisnika iz aplikacije.
     * @param ac
     * @param token
     */
    @Override
    public void logOut(final Activity ac, String token) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        JSONObject jObj= new JSONObject();
        try {
            jObj.put("tokenId", token);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String data = jObj.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"),data);
        Call<String> call = calls.logout(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                }
                else{
                    Log.d("Api", "fail logOut");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }

    /**
     * Implementacija metoda sucelja UserInteractor. Metoda sluzi za dohvacanje podataka korisnika za prikaz
     * na navigation draweru
     * @param userId
     */
    @Override
    public void getDataForDrawer(int userId) {
        CallDefinitions calls = RetrofitREST.getRetrofit().create(CallDefinitions.class);
        Call<UserModel> call = calls.getDataForDrawer(userId);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel user=response.body();
                    LoggedUserData.getInstance().setName(user.getName());
                    LoggedUserData.getInstance().setEmail(user.getEmail());
                    LoggedUserData.getInstance().setSurname(user.getSurname());
                    LoggedUserData.getInstance().setImage(user.getPicture());
                    listenerLogin.onUserDataArrived();
                }
                else{
                    Log.d("Api", "fail viewUserData");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Api", t.getMessage());
            }
        });
    }
}
