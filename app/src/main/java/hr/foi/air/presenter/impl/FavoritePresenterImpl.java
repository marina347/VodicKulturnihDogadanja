package hr.foi.air.presenter.impl;

import java.util.List;

import hr.foi.air.interactor.FavoriteInteractor;
import hr.foi.air.interactor.listener.FavoriteInteractorListener;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.FavoritePresenter;
import hr.foi.air.view.FavoriteView;

/**
 * Klasa koja poziva interactora kako bi mu poslao podatke za favorite i view kako bi mu javio rezultat
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

    /**
     * Implementacija metode sucelja FavoritePresenter. Podaci se prosljeduju interactoru
     * @param userId
     */
    @Override
    public void tryGetFavorites(int userId) {
        interactor.getFavorite(userId);
    }

    /**
     * Implementacija metode sucelja FavoritePresenter. Podaci se prosljeduju interactoru
     * @param userId
     * @param eventId
     */
    @Override
    public void tryDeleteFavorite(int userId, int eventId) {
        interactor.deleteFavorite(userId, eventId);
    }

    /**
     * Implementacija FavoriteInteractorListenera. View se obavjestava u uspjesnosti dohvacanja favorita.
     * @param favoritesList
     */
    @Override
    public void onSuccess(List<EventModel> favoritesList) {
        view.onSuccess(favoritesList);
    }

    /**
     * Implementacija FavoriteInteractorListenera. View se obavjestava u neuspjesnosti dohvacanja favorita.
     */
    @Override
    public void noFavorites() {
        view.noFavorites("Nemate događaja označenih kao favorit!");
    }

    /**
     * Implementacija FavoriteInteractorListenera. View se obavjestava u uspjesnosti brisanja favorita.
     */
    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

}
