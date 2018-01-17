package hr.foi.vodickulturnihdogadanja;

import org.junit.Test;

import hr.foi.vodickulturnihdogadanja.fragments.EventDetailsFragment;
import hr.foi.vodickulturnihdogadanja.interactor.EventDetailsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.FavoriteInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.FavoriteInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class EventDetailsMVPTest {
    EventDetailsInteractor interactor;
    FavoriteInteractor finteractor;
    EventDetailsView view;
    EventDetailsPresenterImpl eventDetailsPresenter;


    @Test
    public void testDisplayAddFavorite() {
        view = mock(EventDetailsFragment.class);
        interactor = mock(EventDetailsInteractor.class);
        finteractor=mock(FavoriteInteractorImpl.class);
        eventDetailsPresenter = new EventDetailsPresenterImpl(interactor,finteractor,view);

        int userId=1;
        int eventId=2;

        eventDetailsPresenter.tryAddFavorite(eventId);
        verify(finteractor).addFavorite(userId,eventId);
        eventDetailsPresenter.onAddSuccess();
        verify(view).onSuccessAddedFavorite();
    }

    @Test
    public void testDisplayAddEvaluation() {
        view = mock(EventDetailsFragment.class);
        interactor = mock(EventDetailsInteractor.class);
        finteractor=mock(FavoriteInteractorImpl.class);
        eventDetailsPresenter = new EventDetailsPresenterImpl(interactor,finteractor,view);

        int userId=1;
        int eventId=2;
        int mark=1;

        eventDetailsPresenter.tryAddEvaluation(mark,userId,eventId);
        verify(interactor).addEvaluation(mark,userId,eventId);
        eventDetailsPresenter.successAddedEvaluation();
        verify(view).onSuccessAddedEvaluation();

    }

    @Test
    public void testDisplayGetEventById() {
        view = mock(EventDetailsFragment.class);
        interactor = mock(EventDetailsInteractorImpl.class);
        finteractor=mock(FavoriteInteractorImpl.class);
        eventDetailsPresenter = new EventDetailsPresenterImpl(interactor,finteractor,view);

        int eventId=2;
        TokenModel model=new TokenModel();

        model.setUserId(1);
        model.setTokenId("fffff");
        model.setTime(10000000);
        LoggedUserData.getInstance().setTokenModel(model);

        EventModel eventModel=mock(EventModel.class);

        eventDetailsPresenter.tryGetEventById(eventId);
        verify(interactor).getEventById(eventId);
        eventDetailsPresenter.ArrivedEventById(eventModel);
        verify(view).ArrivedEvent(eventModel);
    }
}

