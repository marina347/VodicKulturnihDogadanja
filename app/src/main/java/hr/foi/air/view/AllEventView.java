package hr.foi.air.view;

import java.util.List;

import hr.foi.air.model.EventModel;

/**
 * Created by Mateja on 12-Jan-18.
 */

public interface AllEventView {
    void ArrivedAllEvents(List<EventModel> list);
    void NoAllEvents(String error);
}
