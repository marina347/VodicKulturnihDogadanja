package hr.foi.vodickulturnihdogadanja.utils;

import hr.foi.vodickulturnihdogadanja.model.TokenModel;

/**
 * Created by marbulic on 10/24/2017.
 */

public class LoggedUserData {

    TokenModel tokenModel;

    private static LoggedUserData instance = null;
    private LoggedUserData() {

    }
    public static LoggedUserData getInstance() {
        if(instance == null) {
            instance = new LoggedUserData();
        }
        return instance;
    }

    public TokenModel getTokenModel() {
        return tokenModel;
    }

    public void setTokenModel(TokenModel tokenModel) {
        this.tokenModel = tokenModel;
    }




}
