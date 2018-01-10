package hr.foi.vodickulturnihdogadanja.fragments;

import android.content.Intent;
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
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.adapters.CommentAdapter;
import hr.foi.vodickulturnihdogadanja.interactor.impl.CommentInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.presenter.CommentPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.CommentPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.CommentView;

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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Intent intent = getActivity().getIntent();
        //eventId=intent.getIntExtra("eventId", -1);
        eventId=this.getArguments().getInt("eventId",-1);
        commentPresenter.tryGetComments(eventId);
    }

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

    @Override
    public void NoComment(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        commentPresenter.tryGetComments(eventId);
        Toast.makeText(getActivity(),"Uspješno komentiranje",Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

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
            Toast.makeText(getActivity(), "Niste unijeli tekst", Toast.LENGTH_LONG).show();
        }
    }
}
