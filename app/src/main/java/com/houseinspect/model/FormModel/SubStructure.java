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
public class SubStructure {

    @SerializedName("Soil Classification")
    @Expose
    private InspectionParameter soilClassification;
    @SerializedName("Dolomite areas")
    @Expose
    private InspectionParameter dolomiteAreas;
    @SerializedName("Site clearance")
    @Expose
    private InspectionParameter siteClearance;
    @SerializedName("Water logged site")
    @Expose
    private InspectionParameter waterLoggedSite;
    @SerializedName("Excavations")
    @Expose
    private InspectionParameter excavations;
    @SerializedName("Raft slabs")
    @Expose
    private InspectionParameter raftSlabs;
    @SerializedName("Reinforcement")
    @Expose
    private InspectionParameter reinforcement;
    @SerializedName("Concrete")
    @Expose
    private InspectionParameter concrete;
    @SerializedName("Masonry")
    @Expose
    private InspectionParameter masonry;
    @SerializedName("Infill of masonry")
    @Expose
    private InspectionParameter infillOfMasonry;
    @SerializedName("Brickforce W/Ties")
    @Expose
    private InspectionParameter brickforceWTies;
    @SerializedName("Filling")
    @Expose
    private InspectionParameter filling;
    @SerializedName("Surface water disposal")
    @Expose
    private InspectionParameter surfaceWaterDisposal;
    @SerializedName("Dpm under floors")
    @Expose
    private InspectionParameter dpmUnderFloors;
    @SerializedName("Fabric reinforcement")
    @Expose
    private InspectionParameter fabricReinforcement;
    @SerializedName("Concrete surface beds")
    @Expose
    private InspectionParameter concreteSurfaceBeds;
    @SerializedName("Construction joints")
    @Expose
    private InspectionParameter constructionJoints;
    @SerializedName("Basement split level")
    @Expose
    private InspectionParameter basementSplitLevel;
    @SerializedName("Suspended timber floors")
    @Expose
    private InspectionParameter suspendedTimberFloors;
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

    public InspectionParameter getSoilClassification() {
        return soilClassification;
    }

    public void setSoilClassification(InspectionParameter soilClassification) {
        this.soilClassification = soilClassification;
    }

    public InspectionParameter getDolomiteAreas() {
        return dolomiteAreas;
    }

    public void setDolomiteAreas(InspectionParameter dolomiteAreas) {
        this.dolomiteAreas = dolomiteAreas;
    }

    public InspectionParameter getSiteClearance() {
        return siteClearance;
    }

    public void setSiteClearance(InspectionParameter siteClearance) {
        this.siteClearance = siteClearance;
    }

    public InspectionParameter getWaterLoggedSite() {
        return waterLoggedSite;
    }

    public void setWaterLoggedSite(InspectionParameter waterLoggedSite) {
        this.waterLoggedSite = waterLoggedSite;
    }

    public InspectionParameter getExcavations() {
        return excavations;
    }

    public void setExcavations(InspectionParameter excavations) {
        this.excavations = excavations;
    }

    public InspectionParameter getRaftSlabs() {
        return raftSlabs;
    }

    public void setRaftSlabs(InspectionParameter raftSlabs) {
        this.raftSlabs = raftSlabs;
    }

    public InspectionParameter getReinforcement() {
        return reinforcement;
    }

    public void setReinforcement(InspectionParameter reinforcement) {
        this.reinforcement = reinforcement;
    }

    public InspectionParameter getConcrete() {
        return concrete;
    }

    public void setConcrete(InspectionParameter concrete) {
        this.concrete = concrete;
    }

    public InspectionParameter getMasonry() {
        return masonry;
    }

    public void setMasonry(InspectionParameter masonry) {
        this.masonry = masonry;
    }

    public InspectionParameter getInfillOfMasonry() {
        return infillOfMasonry;
    }

