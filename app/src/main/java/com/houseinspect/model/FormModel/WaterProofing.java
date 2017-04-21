package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.ServerImageResponse;
import com.houseinspect.util.ImageUtil;

import java.util.List;

/**
 * Created by Lalit on 11/14/2016.
 */
public class WaterProofing {

    @SerializedName("Fit for purpose")
    @Expose
    private InspectionParameter fitForPurpose;
    @SerializedName("Full-bores")
    @Expose
    private InspectionParameter fullBores;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public InspectionParameter getFitForPurpose() {
        return fitForPurpose;
    }

    public void setFitForPurpose(InspectionParameter fitForPurpose) {
        this.fitForPurpose = fitForPurpose;
    }

    public InspectionParameter getFullBores() {
        return fullBores;
    }

    public void setFullBores(InspectionParameter fullBores) {
        this.fullBores = fullBores;
    }

    public boolean isCompleted() {
        if (fitForPurpose == null)
            return false;
        if (fullBores == null)
            return false;
        return true;
    }

    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (fitForPurpose != null)
            imageUtil.setServerUrl(fitForPurpose.getInspectionImages());
        if (fullBores != null)
            imageUtil.setServerUrl(fullBores.getInspectionImages());
    }

    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (fitForPurpose != null)
            imageUtil.removeBase64(fitForPurpose.getInspectionImages());
        if (fullBores != null)
            imageUtil.removeBase64(fullBores.getInspectionImages());
    }
}