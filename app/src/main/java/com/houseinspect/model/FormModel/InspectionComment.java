package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.CommentUser;

/**
 * Created by Lalit on 12/7/2016.
 */
public class InspectionComment {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("comment_date")
    @Expose
    private String commentDate;
    @SerializedName("comment_user")
    @Expose
    private CommentUser commentUser;

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The commentDate
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     *
     * @param commentDate
     * The comment_date
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    /**
     *
     * @return
     * The commentUser
     */
    public CommentUser getCommentUser() {
        return commentUser;
    }

    /**
     *
     * @param commentUser
     * The comment_user
     */
    public void setCommentUser(CommentUser commentUser) {
        this.commentUser = commentUser;
    }

}