package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.UserInteractor;
import hr.foi.air.interactor.listener.UserInteractorUserProfileListener;
import hr.foi.air.model.UserModel;
import hr.foi.air.presenter.UserProfilePresenter;
import hr.foi.air.view.UserProfileView;

/**
 * Klasa koja poziva interactora kako bi mu poslao podatke za korisnika i view kako bi mu javio rezultat.
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

    /**
     * Implementacija sucelja UserProfilePresenter. Podaci se prosljeduju interactoru.
     * @param userId
     */
    @Override
    public void tryViewData (int userId) {
        userInteractor.viewUserData(userId);
    }

    /**
     * Implementacija sucelja UserProfilePresenter. Podaci se prosljeduju interactoru.
     * @param userData
     */
    @Override
    public void tryEditData (UserModel userData) {
        userInteractor.editUserData(userData);
    }

    /**
     * Implementacija UserInteractorUserProfileListener. View se obavjestava u uspjesnosti prikaza podataka.
     * @param userModel
     */
    @Override
    public void onSuccess(UserModel userModel) {
        userProfileView.onSuccess(userModel);
    }
}
