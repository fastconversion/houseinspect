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
public class Electrical {

    @SerializedName("Conduets built in securely")
    @Expose
    private InspectionParameter conduetsBuiltInSecurely;
    @SerializedName("Conduets built in < 1/3")
    @Expose
    private InspectionParameter conduetsBuiltIn13;
    @SerializedName("Concrete cover")
    @Expose
    private InspectionParameter concreteCover;
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

    public InspectionParameter getConduetsBuiltInSecurely() {
        return conduetsBuiltInSecurely;
    }

    public void setConduetsBuiltInSecurely(InspectionParameter conduetsBuiltInSecurely) {
        this.conduetsBuiltInSecurely = conduetsBuiltInSecurely;
    }

    public InspectionParameter getConduetsBuiltIn13() {
        return conduetsBuiltIn13;
    }

    public void setConduetsBuiltIn13(InspectionParameter conduetsBuiltIn13) {
        this.conduetsBuiltIn13 = conduetsBuiltIn13;
    }

    public InspectionParameter getConcreteCover() {
        return concreteCover;
    }

    public void setConcreteCover(InspectionParameter concreteCover) {
        this.concreteCover = concreteCover;
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
        if (conduetsBuiltInSecurely == null)
            return false;
        if (conduetsBuiltIn13 == null)
            return false;
        if (concreteCover == null)
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
        if (conduetsBuiltInSecurely != null)
            imageUtil.setServerUrl(conduetsBuiltInSecurely.getInspectionImages());
        if (conduetsBuiltIn13 != null)
            imageUtil.setServerUrl(conduetsBuiltIn13.getInspectionImages());
        if (concreteCover != null)
            imageUtil.setServerUrl(concreteCover.getInspectionImages());
        if (fittingsSecurelyFixed != null)
            imageUtil.setServerUrl(fittingsSecurelyFixed.getInspectionImages());
        if (fittingsPlumbAndSquare != null)
            imageUtil.setServerUrl(fittingsPlumbAndSquare.getInspectionImages());
    }

    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (conduetsBuiltInSecurely != null)
            imageUtil.removeBase64(conduetsBuiltInSecurely.getInspectionImages());
        if (conduetsBuiltIn13 != null)
            imageUtil.removeBase64(conduetsBuiltIn13.getInspectionImages());
        if (concreteCover != null)
            imageUtil.removeBase64(concreteCover.getInspectionImages());
        if (fittingsSecurelyFixed != null)
            imageUtil.removeBase64(fittingsSecurelyFixed.getInspectionImages());
        if (fittingsPlumbAndSquare != null)
            imageUtil.removeBase64(fittingsPlumbAndSquare.getInspectionImages());
    }
}
