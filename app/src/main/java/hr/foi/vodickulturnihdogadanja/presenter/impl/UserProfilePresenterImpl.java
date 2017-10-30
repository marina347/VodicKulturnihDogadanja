package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.UserInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.UserInteractorUserProfileListener;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.UserProfilePresenter;
import hr.foi.vodickulturnihdogadanja.view.UserProfileView;

/**
 * Created by Mateja on 25-Oct-17.
 */

public class UserProfilePresenterImpl implements UserProfilePresenter, UserInteractorUserProfileListener{
    UserInteractor userInteractor;
    UserProfileView userProfileView;

    public UserProfilePresenterImpl(UserInteractor userInteractor, UserProfileView userProfileView) {
        this.userInteractor = userInteractor;
        userInteractor.setUserProfileListener(this);
        this.userProfileView = userProfileView;

    }

    @Override
    public void tryViewData (int userId) {
        userInteractor.viewUserData(userId);
    }

    @Override
    public void tryEditData (UserModel userData) {
        userInteractor.editUserData(userData);
    }

    @Override
    public void onSuccess(UserModel userModel) {
        userProfileView.onSuccess(userModel);
    }
}
