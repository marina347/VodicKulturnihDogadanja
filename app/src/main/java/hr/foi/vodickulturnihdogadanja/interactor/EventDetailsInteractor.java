package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.EventDetailsInteractorListener;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsInteractor {
    void setEventDetailsListener(EventDetailsInteractorListener listener);
    void getEventById(int eventId);
}