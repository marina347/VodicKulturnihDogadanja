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
    @Override
    public void tryGetEventById(int eventId) {
        edi.getEventById(eventId);
    }

    @Override
    public void ArrivedEventById(EventModel event) {
        edv.ArrivedEvent(event);
    }

    @Override
    public void successAddedEvaluation() {
        edv.onSuccessAddedEvaluation();
    }

    @Override
    public void failedAddedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    @Override
    public void onSuccessDeletedEvaluation() {
        edv.onSuccessDeletedEvaluation();
    }

    @Override
    public void onFailedDeletedEvaluation() {
        edv.onFailedAddedEvaluation();
    }

    @Override
    public void onAddSuccess() {
        edv.onSuccessAddedFavorite();
    }

    @Override
    public void onAddFailure() {
        edv.onSuccessAddedFavorite();
    }

    @Override
    public void tryAddFavorite(int eventId) {
        fi.addFavorite(LoggedUserData.getInstance().getTokenModel().getUserId(),eventId);
    }

    @Override
    public void tryAddEvaluation(int mark, int userId, int eventId) {
        edi.addEvaluation(mark,userId,eventId);
    }

    @Override
    public void tryDeleteEvaluation(int userId, int eventId) {
        edi.deleteEvaluation(userId, eventId);
    }
}
