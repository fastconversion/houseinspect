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
public class Plumbing {

    @SerializedName("Pipes built in")
    @Expose
    private InspectionParameter pipesBuiltIn;
    @SerializedName("Pipes buily in < 1/3")
    @Expose
    private InspectionParameter pipesBuilyIn13;
    @SerializedName("Pipes cast in < 50%")
    @Expose
    private InspectionParameter pipesCastIn50;
    @SerializedName("Fittings securely fixed")
    @Expose
    private InspectionParameter fittingsSecurelyFixed;
    @SerializedName("Fittings plumb and square")
    @Expose
    private InspectionParameter fittingsPlumbAndSquare;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public InspectionParameter getPipesBuiltIn() {
        return pipesBuiltIn;
    }

    public void setPipesBuiltIn(InspectionParameter pipesBuiltIn) {
        this.pipesBuiltIn = pipesBuiltIn;
    }

    public InspectionParameter getPipesBuilyIn13() {
        return pipesBuilyIn13;
    }

    public void setPipesBuilyIn13(InspectionParameter pipesBuilyIn13) {
        this.pipesBuilyIn13 = pipesBuilyIn13;
    }

    public InspectionParameter getPipesCastIn50() {
        return pipesCastIn50;
    }

    public void setPipesCastIn50(InspectionParameter pipesCastIn50) {
        this.pipesCastIn50 = pipesCastIn50;
    }

    public InspectionParameter getFittingsSecurelyFixed() {
        return fittingsSecurelyFixed;
    }

    public void setFittingsSecurelyFixed(InspectionParameter fittingsSecurelyFixed) {
        this.fittingsSecurelyFixed = fittingsSecurelyFixed;
    }

    public InspectionParameter getFittingsPlumbAndSquare() {
        return fittingsPlumbAndSquare;
    }

    public void setFittingsPlumbAndSquare(InspectionParameter fittingsPlumbAndSquare) {
        this.fittingsPlumbAndSquare = fittingsPlumbAndSquare;
    }

    public boolean isCompleted() {
        if (pipesBuiltIn == null)
            return false;
        if (pipesBuilyIn13 == null)
            return false;
        if (pipesCastIn50 == null)
            return false;
        if (fittingsSecurelyFixed == null)
            return false;
        if (fittingsPlumbAndSquare == null)
            return false;
        return true;
    }

    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (pipesBuiltIn != null)
            imageUtil.setServerUrl(pipesBuiltIn.getInspectionImages());
        if (pipesBuilyIn13 != null)
            imageUtil.setServerUrl(pipesBuilyIn13.getInspectionImages());
        if (pipesCastIn50 != null)
            imageUtil.setServerUrl(pipesCastIn50.getInspectionImages());
        if (fittingsSecurelyFixed != null)
            imageUtil.setServerUrl(fittingsSecurelyFixed.getInspectionImages());
        if (fittingsPlumbAndSquare != null)
            imageUtil.setServerUrl(fittingsPlumbAndSquare.getInspectionImages());
    }

    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (pipesBuiltIn != null)
            imageUtil.removeBase64(pipesBuiltIn.getInspectionImages());
        if (pipesBuilyIn13 != null)
            imageUtil.removeBase64(pipesBuilyIn13.getInspectionImages());
        if (pipesCastIn50 != null)
            imageUtil.removeBase64(pipesCastIn50.getInspectionImages());
        if (fittingsSecurelyFixed != null)
            imageUtil.removeBase64(fittingsSecurelyFixed.getInspectionImages());
        if (fittingsPlumbAndSquare != null)
            imageUtil.removeBase64(fittingsPlumbAndSquare.getInspectionImages());
    }
}
