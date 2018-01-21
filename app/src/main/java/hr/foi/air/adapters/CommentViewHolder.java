package hr.foi.air.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.R;
import hr.foi.air.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.comment_user)
    TextView commentUser;
    @BindView(R.id.comment_datetime)
    TextView commentDatetime;
    @BindView(R.id.comment_text)
    TextView commentText;

    View view;
    public CommentViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(CommentModel comment){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        Date d = new Date(comment.getTime());
        commentDatetime.setText(df.format(d));
        commentText.setText(comment.getText());
        commentUser.setText(comment.getUsername());
        //commentUser.setText(comment.getUserId());
    }
}