    public void setInfillOfMasonry(InspectionParameter infillOfMasonry) {
        this.infillOfMasonry = infillOfMasonry;
    }

    public InspectionParameter getBrickforceWTies() {
        return brickforceWTies;
    }

    public void setBrickforceWTies(InspectionParameter brickforceWTies) {
        this.brickforceWTies = brickforceWTies;
    }

    public InspectionParameter getFilling() {
        return filling;
    }

    public void setFilling(InspectionParameter filling) {
        this.filling = filling;
    }

    public InspectionParameter getSurfaceWaterDisposal() {
        return surfaceWaterDisposal;
    }

    public void setSurfaceWaterDisposal(InspectionParameter surfaceWaterDisposal) {
        this.surfaceWaterDisposal = surfaceWaterDisposal;
    }

    public InspectionParameter getDpmUnderFloors() {
        return dpmUnderFloors;
    }

    public void setDpmUnderFloors(InspectionParameter dpmUnderFloors) {
        this.dpmUnderFloors = dpmUnderFloors;
    }

    public InspectionParameter getFabricReinforcement() {
        return fabricReinforcement;
    }

    public void setFabricReinforcement(InspectionParameter fabricReinforcement) {
        this.fabricReinforcement = fabricReinforcement;
    }

    public InspectionParameter getConcreteSurfaceBeds() {
        return concreteSurfaceBeds;
    }

    public void setConcreteSurfaceBeds(InspectionParameter concreteSurfaceBeds) {
        this.concreteSurfaceBeds = concreteSurfaceBeds;
    }

    public InspectionParameter getConstructionJoints() {
        return constructionJoints;
    }

    public void setConstructionJoints(InspectionParameter constructionJoints) {
        this.constructionJoints = constructionJoints;
    }

    public InspectionParameter getBasementSplitLevel() {
        return basementSplitLevel;
    }

    public void setBasementSplitLevel(InspectionParameter basementSplitLevel) {
        this.basementSplitLevel = basementSplitLevel;
    }

    public InspectionParameter getSuspendedTimberFloors() {
        return suspendedTimberFloors;
    }

    public void setSuspendedTimberFloors(InspectionParameter suspendedTimberFloors) {
        this.suspendedTimberFloors = suspendedTimberFloors;
    }

    public InspectionParameter getDocumentation() {
        return documentation;
    }

    public void setDocumentation(InspectionParameter documentation) {
        this.documentation = documentation;
    }

