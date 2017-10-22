package hr.foi.vodickulturnihdogadanja.model;

import com.google.gson.annotations.Expose;

/**
 * Created by marbulic on 10/21/2017.
 */

public class UserModel {
    @Expose(serialize =  false)
    private int userId;

    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String email;
    @Expose
    private String picture;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
