package hr.foi.vodickulturnihdogadanja;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.FavoritePresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.FavoriteView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class FavoriteMVPTest {
    FavoriteInteractorImpl interactor;
    FavoriteView view;
    FavoritePresenterImpl favoritePresenter;

    @Test
    public void testDisplayGetFavorites() {
        view = mock(FavoriteView.class);
        interactor = mock(FavoriteInteractorImpl.class);
        favoritePresenter = new FavoritePresenterImpl(interactor, view);

        int userId=1;

        EventModel eventModel = mock(EventModel.class);
        List<EventModel> eventModelList = new ArrayList<>(1);
        eventModelList.add(eventModel);

        favoritePresenter.tryGetFavorites(userId);
        verify(interactor).getFavorite(userId);
        favoritePresenter.onSuccess(eventModelList);
        verify(view).onSuccess(eventModelList);

    }

    @Test
    public void testDisplayDeleteFavorites() {
        view = mock(FavoriteView.class);
        interactor = mock(FavoriteInteractorImpl.class);
        favoritePresenter = new FavoritePresenterImpl(interactor, view);

        int userId=1;
        int eventId=1;

        favoritePresenter.tryDeleteFavorite(userId,eventId);
        verify(interactor).deleteFavorite(userId,eventId);
        favoritePresenter.onSuccessDelete();
        verify(view).onSuccessDelete();

    }
}
