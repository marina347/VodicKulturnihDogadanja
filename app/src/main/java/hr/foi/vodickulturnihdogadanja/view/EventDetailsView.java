package hr.foi.vodickulturnihdogadanja.view;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by LEGION Y520 on 23.11.2017..
 */

public interface EventDetailsView {
    void ArrivedEvent(EventModel event);
    void onSuccessAddedFavorite();
    void onSuccessAddedEvaluation();
    void onFailedAddedEvaluation();
}
