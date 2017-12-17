package hr.foi.vodickulturnihdogadanja.presenter.impl;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.interactor.CommentInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.listener.CommentInteractorListener;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.presenter.CommentPresenter;
import hr.foi.vodickulturnihdogadanja.view.CommentView;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public class CommentPresenterImpl implements CommentPresenter, CommentInteractorListener {
    CommentInteractor commentInteractor;
    CommentView commentView;

    public CommentPresenterImpl(CommentInteractor commentInteractor, CommentView commentView) {
        this.commentInteractor = commentInteractor;
        commentInteractor.setCommentListener(this);
        this.commentView = commentView;
    }


    @Override
    public void tryGetComments(int eventId) {
        commentInteractor.getComments(eventId);
    }

    @Override
    public void tryAddNewComment(CommentModel comment) {
        commentInteractor.CreateNewComment(comment);
    }

    @Override
    public void NoComment() {
        commentView.NoComment("Nema postojećih komentar!");
    }

    @Override
    public void ArrivedComments(List<CommentModel> list) {
        commentView.ArrivedComments(list);
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        commentView.onSuccessCreateNewComment(comment);
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        commentView.onFailedCreateNewComment(s);
    }
}

