package hr.foi.vodickulturnihdogadanja.presenter.impl;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.FavoriteInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.FavoriteInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.FavoritePresenter;
import hr.foi.vodickulturnihdogadanja.view.FavoriteView;
import okhttp3.ResponseBody;

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
