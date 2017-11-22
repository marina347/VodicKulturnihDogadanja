package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.FavoriteInteractorListener;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteInteractor {
    void setFavoriteListener(FavoriteInteractorListener listener);
    void getFavorite(int userId);
}
