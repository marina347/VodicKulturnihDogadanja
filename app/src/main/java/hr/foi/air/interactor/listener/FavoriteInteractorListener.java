package hr.foi.air.interactor.listener;

import java.util.List;

import hr.foi.air.model.EventModel;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteInteractorListener {
    void onSuccess(List<EventModel> listFavorites);
    void noFavorites();
    void onSuccessDelete();
}
