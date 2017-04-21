package com.houseinspect.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.FormModel.DemographicDetails;

/**
 * Created by Lalit on 1/6/2017.
 */
public class Payload {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("Demographic Details")
    @Expose
    private DemographicDetails demographicDetails;

    @SerializedName("expected_inspection_date")
    @Expose
    String expectedDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public DemographicDetails getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }
}