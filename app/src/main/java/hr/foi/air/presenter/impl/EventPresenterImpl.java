package hr.foi.air.presenter.impl;

import java.util.List;

import hr.foi.air.interactor.EventInteractor;
import hr.foi.air.interactor.listener.EventInteractorListener;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.EventPresenter;
import hr.foi.air.view.AllEventView;
import hr.foi.air.view.EventView;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class EventPresenterImpl implements EventPresenter,EventInteractorListener {
    EventInteractor ei;
    EventView ev;
    AllEventView aev;

    public EventPresenterImpl(EventInteractor ei, EventView ev) {
        this.ei = ei;
        ei.setEventListener(this);
        this.ev = ev;
    }

    public EventPresenterImpl(EventInteractor ei, AllEventView aev) {
        this.ei = ei;
        ei.setAllEventListener(this);
        this.aev = aev;
    }

    /**
     * Implementacija funkcije sucelja EventPresenter. Funkcija pokusava dohvatiti buduce dogadaje.
     */
    @Override
    public void tryGetEvents() {
        ei.getEvent();
    }

    /**
     * Implementacija funkcije sucelja EventPresenter. Funkcija pokusava dohvatiti sve dogadaje.
     */
    @Override
    public void tryGetAllEvents() {
        ei.getAllEvents();
    }

    /**
     * Implementacija funkcije EventInteractorListener. Funkcija šalje pristigli događaj EventView-u.
     * @param eventList
     */
    @Override
    public void ArrivedEvents(List<EventModel> eventList) {
        ev.Arrived(eventList);
    }

    /**
     * Implementacija funkcije EventInteractorListener. Funkcija šalje poruku o nepostojanju događaja EventView.
     */
    @Override
    public void NoEvents() {
        ev.NoEvents("Nema budućih događanja!");
    }

    /**
     * Implementacija funkcije EventInteractorListener. Funkcija šalje pristigli sve događaje EventView-u.
     * @param allEventList
     */
    @Override
    public void ArrivedAllEvents(List<EventModel> allEventList) {
        aev.ArrivedAllEvents(allEventList);
    }

    /**
     * Implementacija funkcije EventInteractorListener. Funkcija šalje poruku o nepostojanju događaja EventView.
     */
    @Override
    public void NoAllEvents() {
        aev.NoAllEvents("U bazi nema zabilježenih događaja!");
    }
}
