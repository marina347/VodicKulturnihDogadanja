package hr.foi.vodickulturnihdogadanja.interactor.listener;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteInteractorListener {
    void onSuccess(List<EventModel> listFavorites);
    void noFavorites();
}
