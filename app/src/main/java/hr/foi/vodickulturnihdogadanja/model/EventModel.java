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
    @Expose
    private int numOfLikes;
    @Expose
    private int numOfDislikes;
    @Expose
    private int isFavorite;

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getUserEval() {
        return userEval;
    }

    public void setUserEval(int userEval) {
        this.userEval = userEval;
    }

    @Expose
    private int userEval;

    public int getNumOfDislikes() {
        return numOfDislikes;
    }

    public void setNumOfDislikes(int numOfDislikes) {
        this.numOfDislikes = numOfDislikes;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Expose
    private String picture;


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
