package hr.foi.air.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.adapters.CommentAdapter;
import hr.foi.air.interactor.impl.CommentInteractorImpl;
import hr.foi.air.model.CommentModel;
import hr.foi.air.presenter.CommentPresenter;
import hr.foi.air.presenter.impl.CommentPresenterImpl;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.CommentView;

/**
 * Created by LEGION Y520 on 10.1.2018..
 */

public class CommentsFragment extends Fragment implements CommentView {
    @BindView(R.id.new_comment_text)
    TextView txtNewComment;
    @BindView(R.id.btn_new_comment)
    Button btnNewComment;
    private CommentPresenter commentPresenter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CommentAdapter commentAdapter;
    private int eventId;
    private List<CommentModel> comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comments, null);
        ButterKnife.bind(this, rootView);
        CommentPresenter cp = new CommentPresenterImpl(new CommentInteractorImpl(), this);
        this.commentPresenter=cp;

        if(LoggedUserData.getInstance().getTokenModel()!=null){
            txtNewComment.setVisibility(View.VISIBLE);
            btnNewComment.setVisibility(View.VISIBLE);
        }
        getActivity().setTheme(R.style.ColoredHandleCursorTheme);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        eventId=this.getArguments().getInt("eventId",-1);
        commentPresenter.tryGetComments(eventId);
    }

    /**
     * Funkcija salje pistiglu listu komentara Recycler adapteru da ih prikaze.
     * @param commenList
     */
    @Override
    public void ArrivedComments(List<CommentModel> commenList) {
        comments=commenList;
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_com);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        commentAdapter = new CommentAdapter(comments, getActivity());
        recyclerView.setAdapter(commentAdapter);
    }

    /**
     * Funkcija ispisuje poruku o nepostojecim komentarima.
     * @param s
     */
    @Override
    public void NoComment(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    /**
     * Funkcija sluzi za prikazivanje kreiranog komentara.
     * @param comment
     */
    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        commentPresenter.tryGetComments(eventId);
        Toast.makeText(getActivity(),R.string.successful_commenting,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    /**
     * Funkcija sluzi za ispis poruke kada se komentar neuspije stvoriti.
     * @param s
     */
    @Override
    public void onFailedCreateNewComment(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    /**
     * Funkcija sluzi za kreiranje novoga komentara.
     * @param view
     */
    @OnClick(R.id.btn_new_comment)
    public void NewCommentClick(View view){
        AddNewComment();
    }
    private void AddNewComment() {
        String newCommentText = txtNewComment.getText().toString();
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        if (!newCommentText.contentEquals("") && eventId!=-1 && userId!=-1){
            CommentModel commentModel = new CommentModel();
            commentModel.setText(newCommentText);
            commentModel.setTime(new Date().getTime());
            commentModel.setEventId(eventId);
            commentModel.setUserId(userId);
            commentPresenter.tryAddNewComment(commentModel);
        }
        else {
            Toast.makeText(getActivity(), R.string.no_text, Toast.LENGTH_LONG).show();
        }
    }
}
