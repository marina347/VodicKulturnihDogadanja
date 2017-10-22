package hr.foi.vodickulturnihdogadanja.interactor.impl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.vodickulturnihdogadanja.interactor.CallDefinitions;
import hr.foi.vodickulturnihdogadanja.interactor.RetrofitREST;
import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorLoginListener;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorRegistrationListener;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marbulic on 10/21/2017.
 */

public class UserInteractorImpl implements UserInteractor {
    UserInteractorRegistrationListener listener;
    UserInteractorLoginListener listenerLogin;
    @Override
    public void setRegistrationListener(UserInteractorRegistrationListener listener){
        this.listener = listener;
    }
    @Override
    public void setLoginListener(UserInteractorLoginListener listener) {
        this.listenerLogin = listener;
    }

    @Override
    public void Login(String username, String password) {
        JSONObject jObj= new JSONObject();
        try {
            jObj.put("username", username);
            jObj.put("password", password);
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
            }
        });
        //call.enqueue();

    }
}
