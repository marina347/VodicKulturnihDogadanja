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
import hr.foi.air.fragments.FavoriteFragment;
import hr.foi.air.model.EventModel;
import hr.foi.air.utils.Base64Coding;

/**Klasa za prikaz favorita pomocu RecyclerView Adaptera
 * Created by marbulic on 12/10/2017.
 */

public class FavoriteEventsAdapter extends RecyclerView.Adapter<FavoriteEventsAdapter.EventViewHolder> implements Filterable {
    List<EventModel> eventList;
    List<EventModel> filtredEventList;
    Context context;
    final FavoriteFragment favoriteFragment;//

    public FavoriteEventsAdapter(List<EventModel> eventList, Context context, FavoriteFragment favoriteFragment) {//
        this.eventList = eventList;
        this.filtredEventList = eventList;
        this.context = context;
        this.favoriteFragment = favoriteFragment;//
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_event_list_item,parent,false);//
        return new EventViewHolder(view, favoriteFragment);//
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bind(filtredEventList.get(position));
    }

    @Override
    public int getItemCount() { return filtredEventList.size();
    }

    /**
     * Metoda za pretraživanje događaja
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
        @BindView(R.id.star)
        TextView favoriteStarButton;

        private EventModel mEvent;
        final View view;
        final FavoriteFragment favoriteFragment;//

        public EventViewHolder(View itemView, FavoriteFragment favoriteFragment) {//
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
            this.favoriteFragment = favoriteFragment;//
        }

        /**
         * Metoda za prikaz primljenih podataka od događaju
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
            configureFavoriteRemoval(favoriteStarButton, eventModel);//
        }

        /**
         * Metoda za otvaranje detalja odabranog događaja
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
         * Metoda vraća datum oblika dd.mm.yyyy.
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

        /**
         * Metoda za brisanje događaja iz popisa favorita
         * @param star
         * @param eventModel
         */
        private void configureFavoriteRemoval(final TextView star, final EventModel eventModel){
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteFragment.onEventRemoveFavorite(eventModel, getLayoutPosition());
                }
            });
        }



    }
}
