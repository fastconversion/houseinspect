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
public class SuperStructure {

    @SerializedName("DPC")
    @Expose
    private InspectionParameter dPC;
    @SerializedName("Cavity Walls")
    @Expose
    private InspectionParameter cavityWalls;
    @SerializedName("Masonry")
    @Expose
    private InspectionParameter masonry;
    @SerializedName("Mortar")
    @Expose
    private InspectionParameter mortar;
    @SerializedName("RC concrete")
    @Expose
    private InspectionParameter rCConcrete;
    @SerializedName("Alignment of corners")
    @Expose
    private InspectionParameter alignmentOfCorners;
    @SerializedName("Masonry panels")
    @Expose
    private InspectionParameter masonryPanels;
    @SerializedName("Intersection of walls")
    @Expose
    private InspectionParameter intersectionOfWalls;
    @SerializedName("Building in of frames")
    @Expose
    private InspectionParameter buildingInOfFrames;
    @SerializedName("Chasing")
    @Expose
    private InspectionParameter chasing;
    @SerializedName("Brick force W/ties")
    @Expose
    private InspectionParameter brickForceWTies;
    @SerializedName("Control Joints")
    @Expose
    private InspectionParameter controlJoints;
    @SerializedName("Staircases")
    @Expose
    private InspectionParameter staircases;
    @SerializedName("Circular masonry")
    @Expose
    private InspectionParameter circularMasonry;
    @SerializedName("Lintel design and bearing")
    @Expose
    private InspectionParameter lintelDesignAndBearing;
    @SerializedName("Suspended Floors")
    @Expose
    private InspectionParameter suspendedFloors;
    @SerializedName("Joints in slabs")
    @Expose
    private InspectionParameter jointsInSlabs;
    @SerializedName("Roof anchors")
    @Expose
    private InspectionParameter roofAnchors;
    @SerializedName("Non-standard system")
    @Expose
    private InspectionParameter nonStandardSystem;
    @SerializedName("Timber construction")
    @Expose
    private InspectionParameter timberConstruction;
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

    public InspectionParameter getdPC() {
        return dPC;
    }

    public void setdPC(InspectionParameter dPC) {
        this.dPC = dPC;
    }

    public InspectionParameter getCavityWalls() {
        return cavityWalls;
    }

    public void setCavityWalls(InspectionParameter cavityWalls) {
        this.cavityWalls = cavityWalls;
    }

    public InspectionParameter getMasonry() {
        return masonry;
    }

    public void setMasonry(InspectionParameter masonry) {
        this.masonry = masonry;
    }

    public InspectionParameter getMortar() {
        return mortar;
    }

    public void setMortar(InspectionParameter mortar) {
        this.mortar = mortar;
    }

    public InspectionParameter getrCConcrete() {
        return rCConcrete;
    }

    public void setrCConcrete(InspectionParameter rCConcrete) {
        this.rCConcrete = rCConcrete;
    }

    public InspectionParameter getAlignmentOfCorners() {
        return alignmentOfCorners;
    }

    public void setAlignmentOfCorners(InspectionParameter alignmentOfCorners) {
        this.alignmentOfCorners = alignmentOfCorners;
    }

    public InspectionParameter getMasonryPanels() {
        return masonryPanels;
    }

    public void setMasonryPanels(InspectionParameter masonryPanels) {
        this.masonryPanels = masonryPanels;
    }

    public InspectionParameter getIntersectionOfWalls() {
        return intersectionOfWalls;
    }

    public void setIntersectionOfWalls(InspectionParameter intersectionOfWalls) {
        this.intersectionOfWalls = intersectionOfWalls;
    }

    public InspectionParameter getBuildingInOfFrames() {
        return buildingInOfFrames;
    }

    public void setBuildingInOfFrames(InspectionParameter buildingInOfFrames) {
        this.buildingInOfFrames = buildingInOfFrames;
    }

    public InspectionParameter getChasing() {
        return chasing;
    }

