package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import hr.foi.vodickulturnihdogadanja.view.CommentView;

public class CommentActivity extends AppCompatActivity implements CommentView {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        CommentPresenter cp = new CommentPresenterImpl(new CommentInteractorImpl(), this);
        this.commentPresenter=cp;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        eventId=intent.getIntExtra("eventId", -1);
        commentPresenter.tryGetComments(eventId);

    }

    @Override
    public void ArrivedComments(List<CommentModel> commenList) {
        comments=commenList;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_com);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        commentAdapter = new CommentAdapter(comments, this);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public void NoComment(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        commentPresenter.tryGetComments(eventId);
        Toast.makeText(this,R.string.successful_commenting,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    @OnClick(R.id.btn_new_comment)
    public void NewCommentClick(View view){
        AddNewComment();
    }
    private void AddNewComment() {
        String newCommentText = txtNewComment.getText().toString();
        int userId =14; // LoggedUserData.getInstance().getTokenModel().getUserId();
        if (!newCommentText.contentEquals("") && eventId!=-1 && userId!=-1){
            CommentModel commentModel = new CommentModel();
            commentModel.setText(newCommentText);
            commentModel.setTime(new Date().getTime());
            commentModel.setEventId(eventId);
            commentModel.setUserId(userId);
            commentPresenter.tryAddNewComment(commentModel);
        }
        else {
            Toast.makeText(this, R.string.no_text, Toast.LENGTH_LONG).show();
        }
    }
}
