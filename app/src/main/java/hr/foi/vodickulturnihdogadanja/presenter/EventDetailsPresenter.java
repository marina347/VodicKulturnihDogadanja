package hr.foi.vodickulturnihdogadanja.presenter;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsPresenter {
    void tryGetEventById(int eventId);
    void tryAddFavorite(int eventId);
}
