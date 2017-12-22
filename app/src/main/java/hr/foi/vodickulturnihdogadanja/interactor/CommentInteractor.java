package hr.foi.vodickulturnihdogadanja.interactor;

import hr.foi.vodickulturnihdogadanja.interactor.listener.CommentInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public interface CommentInteractor {
    void setCommentListener(CommentInteractorListener commentListener);
    void getComments(int eventId);
    void CreateNewComment(CommentModel comment);
}
