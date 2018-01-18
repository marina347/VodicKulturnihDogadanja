package hr.foi.vodickulturnihdogadanja;

import org.junit.Before;
import org.junit.Test;

import hr.foi.vodickulturnihdogadanja.fragments.UserProfileFragment;
import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.UserProfilePresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.UserProfileView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class UserProfileMVPTest {
    UserInteractor interactor;
    UserProfileView view;
    UserProfilePresenterImpl userPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(UserProfileFragment.class);
        interactor = mock(UserInteractor.class);
        userPresenter = new UserProfilePresenterImpl(interactor, view);
    }

    @Test
    public void testDisplayGetUserProfile() {
        int userId=1;
        UserModel userModel=mock(UserModel.class);

        userPresenter.tryViewData(userId);
        verify(interactor).viewUserData(userId);
        userPresenter.onSuccess(userModel);
        verify(view).onSuccess(userModel);
    }
    @Test
    public void testDisplayEditUserProfile() {
        UserModel userModel=mock(UserModel.class);
        userPresenter.tryEditData(userModel);
        verify(interactor).editUserData(userModel);
        userPresenter.onSuccess(userModel);
        verify(view).onSuccess(userModel);
    }
}
