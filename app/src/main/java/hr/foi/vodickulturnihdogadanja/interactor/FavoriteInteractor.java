package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.AddFavoriteInteractorListener;
import hr.foi.vodickulturnihdogadanja.interactor.listener.FavoriteInteractorListener;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoriteInteractor {
    void setFavoriteListener(FavoriteInteractorListener listener);
    void setAddFavoriteListener(AddFavoriteInteractorListener listener);
    void getFavorite(int userId);
    void deleteFavorite(int userId, int eventId);
    void addFavorite(int userId,int eventId);
}