    public boolean isCompleted() {
        if (soilClassification == null)
            return false;
        if (dolomiteAreas == null)
            return false;
        if (siteClearance == null)
            return false;
        if (waterLoggedSite == null)
            return false;
        if (excavations == null)
            return false;
        if (raftSlabs == null)
            return false;
        if (reinforcement == null)
            return false;
        if (concrete == null)
            return false;
        if (masonry == null)
            return false;
        if (infillOfMasonry == null)
            return false;
        if (brickforceWTies == null)
            return false;
        if (filling == null)
            return false;
        if (surfaceWaterDisposal == null)
            return false;
        if (dpmUnderFloors == null)
            return false;
        if (fabricReinforcement == null)
            return false;
        if (concreteSurfaceBeds == null)
            return false;
        if (constructionJoints == null)
            return false;
        if (basementSplitLevel == null)
            return false;
        if (suspendedTimberFloors == null)
            return false;
        if (documentation == null)
            return false;
        return true;
    }


    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (soilClassification != null)
            imageUtil.setServerUrl(soilClassification.getInspectionImages());
        if (dolomiteAreas != null)
            imageUtil.setServerUrl(dolomiteAreas.getInspectionImages());
        if (siteClearance != null)
            imageUtil.setServerUrl(siteClearance.getInspectionImages());
        if (waterLoggedSite != null)
            imageUtil.setServerUrl(waterLoggedSite.getInspectionImages());
        if (excavations != null)
            imageUtil.setServerUrl(excavations.getInspectionImages());
        if (raftSlabs != null)
            imageUtil.setServerUrl(raftSlabs.getInspectionImages());
        if (reinforcement != null)
            imageUtil.setServerUrl(reinforcement.getInspectionImages());
        if (concrete != null)
            imageUtil.setServerUrl(concrete.getInspectionImages());
        if (masonry != null)
            imageUtil.setServerUrl(masonry.getInspectionImages());
        if (infillOfMasonry != null)
            imageUtil.setServerUrl(infillOfMasonry.getInspectionImages());
        if (brickforceWTies != null)
            imageUtil.setServerUrl(brickforceWTies.getInspectionImages());
        if (filling != null)
            imageUtil.setServerUrl(filling.getInspectionImages());
        if (surfaceWaterDisposal != null)
            imageUtil.setServerUrl(surfaceWaterDisposal.getInspectionImages());
        if (dpmUnderFloors != null)
            imageUtil.setServerUrl(dpmUnderFloors.getInspectionImages());
        if (fabricReinforcement != null)
            imageUtil.setServerUrl(fabricReinforcement.getInspectionImages());
        if (concreteSurfaceBeds != null)
            imageUtil.setServerUrl(concreteSurfaceBeds.getInspectionImages());
        if (constructionJoints != null)
            imageUtil.setServerUrl(constructionJoints.getInspectionImages());
        if (basementSplitLevel != null)
            imageUtil.setServerUrl(basementSplitLevel.getInspectionImages());
        if (suspendedTimberFloors != null)
            imageUtil.setServerUrl(suspendedTimberFloors.getInspectionImages());
        if (documentation != null)
            imageUtil.setServerUrl(documentation.getInspectionImages());
    }


    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (soilClassification != null)
            imageUtil.removeBase64(soilClassification.getInspectionImages());
        if (dolomiteAreas != null)
            imageUtil.removeBase64(dolomiteAreas.getInspectionImages());
        if (siteClearance != null)
            imageUtil.removeBase64(siteClearance.getInspectionImages());
        if (waterLoggedSite != null)
            imageUtil.removeBase64(waterLoggedSite.getInspectionImages());
        if (excavations != null)
            imageUtil.removeBase64(excavations.getInspectionImages());
        if (raftSlabs != null)
            imageUtil.removeBase64(raftSlabs.getInspectionImages());
        if (reinforcement != null)
            imageUtil.removeBase64(reinforcement.getInspectionImages());
        if (concrete != null)
            imageUtil.removeBase64(concrete.getInspectionImages());
        if (masonry != null)
            imageUtil.removeBase64(masonry.getInspectionImages());
        if (infillOfMasonry != null)
            imageUtil.removeBase64(infillOfMasonry.getInspectionImages());
        if (brickforceWTies != null)
            imageUtil.removeBase64(brickforceWTies.getInspectionImages());
        if (filling != null)
            imageUtil.removeBase64(filling.getInspectionImages());
        if (surfaceWaterDisposal != null)
            imageUtil.removeBase64(surfaceWaterDisposal.getInspectionImages());
        if (dpmUnderFloors != null)
            imageUtil.removeBase64(dpmUnderFloors.getInspectionImages());
        if (fabricReinforcement != null)
            imageUtil.removeBase64(fabricReinforcement.getInspectionImages());
        if (concreteSurfaceBeds != null)
            imageUtil.removeBase64(concreteSurfaceBeds.getInspectionImages());
        if (constructionJoints != null)
            imageUtil.removeBase64(constructionJoints.getInspectionImages());
        if (basementSplitLevel != null)
            imageUtil.removeBase64(basementSplitLevel.getInspectionImages());
        if (suspendedTimberFloors != null)
            imageUtil.removeBase64(suspendedTimberFloors.getInspectionImages());
        if (documentation != null)
            imageUtil.removeBase64(documentation.getInspectionImages());
    }


}
