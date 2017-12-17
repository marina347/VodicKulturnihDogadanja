package hr.foi.vodickulturnihdogadanja.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.model.CommentModel;

/**
 * Created by LEGION Y520 on 14.12.2017..
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<CommentModel> commentList;
    Context context;

    public  CommentAdapter(List<CommentModel> commentList, Context context){
        this.commentList=commentList;
        this.context=context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
