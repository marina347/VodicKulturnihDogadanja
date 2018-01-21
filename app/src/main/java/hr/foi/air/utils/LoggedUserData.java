package hr.foi.air.utils;

import hr.foi.air.model.TokenModel;

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
    public static void setInstanceToNull(){
        instance=null;
    }

    public TokenModel getTokenModel() {
        return tokenModel;
    }

    public void setTokenModel(TokenModel tokenModel) {
        this.tokenModel = tokenModel;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String surname;
    String image;



}
