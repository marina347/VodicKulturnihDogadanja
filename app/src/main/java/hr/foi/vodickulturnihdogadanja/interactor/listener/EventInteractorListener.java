package hr.foi.vodickulturnihdogadanja.interactor.listener;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public interface EventInteractorListener {
    void ArrivedEvents(List<EventModel> eventList);
    void NoEvents();
    void ArrivedAllEvents(List<EventModel> allEventList);
    void NoAllEvents();
}
