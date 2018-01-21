package hr.foi.air.presenter;

import hr.foi.air.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public interface CommentPresenter{
    void tryGetComments(int eventId);
    void tryAddNewComment(CommentModel comment);
}
