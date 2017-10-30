package hr.foi.vodickulturnihdogadanja.presenter.impl;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.EventInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.view.EventView;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class EventPresenterImpl implements EventPresenter,EventInteractorListener {
    EventInteractor ei;
    EventView ev;

    public EventPresenterImpl(EventInteractor ei, EventView ev) {
        this.ei = ei;
        ei.setEventListener(this);
        this.ev = ev;
    }

    @Override
    public void tryGetEvents() {
        ei.getEvent();
    }

    @Override
    public void ArrivedEvents(List<EventModel> eventList) {
        ev.Arrived(eventList);
    }

    @Override
    public void NoEvents() {
        ev.NoEvents("Nema budućih događanja!");
    }
}
