package hr.foi.vodickulturnihdogadanja.utils.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by marbulic on 11/22/2017.
 */

public class TokenManager extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        saveToken(token);
    }
    private void saveToken(String token){
        new UserInteractorImpl().updateDeviceId( token, LoggedUserData.getInstance().getTokenModel().getUserId());
    }

}