    public void setChasing(InspectionParameter chasing) {
        this.chasing = chasing;
    }

    public InspectionParameter getBrickForceWTies() {
        return brickForceWTies;
    }

    public void setBrickForceWTies(InspectionParameter brickForceWTies) {
        this.brickForceWTies = brickForceWTies;
    }

    public InspectionParameter getControlJoints() {
        return controlJoints;
    }

    public void setControlJoints(InspectionParameter controlJoints) {
        this.controlJoints = controlJoints;
    }

    public InspectionParameter getStaircases() {
        return staircases;
    }

    public void setStaircases(InspectionParameter staircases) {
        this.staircases = staircases;
    }

    public InspectionParameter getCircularMasonry() {
        return circularMasonry;
    }

    public void setCircularMasonry(InspectionParameter circularMasonry) {
        this.circularMasonry = circularMasonry;
    }

    public InspectionParameter getLintelDesignAndBearing() {
        return lintelDesignAndBearing;
    }

    public void setLintelDesignAndBearing(InspectionParameter lintelDesignAndBearing) {
        this.lintelDesignAndBearing = lintelDesignAndBearing;
    }

    public InspectionParameter getSuspendedFloors() {
        return suspendedFloors;
    }

    public void setSuspendedFloors(InspectionParameter suspendedFloors) {
        this.suspendedFloors = suspendedFloors;
    }

    public InspectionParameter getJointsInSlabs() {
        return jointsInSlabs;
    }

    public void setJointsInSlabs(InspectionParameter jointsInSlabs) {
        this.jointsInSlabs = jointsInSlabs;
    }

    public InspectionParameter getRoofAnchors() {
        return roofAnchors;
    }

    public void setRoofAnchors(InspectionParameter roofAnchors) {
        this.roofAnchors = roofAnchors;
    }

    public InspectionParameter getNonStandardSystem() {
        return nonStandardSystem;
    }

    public void setNonStandardSystem(InspectionParameter nonStandardSystem) {
        this.nonStandardSystem = nonStandardSystem;
    }

    public InspectionParameter getTimberConstruction() {
        return timberConstruction;
    }

    public void setTimberConstruction(InspectionParameter timberConstruction) {
        this.timberConstruction = timberConstruction;
    }

    public InspectionParameter getDocumentation() {
        return documentation;
    }

    public void setDocumentation(InspectionParameter documentation) {
        this.documentation = documentation;
    }

    public boolean isCompleted() {
        if (dPC == null)
            return false;
        if (cavityWalls == null)
            return false;
        if (masonry == null)
            return false;
        if (mortar == null)
            return false;
        if (rCConcrete == null)
            return false;
        if (alignmentOfCorners == null)
            return false;
        if (masonryPanels == null)
            return false;
        if (intersectionOfWalls == null)
            return false;
        if (buildingInOfFrames == null)
            return false;
        if (chasing == null)
            return false;
        if (brickForceWTies == null)
            return false;
        if (controlJoints == null)
            return false;
        if (staircases == null)
            return false;
        if (circularMasonry == null)
            return false;
        if (lintelDesignAndBearing == null)
            return false;
        if (suspendedFloors == null)
            return false;
        if (jointsInSlabs == null)
            return false;
        if (roofAnchors == null)
            return false;
        if (nonStandardSystem == null)
            return false;
        if (timberConstruction == null)
            return false;
        if (documentation == null)
            return false;
        return true;
    }

    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (dPC != null)
            imageUtil.setServerUrl(dPC.getInspectionImages());

        if (cavityWalls != null)
            imageUtil.setServerUrl(cavityWalls.getInspectionImages());

        if (masonry != null)
            imageUtil.setServerUrl(masonry.getInspectionImages());

        if (mortar != null)
            imageUtil.setServerUrl(mortar.getInspectionImages());

        if (rCConcrete != null)
            imageUtil.setServerUrl(rCConcrete.getInspectionImages());

        if (alignmentOfCorners != null)
            imageUtil.setServerUrl(alignmentOfCorners.getInspectionImages());

