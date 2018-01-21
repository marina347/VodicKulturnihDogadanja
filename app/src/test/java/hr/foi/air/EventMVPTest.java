package hr.foi.air;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.activity.MainActivity;
import hr.foi.air.interactor.EventInteractor;
import hr.foi.air.interactor.impl.EventInteractorImpl;
import hr.foi.air.model.EventModel;
import hr.foi.air.presenter.impl.EventPresenterImpl;
import hr.foi.air.view.AllEventView;
import hr.foi.air.view.EventView;

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

