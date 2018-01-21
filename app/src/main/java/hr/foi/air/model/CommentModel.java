package hr.foi.air.model;

import com.google.gson.annotations.Expose;

/**
 * Created by LEGION Y520 on 6.12.2017..
 */

public class CommentModel {
    @Expose(serialize = false)
    private int commentId;
    @Expose
    private String text;
    @Expose
    private Long time;
    @Expose
    private int userId;
    @Expose
    private String username;
    @Expose
    private int eventId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
