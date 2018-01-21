package hr.foi.air.presenter.impl;

import java.util.List;

import hr.foi.air.interactor.FavoriteInteractor;
import hr.foi.air.interactor.listener.FavoriteInteractorListener;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.FavoritePresenter;
import hr.foi.air.view.FavoriteView;

/**
 * Created by Mateja on 22-Nov-17.
 */

public class FavoritePresenterImpl implements FavoritePresenter, FavoriteInteractorListener {
    FavoriteInteractor interactor;
    FavoriteView view;

    public FavoritePresenterImpl(FavoriteInteractor interactor, FavoriteView view) {
        this.interactor = interactor;
        interactor.setFavoriteListener(this);
        this.view = view;
    }

    @Override
    public void tryGetFavorites(int userId) {
        interactor.getFavorite(userId);
    }

    @Override
    public void tryDeleteFavorite(int userId, int eventId) {
        interactor.deleteFavorite(userId, eventId);
    }

    @Override
    public void onSuccess(List<EventModel> favoritesList) {
        view.onSuccess(favoritesList);
    }

    @Override
    public void noFavorites() {
        view.noFavorites("Nemate događaja označenih kao favorit!");
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

}
