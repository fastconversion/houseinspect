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
public class StormWater {

    @SerializedName("Water ponding")
    @Expose
    private InspectionParameter waterPonding;
    @SerializedName("Slab above NGL")
    @Expose
    private InspectionParameter slabAboveNGL;
    @SerializedName("Sewer trenches")
    @Expose
    private InspectionParameter sewerTrenches;
    @SerializedName("High banks")
    @Expose
    private InspectionParameter highBanks;
    @SerializedName("Boundary walls")
    @Expose
    private InspectionParameter boundaryWalls;
    @SerializedName("Pools, etc.")
    @Expose
    private InspectionParameter poolsEtc;
    @SerializedName("Documentation")
    @Expose
    private InspectionParameter documentation;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public InspectionParameter getWaterPonding() {
        return waterPonding;
    }

    public void setWaterPonding(InspectionParameter waterPonding) {
        this.waterPonding = waterPonding;
    }

    public InspectionParameter getSlabAboveNGL() {
        return slabAboveNGL;
    }

    public void setSlabAboveNGL(InspectionParameter slabAboveNGL) {
        this.slabAboveNGL = slabAboveNGL;
    }

    public InspectionParameter getSewerTrenches() {
        return sewerTrenches;
    }

    public void setSewerTrenches(InspectionParameter sewerTrenches) {
        this.sewerTrenches = sewerTrenches;
    }

    public InspectionParameter getHighBanks() {
        return highBanks;
    }

    public void setHighBanks(InspectionParameter highBanks) {
        this.highBanks = highBanks;
    }

    public InspectionParameter getBoundaryWalls() {
        return boundaryWalls;
    }

    public void setBoundaryWalls(InspectionParameter boundaryWalls) {
        this.boundaryWalls = boundaryWalls;
    }

    public InspectionParameter getPoolsEtc() {
        return poolsEtc;
    }

    public void setPoolsEtc(InspectionParameter poolsEtc) {
        this.poolsEtc = poolsEtc;
    }

    public InspectionParameter getDocumentation() {
        return documentation;
    }

    public void setDocumentation(InspectionParameter documentation) {
        this.documentation = documentation;
    }

    public boolean isCompleted() {
        if (waterPonding == null)
            return false;
        if (slabAboveNGL == null)
            return false;
        if (sewerTrenches == null)
            return false;
        if (highBanks == null)
            return false;
        if (boundaryWalls == null)
            return false;
        if (poolsEtc == null)
            return false;
        if (documentation == null)
            return false;
        return true;
    }
    
    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (waterPonding != null)
            imageUtil.setServerUrl(waterPonding.getInspectionImages());
        if (slabAboveNGL != null)
            imageUtil.setServerUrl(slabAboveNGL.getInspectionImages());
        if (sewerTrenches != null)
            imageUtil.setServerUrl(sewerTrenches.getInspectionImages());
        if (highBanks != null)
            imageUtil.setServerUrl(highBanks.getInspectionImages());
        if (boundaryWalls != null)
            imageUtil.setServerUrl(boundaryWalls.getInspectionImages());
        if (poolsEtc != null)
            imageUtil.setServerUrl(poolsEtc.getInspectionImages());
        if (documentation != null)
            imageUtil.setServerUrl(documentation.getInspectionImages());
    }

    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (waterPonding != null)
            imageUtil.removeBase64(waterPonding.getInspectionImages());
        if (slabAboveNGL != null)
            imageUtil.removeBase64(slabAboveNGL.getInspectionImages());
        if (sewerTrenches != null)
            imageUtil.removeBase64(sewerTrenches.getInspectionImages());
        if (highBanks != null)
            imageUtil.removeBase64(highBanks.getInspectionImages());
        if (boundaryWalls != null)
            imageUtil.removeBase64(boundaryWalls.getInspectionImages());
        if (poolsEtc != null)
            imageUtil.removeBase64(poolsEtc.getInspectionImages());
        if (documentation != null)
            imageUtil.removeBase64(documentation.getInspectionImages());
    }
}