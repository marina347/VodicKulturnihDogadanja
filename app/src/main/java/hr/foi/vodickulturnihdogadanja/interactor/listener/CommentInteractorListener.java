package hr.foi.vodickulturnihdogadanja.interactor.listener;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public interface CommentInteractorListener {
    void NoComment();
    void ArrivedComments(List<CommentModel> list);

    void onSuccessCreateNewComment(CommentModel comment);

    void onFailedCreateNewComment(String s);
}
