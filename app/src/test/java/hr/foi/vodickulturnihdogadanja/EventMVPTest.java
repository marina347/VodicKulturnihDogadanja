package hr.foi.vodickulturnihdogadanja;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.activity.MainActivity;
import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.AllEventView;
import hr.foi.vodickulturnihdogadanja.view.EventView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class EventMVPTest {

    EventInteractor interactor;
    EventView view;
    EventPresenterImpl eventPresenter;


    @Test
    public void testDisplayGetEventsForGuest() {
        view = mock(MainActivity.class);
        interactor = mock(EventInteractorImpl.class);
        eventPresenter = new EventPresenterImpl(interactor, view);

        EventModel eventModel = mock(EventModel.class);
        List<EventModel> eventModelList = new ArrayList<>(1);
        eventModelList.add(eventModel);

        eventPresenter.tryGetEvents();
        verify(interactor).getEvent();
        eventPresenter.ArrivedEvents(eventModelList);
        verify(view).Arrived(eventModelList);
    }

    @Test
    public void testDisplayGetEvents() {
        view = mock(EventView.class);
        interactor = mock(EventInteractorImpl.class);
        eventPresenter = new EventPresenterImpl(interactor, view);

        EventModel eventModel = mock(EventModel.class);
        List<EventModel> eventModelList = new ArrayList<>(1);
        eventModelList.add(eventModel);

        eventPresenter.tryGetEvents();
        verify(interactor).getEvent();
        eventPresenter.ArrivedEvents(eventModelList);
        verify(view).Arrived(eventModelList);
    }

    @Test
    public void testDisplayGetAllEvents() {
        AllEventView view = mock(AllEventView.class);
        interactor = mock(EventInteractorImpl.class);
        eventPresenter = new EventPresenterImpl(interactor, view);

        EventModel eventModel = mock(EventModel.class);
        List<EventModel> eventModelList = new ArrayList<>(1);
        eventModelList.add(eventModel);

        eventPresenter.tryGetAllEvents();
        verify(interactor).getAllEvents();
        eventPresenter.ArrivedAllEvents(eventModelList);
        verify(view).ArrivedAllEvents(eventModelList);
    }
}

