package hr.foi.air.utils;

/**
 * Created by marbulic on 10/30/2017.
 */

public class RESTError {
    private int errorNumber;
    private String errorMessage;

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
