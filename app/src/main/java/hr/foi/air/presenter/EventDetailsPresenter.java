package hr.foi.air.presenter;

/**
 * Created by LEGION Y520 on 24.11.2017..
 */

public interface EventDetailsPresenter {
    void tryGetEventById(int eventId);
    void tryAddFavorite(int eventId);
    void tryAddEvaluation(int mark,int userId, int eventId);
    void tryDeleteEvaluation(int userId,int eventId);
}
