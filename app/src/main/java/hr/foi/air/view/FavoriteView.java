package hr.foi.air.view;

import java.util.List;

import hr.foi.air.model.EventModel;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteView {
    void onSuccess(List<EventModel> list);
    void noFavorites(String error);

    void onSuccessDelete();
}
