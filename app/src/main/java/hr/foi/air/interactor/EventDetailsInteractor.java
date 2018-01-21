package hr.foi.air.interactor;

import hr.foi.air.interactor.listener.EventDetailsInteractorListener;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsInteractor {
    void setEventDetailsListener(EventDetailsInteractorListener listener);
    void getEventById(int eventId);
    void addEvaluation(int mark,int userId,int eventId);
    void deleteEvaluation(int userId,int eventId);
}
