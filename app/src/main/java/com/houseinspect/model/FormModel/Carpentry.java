package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.ServerImageResponse;
import com.houseinspect.util.ImageUtil;

import java.util.List;

/**
 * Created by Lalit on 11/12/2016.
 */
public class Carpentry {

    @SerializedName("Quality")
    @Expose
    private InspectionParameter quality;
    @SerializedName("Damage to brickwork")
    @Expose
    private InspectionParameter damageToBrickwork;
    @SerializedName("Damage to concrete")
    @Expose
    private InspectionParameter damageToConcrete;

    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public InspectionParameter getQuality() {
        return quality;
    }

    public void setQuality(InspectionParameter quality) {
        this.quality = quality;
    }

    public InspectionParameter getDamageToBrickwork() {
        return damageToBrickwork;
    }

    public void setDamageToBrickwork(InspectionParameter damageToBrickwork) {
        this.damageToBrickwork = damageToBrickwork;
    }

    public InspectionParameter getDamageToConcrete() {
        return damageToConcrete;
    }

    public void setDamageToConcrete(InspectionParameter damageToConcrete) {
        this.damageToConcrete = damageToConcrete;
    }

    public boolean isCompleted() {
        if (quality == null)
            return false;
        if (damageToBrickwork == null)
            return false;
        if (damageToConcrete == null)
            return false;
        return true;
    }


    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (quality != null)
            imageUtil.setServerUrl(quality.getInspectionImages());
        if (damageToBrickwork != null)
            imageUtil.setServerUrl(damageToBrickwork.getInspectionImages());
        if (damageToConcrete != null)
            imageUtil.setServerUrl(damageToConcrete.getInspectionImages());
    }


    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (quality != null)
            imageUtil.removeBase64(quality.getInspectionImages());
        if (damageToBrickwork != null)
            imageUtil.removeBase64(damageToBrickwork.getInspectionImages());
        if (damageToConcrete != null)
            imageUtil.removeBase64(damageToConcrete.getInspectionImages());
    }

}
