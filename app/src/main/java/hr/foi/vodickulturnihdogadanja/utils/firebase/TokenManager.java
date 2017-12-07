package hr.foi.vodickulturnihdogadanja.utils.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
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
        //new UserInteractorImpl().updateDeviceId(token,);
    }

}
