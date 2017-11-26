package hr.foi.vodickulturnihdogadanja.interactor.listener;

import okhttp3.ResponseBody;

/**
 * Created by Mateja on 25-Nov-17.
 */

public interface FavoriteAddInteractorListener {
    void onSuccess (ResponseBody responseBody);
}
