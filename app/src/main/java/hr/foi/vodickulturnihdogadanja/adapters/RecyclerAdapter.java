package hr.foi.vodickulturnihdogadanja.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.model.EventModel;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.EventViewHolder>{
    List<EventModel> eventList;
    Context context;

    public RecyclerAdapter(List<EventModel> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getName());
        holder.eventDescription.setText(eventList.get(position).getDescription());
        holder.eventBegin.setText(DateConverter(eventList.get(position).getBegin()));
        holder.eventEnd.setText(DateConverter(eventList.get(position).getEnd()));
    }

    @Override
    public int getItemCount() { return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.event_name)
        TextView eventName;
        @BindView(R.id.event_description)
        TextView eventDescription;
        @BindView(R.id.event_begin)
        TextView eventBegin;
        @BindView(R.id.event_end)
        TextView eventEnd;
        @BindView(R.id.event_image)
        ImageView eventImage;

        View view;
        public EventViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this,itemView);
        }
    }
    private String DateConverter(Long date){
        if(date == 0 ){
            return "";
        }else {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
            Date d=new Date(date);
            return df.format(d);
        }
    };
}
