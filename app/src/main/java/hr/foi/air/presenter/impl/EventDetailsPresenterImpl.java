package hr.foi.air.presenter.impl;

import hr.foi.air.interactor.EventDetailsInteractor;
import hr.foi.air.interactor.FavoriteInteractor;
import hr.foi.air.interactor.listener.AddFavoriteInteractorListener;
import hr.foi.air.interactor.listener.EventDetailsInteractorListener;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.EventDetailsPresenter;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.EventDetailsView;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public class EventDetailsPresenterImpl implements EventDetailsPresenter, EventDetailsInteractorListener, AddFavoriteInteractorListener {
    EventDetailsInteractor edi;
    EventDetailsView edv;
    FavoriteInteractor fi;

    public EventDetailsPresenterImpl (EventDetailsInteractor edi, FavoriteInteractor fi, EventDetailsView edv)
    {
        this.edi=edi;
        edi.setEventDetailsListener(this);
        this.edv=edv;
        this.fi = fi;
    }

    /**
     * Implementacija funkcije sučelja EventDetailsPresenter. Funkcija pokušava dohvatiti događaj pomoću id-a. Šalje id događaja interactoru.
     * @param eventId
     */
    @Override
    public void tryGetEventById(int eventId) {
        edi.getEventById(eventId);
    }

    /**
     * Implementacija funkcije sučelja EventDetailsInteractoListener. Funkcija šalje pristigli događaj EventView-u.
     * @param event
     */
    @Override
    public void ArrivedEventById(EventModel event) {
        edv.ArrivedEvent(event);
    }

    /**
     * Implementacija funkcije sučelja EventDetailsInteractoListener. Funkcija šalje poruku o uspješnom ocjenjivanju događaja EventView.
     */
    @Override
    public void successAddedEvaluation() {
        edv.onSuccessAddedEvaluation();
    }

    /**
     * Implementacija funkcije sučelja EventDetailsInteractoListener. Funkcija šalje poruku o neuspješnom ocjenjivanju događaja EventView.
     */
    @Override
    public void failedAddedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    /**
     * Implementacija funkcije sučelja EventDetailsInteractoListener. Funkcija šalje poruku o uspješnom brisnju ocjene događaja EventView.
     */
    @Override
    public void onSuccessDeletedEvaluation() {
        edv.onSuccessDeletedEvaluation();
    }

    /**
     * Implementacija funkcije sučelja EventDetailsInteractoListener. Funkcija šalje poruku o neuspješnom brisanju ocjene događaja EventView.
     */
    @Override
    public void onFailedDeletedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    /**
     * Implementacija funkcije sučelja AddFavoriteLinteractoListener Funkcija javlja EventView da je događaj uspješno dodan u favorite.
     */
    @Override
    public void onAddSuccess() {
        edv.onSuccessAddedFavorite();
    }

    /**
     * Implementacija funkcije sučelja AddFavoriteLinteractoListener Funkcija javlja EventView da je događaj neuspješno dodan u favorite.
     */
    @Override
    public void onAddFailure() {
        edv.onSuccessAddedFavorite();
    }

    /**
     * Implementacija funkcije sučelja EventDetailsPresenter. Funkcija pokušava dodati događaj u favorite, te ga šalje interactoru.
     * @param eventId
     */
    @Override
    public void tryAddFavorite(int eventId) {
        fi.addFavorite(LoggedUserData.getInstance().getTokenModel().getUserId(),eventId);
    }

    /**
     * Implementacija funkcije sučelja EventDetailsPresenter. Funkcija pokušava ocjeniti događaj, te ocjenu šalje interactoru.
     * @param mark
     * @param userId
     * @param eventId
     */
    @Override
    public void tryAddEvaluation(int mark, int userId, int eventId) {
        edi.addEvaluation(mark,userId,eventId);
    }

    /**
     * Implementacija funkcije sučelja EventDetailsPresenter. Funkcija pokušava  obrisati ocjenu događaj, te zahtjev šalje interactoru.
     * @param userId
     * @param eventId
     */
    @Override
    public void tryDeleteEvaluation(int userId, int eventId) {
        edi.deleteEvaluation(userId, eventId);
    }
}
