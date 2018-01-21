package hr.foi.vodickulturnihdogadanja;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.fragments.CommentsFragment;
import hr.foi.vodickulturnihdogadanja.interactor.CommentInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.CommentInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.CommentPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.CommentView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class CommentMVPTest {
    CommentInteractor interactor;
    CommentView view;
    CommentPresenterImpl commentPresenter;

    @Test
    public void testDisplayGetComments() {
        view = mock(CommentsFragment.class);
        interactor = mock(CommentInteractorImpl.class);
        commentPresenter = new CommentPresenterImpl(interactor, view);

        int eventId = 1;
        CommentModel commentModel = mock(CommentModel.class);
        List<CommentModel> commentModelList = new ArrayList<>(1);
        commentModelList.add(commentModel);

        commentPresenter.tryGetComments(eventId);
        verify(interactor).getComments(eventId);
        commentPresenter.ArrivedComments(commentModelList);
        verify(view).ArrivedComments(commentModelList);
    }

    @Test
    public void testDisplayAddComment() {
        view = mock(CommentsFragment.class);
        interactor = mock(CommentInteractorImpl.class);
        commentPresenter = new CommentPresenterImpl(interactor, view);


        CommentModel commentModel = mock(CommentModel.class);
        List<CommentModel> commentModelList = new ArrayList<>(1);
        commentModelList.add(commentModel);

        commentPresenter.tryAddNewComment(commentModel);
        verify(interactor).CreateNewComment(commentModel);
        commentPresenter.onSuccessCreateNewComment(commentModel);
        verify(view).onSuccessCreateNewComment(commentModel);
    }
}
