package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.UserAccessData;

/**
 * Created by Lalit on 12/8/2016.
 */
public class SeniorInspectorSubmission {

    @SerializedName("house_id")
    @Expose
    private String houseId;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("final_comment")
    @Expose
    private String finalComment;
    @SerializedName("submission_date")
    @Expose
    private String submissionDate;
    @SerializedName("submitted_by_user")
    @Expose
    private UserAccessData submittedByUser;

    /**
     *
     * @return
     * The houseId
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     *
     * @param houseId
     * The house_id
     */
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    /**
     *
     * @return
     * The finalComment
     */
    public String getFinalComment() {
        return finalComment;
    }

    /**
     *
     * @param finalComment
     * The final_comment
     */
    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    /**
     *
     * @return
     * The submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     *
     * @param submissionDate
     * The submission_date
     */
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public UserAccessData getSubmittedByUser() {
        return submittedByUser;
    }

    public void setSubmittedByUser(UserAccessData submittedByUser) {
        this.submittedByUser = submittedByUser;
    }
}