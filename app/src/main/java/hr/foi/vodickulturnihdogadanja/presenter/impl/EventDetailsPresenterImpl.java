package hr.foi.vodickulturnihdogadanja.presenter.impl;

import hr.foi.vodickulturnihdogadanja.interactor.EventDetailsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.EventDetailsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public class EventDetailsPresenterImpl implements EventDetailsPresenter, EventDetailsInteractorListener {
    EventDetailsInteractor edi;
    EventDetailsView edv;

    public EventDetailsPresenterImpl (EventDetailsInteractor edi, EventDetailsView edv)
    {
        this.edi=edi;
        edi.setEventDetailsListener(this);
        this.edv=edv;
    }
    @Override
    public void tryGetEventById(int eventId) {
        edi.getEventById(eventId);
    }

    @Override
    public void tryAddNewComment(CommentModel comment) {
        edi.createNewComment(comment);
    }

    @Override
    public void ArrivedEventById(EventModel event) {
        edv.ArrivedEvent(event);
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        edv.onSuccessCreateNewComment(comment);
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        edv.onFailedCreateNewComment(s);
    }
}
