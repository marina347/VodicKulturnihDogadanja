package hr.foi.vodickulturnihdogadanja.model;

import com.google.gson.annotations.Expose;

/**
 * Created by LEGION Y520 on 30.10.2017..
 */

public class EventModel {
    @Expose(serialize = false)
    private int eventId;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Long begin;
    @Expose
    private Long end;
    @Expose
    private String link;
    @Expose
    private String location;
    @Expose
    private float price;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
