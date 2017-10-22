package hr.foi.vodickulturnihdogadanja.model;

import com.google.gson.annotations.Expose;

/**
 * Created by marbulic on 10/22/2017.
 */

public class TokenModel {
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Expose
    private String tokenId;
    @Expose
    private int userId;
    @Expose
    private int time;
}
