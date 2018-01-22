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
     * Implementacija funkcije sucelja EventDetailsPresenter. Funkcija pokusava dohvatiti dogadaj pomocu id-a. Šalje id dogadaja interactoru.
     * @param eventId
     */
    @Override
    public void tryGetEventById(int eventId) {
        edi.getEventById(eventId);
    }

    /**
     * Implementacija funkcije sucelja EventDetailsInteractoListener. Funkcija salje pristigli dogadaj EventView-u.
     * @param event
     */
    @Override
    public void ArrivedEventById(EventModel event) {
        edv.ArrivedEvent(event);
    }

    /**
     * Implementacija funkcije sucelja EventDetailsInteractoListener. Funkcija salje poruku o uspjesnom ocjenjivanju dogadaja EventView.
     */
    @Override
    public void successAddedEvaluation() {
        edv.onSuccessAddedEvaluation();
    }

    /**
     * Implementacija funkcije sucelja EventDetailsInteractoListener. Funkcija salje poruku o neuspjesnom ocjenjivanju dogadaja EventView.
     */
    @Override
    public void failedAddedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    /**
     * Implementacija funkcije sucelja EventDetailsInteractoListener. Funkcija salje poruku o uspjesnom brisnju ocjene dogadaja EventView.
     */
    @Override
    public void onSuccessDeletedEvaluation() {
        edv.onSuccessDeletedEvaluation();
    }

    /**
     * Implementacija funkcije sucelja EventDetailsInteractoListener. Funkcija salje poruku o neuspjesnom brisanju ocjene dogadaja EventView.
     */
    @Override
    public void onFailedDeletedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    /**
     * Implementacija funkcije sucelja AddFavoriteLinteractoListener Funkcija javlja EventView da je dogadaj uspjesno dodan u favorite.
     */
    @Override
    public void onAddSuccess() {
        edv.onSuccessAddedFavorite();
    }

    /**
     * Implementacija funkcije sucelja AddFavoriteLinteractoListener Funkcija javlja EventView da je dogadaj neuspješno dodan u favorite.
     */
    @Override
    public void onAddFailure() {
        edv.onSuccessAddedFavorite();
    }

    /**
     * Implementacija funkcije sucelja EventDetailsPresenter. Funkcija pokusava dodati dogadaj u favorite, te ga salje interactoru.
     * @param eventId
     */
    @Override
    public void tryAddFavorite(int eventId) {
        fi.addFavorite(LoggedUserData.getInstance().getTokenModel().getUserId(),eventId);
    }

    /**
     * Implementacija funkcije sucelja EventDetailsPresenter. Funkcija pokusava ocjeniti dogadaj, te ocjenu salje interactoru.
     * @param mark
     * @param userId
     * @param eventId
     */
    @Override
    public void tryAddEvaluation(int mark, int userId, int eventId) {
        edi.addEvaluation(mark,userId,eventId);
    }

    /**
     * Implementacija funkcije sucelja EventDetailsPresenter. Funkcija pokusava  obrisati ocjenu dogadaj, te zahtjev salje interactoru.
     * @param userId
     * @param eventId
     */
    @Override
    public void tryDeleteEvaluation(int userId, int eventId) {
        edi.deleteEvaluation(userId, eventId);
    }
}
