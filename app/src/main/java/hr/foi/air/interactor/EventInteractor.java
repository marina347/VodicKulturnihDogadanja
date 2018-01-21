package hr.foi.air.interactor;

import hr.foi.air.interactor.listener.EventInteractorListener;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public interface EventInteractor {
    void setEventListener(EventInteractorListener listener);
    void getEvent();
    void setAllEventListener(EventInteractorListener listener);
    void getAllEvents();
}
