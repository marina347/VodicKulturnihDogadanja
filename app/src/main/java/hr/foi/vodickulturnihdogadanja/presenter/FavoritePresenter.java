package hr.foi.vodickulturnihdogadanja.presenter;

/**
 * Created by Mateja on 22-Nov-17.
 */

public interface FavoritePresenter {
    void tryGetFavorites(int userId);
    void tryDeleteFavorite(int userId, int eventId);
}
