package hr.foi.vodickulturnihdogadanja.view;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by Mateja on 12-Jan-18.
 */

public interface AllEventView {
    void ArrivedAllEvents(List<EventModel> list);
    void NoAllEvents(String error);
}
