package hr.foi.vodickulturnihdogadanja.view;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public interface CommentView {
    void ArrivedComments(List<CommentModel> commenList);
    void NoComment(String s);
    void onSuccessCreateNewComment(CommentModel comment);
    void onFailedCreateNewComment(String s);
}
