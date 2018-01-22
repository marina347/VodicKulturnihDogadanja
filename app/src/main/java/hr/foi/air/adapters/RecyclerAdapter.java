package hr.foi.air.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.R;
import hr.foi.air.activity.EventDetailsActivity;
import hr.foi.air.model.EventModel;
import hr.foi.air.utils.Base64Coding;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.EventViewHolder> implements Filterable{
    List<EventModel> eventList;
    List<EventModel> filtredEventList;
    Context context;

    public RecyclerAdapter(List<EventModel> eventList, Context context) {
        this.eventList = eventList;
        this.filtredEventList = eventList;
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bind(filtredEventList.get(position));
    }

    @Override
    public int getItemCount() { return filtredEventList.size();
    }

    /**
     * Funkcija služi za filtriranje/pretraživanje događaja.
     * @return
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filtredEventList = eventList;
                } else {

                    ArrayList<EventModel> filteredList = new ArrayList<>();

                    for (EventModel events : eventList) {

                        if (events.getName().toLowerCase().contains(charString)) {

                            filteredList.add(events);
                        }
                    }

                    filtredEventList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =filtredEventList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtredEventList = (ArrayList<EventModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_name)
        TextView eventName;
        @BindView(R.id.event_description)
        TextView eventDescription;
        @BindView(R.id.event_begin)
        TextView eventBegin;
        @BindView(R.id.event_image)
        ImageView eventImage;


        private EventModel mEvent;
        View view;

        public EventViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        /**
         * Funkcija prikazuje primljene podatke o događaju.
         * @param eventModel
         */
        public void bind(EventModel eventModel) {
            mEvent = eventModel;
            eventName.setText(eventModel.getName());
            eventDescription.setText(eventModel.getDescription());
            eventBegin.setText(DateConverter(eventModel.getBegin()));
            if(eventModel.getEnd().longValue() != 0){
                eventBegin.setText(DateConverter(eventModel.getBegin()) + " - " + DateConverter(eventModel.getEnd()));
            }
            eventImage.setImageBitmap(Base64Coding.decodeBase64(eventModel.getPicture()));
        }

        /**
         * Funkcija otvara novu detalje o događaju za odabrani događaj.
         */
        @OnClick
        public void selectedEvent() {
            Bundle args = new Bundle();
            args.putInt("id", mEvent.getEventId());
            args.putString("location", mEvent.getLocation());
            Intent intent = new Intent(view.getContext(), EventDetailsActivity.class);
            intent.putExtras(args);
            view.getContext().startActivity(intent);
        }

        /**
         * Funkcija vraća datum oblika dd.mm.yyyy.)
         * @param date
         * @return
         */
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
}
