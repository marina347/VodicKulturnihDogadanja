package hr.foi.air.interactor.listener;

import java.util.List;

import hr.foi.air.model.EventModel;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public interface EventInteractorListener {
    void ArrivedEvents(List<EventModel> eventList);
    void NoEvents();
    void ArrivedAllEvents(List<EventModel> allEventList);
    void NoAllEvents();
}
