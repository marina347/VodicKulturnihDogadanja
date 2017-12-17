package hr.foi.vodickulturnihdogadanja.interactor.listener;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsInteractorListener {
    void ArrivedEventById(EventModel event);
    void successAddedEvaluation();
    void failedAddedEvaluation();
}
