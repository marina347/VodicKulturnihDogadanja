package hr.foi.air.presenter.impl;

import java.util.List;

import hr.foi.air.interactor.CommentInteractor;
import hr.foi.air.interactor.listener.CommentInteractorListener;
import hr.foi.air.model.CommentModel;
import hr.foi.air.presenter.CommentPresenter;
import hr.foi.air.view.CommentView;

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

    /**
     *  Implementacija funkcije sučelja CommentPresenter. Funkcija pokušava dohvatiti komentare. Id događaja šalje se interactoru.
     * @param eventId
     */
    @Override
    public void tryGetComments(int eventId) {
        commentInteractor.getComments(eventId);
    }

    /**
     * Implementacija funkcije sučelja CommentPresenter. Funkcija pokušava kreirati novi komentar. Šalje komentar interactoru.
     * @param comment
     */
    @Override
    public void tryAddNewComment(CommentModel comment) {
        commentInteractor.CreateNewComment(comment);
    }

    /**
     * Implementacija funkcije sučelja CommentInteractorListener. Funkcija poziva funkciju NoComment sučelja CommentView.
     */
    @Override
    public void NoComment() {
        commentView.NoComment("Nema postojećih komentar!");
    }

    /**
     * Implementacija funkcije sučelja CommentInteractorListener. Funkcija šalje sve komentare CommentView.
     * @param list
     */
    @Override
    public void ArrivedComments(List<CommentModel> list) {
        commentView.ArrivedComments(list);
    }

    /**
     * Implementacija funkcije sučelja CommentInteractorListener. Funkcija šalje uspješno krirani komentar CommentView
     * @param comment
     */
    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        commentView.onSuccessCreateNewComment(comment);
    }

    /**
     *  Implementacija funkcije sučelja CommentInteractorListener. Funkcija šalje poruku o neuspjelom kreiranju komentara u CommentView.
     * @param s
     */
    @Override
    public void onFailedCreateNewComment(String s) {
        commentView.onFailedCreateNewComment(s);
    }
}

