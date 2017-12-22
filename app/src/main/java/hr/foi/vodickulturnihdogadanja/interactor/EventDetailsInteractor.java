package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.EventDetailsInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsInteractor {
    void setEventDetailsListener(EventDetailsInteractorListener listener);
    void getEventById(int eventId);
    void addEvaluation(int mark,int userId,int eventId);
}
