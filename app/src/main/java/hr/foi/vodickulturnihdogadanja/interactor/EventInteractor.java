package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.EventInteractorListener;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public interface EventInteractor {
    void setEventListener(EventInteractorListener listener);
    void getEvent();
}
