package hr.foi.air.presenter;

/**
 * Sucelje koje definira metodu kojom ce LoginPresenterImpl pozvati interactora da se pokusa prijaviti u aplikaciju.
 * Created by marbulic on 10/22/2017.
 */

public interface LoginPresenter {
    public void tryLogin(String username, String password);
}
