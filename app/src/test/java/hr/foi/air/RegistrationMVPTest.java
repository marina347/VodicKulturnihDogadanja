package hr.foi.air;

import org.junit.Before;
import org.junit.Test;

import hr.foi.air.activity.RegistrationActivity;
import hr.foi.air.interactor.UserInteractor;
import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.model.UserModel;
import hr.foi.air.presenter.impl.RegistrationPresenterImpl;
import hr.foi.air.view.RegistrationView;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class RegistrationMVPTest {
    UserInteractor interactor;
    RegistrationView view;
    RegistrationPresenterImpl registrationPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(RegistrationActivity.class);
        interactor = mock(UserInteractorImpl.class);
        registrationPresenter = new RegistrationPresenterImpl(interactor, view);

        UserModel usermodel=mock(UserModel.class);
        doNothing().when(interactor).createUser(usermodel);
    }

    @Test
    public void testDisplayRegister() {
        UserModel usermodel=new UserModel();
        usermodel.setUserId(1);
        usermodel.setEmail("something@something.something");
        usermodel.setName("name");
        usermodel.setSurname("surname");
        usermodel.setPassword("password");
        usermodel.setUsername("username");

        registrationPresenter.tryCreateUser(usermodel);
        verify(interactor).createUser(usermodel);
        registrationPresenter.onSuccess(usermodel);
        verify(view).onSuccess(usermodel);
    }
}
