package hr.foi.air.interactor;

import hr.foi.air.interactor.listener.CommentInteractorListener;
import hr.foi.air.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public interface CommentInteractor {
    void setCommentListener(CommentInteractorListener commentListener);
    void getComments(int eventId);
    void CreateNewComment(CommentModel comment);
}
