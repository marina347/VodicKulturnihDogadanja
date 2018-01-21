package hr.foi.air.view;

import hr.foi.air.model.EventModel;

/**
 * Created by LEGION Y520 on 23.11.2017..
 */

public interface EventDetailsView {
    void ArrivedEvent(EventModel event);
    void onSuccessAddedFavorite();
    void onSuccessAddedEvaluation();
    void onFailedAddedEvaluation();
    void onSuccessDeletedEvaluation();
    void onFailedDeletedEvaluation();
}
