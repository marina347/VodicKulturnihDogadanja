package hr.foi.air.view;

import java.util.List;

import hr.foi.air.model.EventModel;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public interface EventView {
    void Arrived(List<EventModel> list);
    void NoEvents(String error);
    //void ArrivedAllEvents(List<EventModel> list);
    //void NoAllEvents(String error);

}
