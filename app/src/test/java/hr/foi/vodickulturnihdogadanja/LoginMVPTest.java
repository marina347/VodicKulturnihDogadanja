package hr.foi.vodickulturnihdogadanja;

import org.junit.Before;
import org.junit.Test;

import hr.foi.vodickulturnihdogadanja.activity.LoginActivity;
import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.LoginPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.LoginView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class LoginMVPTest {
    UserInteractor interactor;
    LoginView view;
    LoginPresenterImpl loginPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(LoginActivity.class);
        interactor = mock(UserInteractor.class);
        loginPresenter = new LoginPresenterImpl(interactor, view);
    }

    @Test
    public void testDisplayLogin() {
        String username="username";
        String password="password";

        TokenModel token=mock(TokenModel.class);
        token.setUserId(1);
        loginPresenter.tryLogin(username,password);
        verify(interactor).Login(username,password);
        loginPresenter.onLoginSuccedded(token);
        loginPresenter.onUserDataArrived();
        verify(view).onSuccess(token);



    }
}
