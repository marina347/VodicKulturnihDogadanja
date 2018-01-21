package hr.foi.air.utils.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.utils.LoggedUserData;

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
