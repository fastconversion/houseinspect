package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.FormModel.DemographicDetails;

/**
 * Created by Lalit on 11/23/2016.
 */
public class HouseKeyDataModel {

    @SerializedName("uid")
    @Expose
    String uniqueKey;
    @SerializedName("Demographic Details")
    @Expose
    DemographicDetails demographicDetails;
    @SerializedName("mobile_key")
    @Expose
    String mobileKey;
    @SerializedName("is_approved")
    @Expose
    String isApproved;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    @SerializedName("expected_inspection_date")
    @Expose
    String expectedDate;

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public DemographicDetails getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public String getMobileKey() {
        return mobileKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getLongUpdatedOn(){
        if(updatedOn == null)
            return 0;
        return Long.parseLong(updatedOn);
    }
}
