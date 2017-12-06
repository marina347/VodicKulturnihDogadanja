package hr.foi.vodickulturnihdogadanja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.EventInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventDetailsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.interactor.impl.EventInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;
import hr.foi.vodickulturnihdogadanja.model.EventModel;
import hr.foi.vodickulturnihdogadanja.model.TokenModel;
import hr.foi.vodickulturnihdogadanja.presenter.EventDetailsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.EventPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventDetailsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.presenter.impl.EventPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.EventDetailsView;

public class EventDetailsActivity extends AppCompatActivity implements EventDetailsView{
    @BindView(R.id.event_details_name)
    TextView txtEventName;
    @BindView(R.id.event_details_description)
    TextView txtEventDescription;
    @BindView(R.id.event_details_begin)
    TextView txtEventBegin;
    @BindView(R.id.event_details_end)
    TextView txtEventEnd;
    @BindView(R.id.event_details_link)
    TextView txtEventLink;
    @BindView(R.id.event_details_location)
    TextView txtEventLocation;
    @BindView(R.id.new_comment_text)
    TextView txtNewComment;
    @BindView(R.id.btn_new_comment)
    Button btnNewComment;
    @BindView(R.id.event_details_img)
    ImageView imgEvent;

    EventDetailsPresenter dp;
    int eventId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        EventDetailsPresenter edp = new EventDetailsPresenterImpl(new EventDetailsInteractorImpl(), this);
        this.dp=edp;

        TokenModel token = LoggedUserData.getInstance().getTokenModel();
        if (token != null){
            txtNewComment.setVisibility(View.VISIBLE);
            btnNewComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        eventId = intent.getIntExtra("id",-1);
        if(eventId != -1)
        {
            dp.tryGetEventById(eventId);
        }
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
            dp.tryAddNewComment(commentModel);
        }
        else {
            Toast.makeText(this, "Niste unijeli tekst", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void ArrivedEvent(EventModel event) {
        txtEventName.setText(event.getName());
        txtEventDescription.setText(event.getDescription());
        txtEventBegin.setText(DateConverter(event.getBegin()));
        if(event.getEnd()>0){
            txtEventEnd.setText("-"+DateConverter(event.getEnd()));
        }
        else {
            txtEventEnd.setText("");
        }

        txtEventLocation.setText(event.getLocation());
        txtEventLink.setText(event.getLink());
        imgEvent.setImageBitmap(Base64Coding.decodeBase64(event.getPicture()));
    }

    @Override
    public void onSuccessCreateNewComment(CommentModel comment) {
        Toast.makeText(this,"Uspješno komentiranje",Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    @Override
    public void onFailedCreateNewComment(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        txtNewComment.setText("");
    }

    private String DateConverter(Long date) {
        if (date == 0) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
            Date d = new Date(date);
            return df.format(d);
        }
    };
}
