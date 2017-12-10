package hr.foi.vodickulturnihdogadanja.view;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.EventModel;
import okhttp3.ResponseBody;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteView {
    void onSuccess(List<EventModel> list);
    void noFavorites(String error);

    void onSuccessDelete();
}
