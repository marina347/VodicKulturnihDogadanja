package hr.foi.air.interactor.listener;

import hr.foi.air.model.EventModel;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsInteractorListener {
    void ArrivedEventById(EventModel event);
    void successAddedEvaluation();
    void failedAddedEvaluation();
    void onSuccessDeletedEvaluation();
    void onFailedDeletedEvaluation();
}