        if (masonryPanels != null)
            imageUtil.setServerUrl(masonryPanels.getInspectionImages());

        if (intersectionOfWalls != null)
            imageUtil.setServerUrl(intersectionOfWalls.getInspectionImages());

        if (buildingInOfFrames != null)
            imageUtil.setServerUrl(buildingInOfFrames.getInspectionImages());

        if (chasing != null)
            imageUtil.setServerUrl(chasing.getInspectionImages());

        if (brickForceWTies != null)
            imageUtil.setServerUrl(brickForceWTies.getInspectionImages());

        if (controlJoints != null)
            imageUtil.setServerUrl(controlJoints.getInspectionImages());

        if (staircases != null)
            imageUtil.setServerUrl(staircases.getInspectionImages());

        if (circularMasonry != null)
            imageUtil.setServerUrl(circularMasonry.getInspectionImages());

        if (lintelDesignAndBearing != null)
            imageUtil.setServerUrl(lintelDesignAndBearing.getInspectionImages());

        if (suspendedFloors != null)
            imageUtil.setServerUrl(suspendedFloors.getInspectionImages());

        if (jointsInSlabs != null)
            imageUtil.setServerUrl(jointsInSlabs.getInspectionImages());

        if (roofAnchors != null)
            imageUtil.setServerUrl(roofAnchors.getInspectionImages());

        if (nonStandardSystem != null)
            imageUtil.setServerUrl(nonStandardSystem.getInspectionImages());

        if (timberConstruction != null)
            imageUtil.setServerUrl(timberConstruction.getInspectionImages());

        if (documentation != null)
            imageUtil.setServerUrl(documentation.getInspectionImages());
    }

    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (dPC != null)
            imageUtil.removeBase64(dPC.getInspectionImages());
        if (cavityWalls != null)
            imageUtil.removeBase64(cavityWalls.getInspectionImages());
        if (masonry != null)
            imageUtil.removeBase64(masonry.getInspectionImages());
        if (mortar != null)
            imageUtil.removeBase64(mortar.getInspectionImages());
        if (rCConcrete != null)
            imageUtil.removeBase64(rCConcrete.getInspectionImages());
        if (alignmentOfCorners != null)
            imageUtil.removeBase64(alignmentOfCorners.getInspectionImages());
        if (masonryPanels != null)
            imageUtil.removeBase64(masonryPanels.getInspectionImages());
        if (intersectionOfWalls != null)
            imageUtil.removeBase64(intersectionOfWalls.getInspectionImages());
        if (buildingInOfFrames != null)
            imageUtil.removeBase64(buildingInOfFrames.getInspectionImages());
        if (chasing != null)
            imageUtil.removeBase64(chasing.getInspectionImages());
        if (brickForceWTies != null)
            imageUtil.removeBase64(brickForceWTies.getInspectionImages());
        if (controlJoints != null)
            imageUtil.removeBase64(controlJoints.getInspectionImages());
        if (staircases != null)
            imageUtil.removeBase64(staircases.getInspectionImages());
        if (circularMasonry != null)
            imageUtil.removeBase64(circularMasonry.getInspectionImages());
        if (lintelDesignAndBearing != null)
            imageUtil.removeBase64(lintelDesignAndBearing.getInspectionImages());
        if (suspendedFloors != null)
            imageUtil.removeBase64(suspendedFloors.getInspectionImages());
        if (jointsInSlabs != null)
            imageUtil.removeBase64(jointsInSlabs.getInspectionImages());
        if (roofAnchors != null)
            imageUtil.removeBase64(roofAnchors.getInspectionImages());
        if (nonStandardSystem != null)
            imageUtil.removeBase64(nonStandardSystem.getInspectionImages());
        if (timberConstruction != null)
            imageUtil.removeBase64(timberConstruction.getInspectionImages());
        if (documentation != null)
            imageUtil.removeBase64(documentation.getInspectionImages());
    }
}
