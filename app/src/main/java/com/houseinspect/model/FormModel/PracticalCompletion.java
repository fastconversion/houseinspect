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
public class PracticalCompletion {

    @SerializedName("Wall Plate")
    @Expose
    private InspectionParameter wallPlate;
    @SerializedName("Timber Qualitity Size")
    @Expose
    private InspectionParameter timberQualititySize;
    @SerializedName("Purlin, beams rafters")
    @Expose
    private InspectionParameter purlinBeamsRafters;
    @SerializedName("Roof Pitch")
    @Expose
    private InspectionParameter roofPitch;
    @SerializedName("Nail plated trusses")
    @Expose
    private InspectionParameter nailPlatedTrusses;
    @SerializedName("Site made trusses")
    @Expose
    private InspectionParameter siteMadeTrusses;
    @SerializedName("Hangers and brackets")
    @Expose
    private InspectionParameter hangersAndBrackets;
    @SerializedName("Bracing")
    @Expose
    private InspectionParameter bracing;
    @SerializedName("Pole structures(Thatched)")
    @Expose
    private InspectionParameter poleStructuresThatched;
    @SerializedName("Battens and purlins")
    @Expose
    private InspectionParameter battensAndPurlins;
    @SerializedName("Roof covering")
    @Expose
    private InspectionParameter roofCovering;
    @SerializedName("Under tile membranes")
    @Expose
    private InspectionParameter underTileMembranes;
    @SerializedName("Valley lining")
    @Expose
    private InspectionParameter valleyLining;
    @SerializedName("Beam filling")
    @Expose
    private InspectionParameter beamFilling;
    @SerializedName("Fire Walls")
    @Expose
    private InspectionParameter fireWalls;
    @SerializedName("Brandering")
    @Expose
    private InspectionParameter brandering;
    @SerializedName("Flashings")
    @Expose
    private InspectionParameter flashings;
    @SerializedName("Geyser installation")
    @Expose
    private InspectionParameter geyserInstallation;
    @SerializedName("Concrete roofs")
    @Expose
    private InspectionParameter concreteRoofs;
    @SerializedName("Metal lath")
    @Expose
    private InspectionParameter metalLath;
    @SerializedName("Plaster mix")
    @Expose
    private InspectionParameter plasterMix;
    @SerializedName("Weep holes")
    @Expose
    private InspectionParameter weepHoles;
    @SerializedName("Glazing")
    @Expose
    private InspectionParameter glazing;
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

    public InspectionParameter getWallPlate() {
        return wallPlate;
    }

    public void setWallPlate(InspectionParameter wallPlate) {
        this.wallPlate = wallPlate;
    }

    public InspectionParameter getTimberQualititySize() {
        return timberQualititySize;
    }

    public void setTimberQualititySize(InspectionParameter timberQualititySize) {
        this.timberQualititySize = timberQualititySize;
    }

    public InspectionParameter getPurlinBeamsRafters() {
        return purlinBeamsRafters;
    }

    public void setPurlinBeamsRafters(InspectionParameter purlinBeamsRafters) {
        this.purlinBeamsRafters = purlinBeamsRafters;
    }

    public InspectionParameter getRoofPitch() {
        return roofPitch;
    }

    public void setRoofPitch(InspectionParameter roofPitch) {
        this.roofPitch = roofPitch;
    }

    public InspectionParameter getNailPlatedTrusses() {
        return nailPlatedTrusses;
    }

    public void setNailPlatedTrusses(InspectionParameter nailPlatedTrusses) {
        this.nailPlatedTrusses = nailPlatedTrusses;
    }

    public InspectionParameter getSiteMadeTrusses() {
        return siteMadeTrusses;
    }

    public void setSiteMadeTrusses(InspectionParameter siteMadeTrusses) {
        this.siteMadeTrusses = siteMadeTrusses;
    }

    public InspectionParameter getHangersAndBrackets() {
        return hangersAndBrackets;
    }

    public void setHangersAndBrackets(InspectionParameter hangersAndBrackets) {
        this.hangersAndBrackets = hangersAndBrackets;
    }

    public InspectionParameter getBracing() {
        return bracing;
    }

    public void setBracing(InspectionParameter bracing) {
        this.bracing = bracing;
    }

    public InspectionParameter getPoleStructuresThatched() {
        return poleStructuresThatched;
    }

    public void setPoleStructuresThatched(InspectionParameter poleStructuresThatched) {
        this.poleStructuresThatched = poleStructuresThatched;
    }

    public InspectionParameter getBattensAndPurlins() {
        return battensAndPurlins;
    }

    public void setBattensAndPurlins(InspectionParameter battensAndPurlins) {
        this.battensAndPurlins = battensAndPurlins;
    }

    public InspectionParameter getRoofCovering() {
        return roofCovering;
    }

    public void setRoofCovering(InspectionParameter roofCovering) {
        this.roofCovering = roofCovering;
    }

    public InspectionParameter getUnderTileMembranes() {
        return underTileMembranes;
    }

    public void setUnderTileMembranes(InspectionParameter underTileMembranes) {
        this.underTileMembranes = underTileMembranes;
    }

    public InspectionParameter getValleyLining() {
        return valleyLining;
    }

    public void setValleyLining(InspectionParameter valleyLining) {
        this.valleyLining = valleyLining;
    }

    public InspectionParameter getBeamFilling() {
        return beamFilling;
    }

    public void setBeamFilling(InspectionParameter beamFilling) {
        this.beamFilling = beamFilling;
    }

    public InspectionParameter getFireWalls() {
        return fireWalls;
    }

    public void setFireWalls(InspectionParameter fireWalls) {
        this.fireWalls = fireWalls;
    }

    public InspectionParameter getBrandering() {
        return brandering;
    }

    public void setBrandering(InspectionParameter brandering) {
        this.brandering = brandering;
    }

    public InspectionParameter getFlashings() {
        return flashings;
    }

    public void setFlashings(InspectionParameter flashings) {
        this.flashings = flashings;
    }

    public InspectionParameter getGeyserInstallation() {
        return geyserInstallation;
    }

    public void setGeyserInstallation(InspectionParameter geyserInstallation) {
        this.geyserInstallation = geyserInstallation;
    }

    public InspectionParameter getConcreteRoofs() {
        return concreteRoofs;
    }

    public void setConcreteRoofs(InspectionParameter concreteRoofs) {
        this.concreteRoofs = concreteRoofs;
    }

    public InspectionParameter getMetalLath() {
        return metalLath;
    }

    public void setMetalLath(InspectionParameter metalLath) {
        this.metalLath = metalLath;
    }

    public InspectionParameter getPlasterMix() {
        return plasterMix;
    }

    public void setPlasterMix(InspectionParameter plasterMix) {
        this.plasterMix = plasterMix;
    }

    public InspectionParameter getWeepHoles() {
        return weepHoles;
    }

    public void setWeepHoles(InspectionParameter weepHoles) {
        this.weepHoles = weepHoles;
    }

    public InspectionParameter getGlazing() {
        return glazing;
    }

    public void setGlazing(InspectionParameter glazing) {
        this.glazing = glazing;
    }

    public InspectionParameter getDocumentation() {
        return documentation;
    }

    public void setDocumentation(InspectionParameter documentation) {
        this.documentation = documentation;
    }

    public boolean isCompleted() {
        if (wallPlate == null)
            return false;
        if (timberQualititySize == null)
            return false;
        if (purlinBeamsRafters == null)
            return false;
        if (roofPitch == null)
            return false;
        if (nailPlatedTrusses == null)
            return false;
        if (siteMadeTrusses == null)
            return false;
        if (hangersAndBrackets == null)
            return false;
        if (bracing == null)
            return false;
        if (poleStructuresThatched == null)
            return false;
        if (battensAndPurlins == null)
            return false;
        if (roofCovering == null)
            return false;
        if (underTileMembranes == null)
            return false;
        if (valleyLining == null)
            return false;
        if (beamFilling == null)
            return false;
        if (fireWalls == null)
            return false;
        if (brandering == null)
            return false;
        if (flashings == null)
            return false;
        if (geyserInstallation == null)
            return false;
        if (concreteRoofs == null)
            return false;
        if (metalLath == null)
            return false;
        if (plasterMix == null)
            return false;
        if (weepHoles == null)
            return false;
        if (glazing == null)
            return false;
        if (documentation == null)
            return false;
        return true;
    }

    public void setAllInspectionServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;

        ImageUtil imageUtil = new ImageUtil(serverImageResponseList);
        if (wallPlate != null)
            imageUtil.setServerUrl(wallPlate.getInspectionImages());
        if (timberQualititySize != null)
            imageUtil.setServerUrl(timberQualititySize.getInspectionImages());
        if (purlinBeamsRafters != null)
            imageUtil.setServerUrl(purlinBeamsRafters.getInspectionImages());
        if (roofPitch != null)
            imageUtil.setServerUrl(roofPitch.getInspectionImages());
        if (nailPlatedTrusses != null)
            imageUtil.setServerUrl(nailPlatedTrusses.getInspectionImages());
        if (siteMadeTrusses != null)
            imageUtil.setServerUrl(siteMadeTrusses.getInspectionImages());
        if (hangersAndBrackets != null)
            imageUtil.setServerUrl(hangersAndBrackets.getInspectionImages());
        if (bracing != null)
            imageUtil.setServerUrl(bracing.getInspectionImages());
        if (poleStructuresThatched != null)
            imageUtil.setServerUrl(poleStructuresThatched.getInspectionImages());
        if (battensAndPurlins != null)
            imageUtil.setServerUrl(battensAndPurlins.getInspectionImages());
        if (roofCovering != null)
            imageUtil.setServerUrl(roofCovering.getInspectionImages());
        if (underTileMembranes != null)
            imageUtil.setServerUrl(underTileMembranes.getInspectionImages());
        if (valleyLining != null)
            imageUtil.setServerUrl(valleyLining.getInspectionImages());
        if (beamFilling != null)
            imageUtil.setServerUrl(beamFilling.getInspectionImages());
        if (fireWalls != null)
            imageUtil.setServerUrl(fireWalls.getInspectionImages());
        if (brandering != null)
            imageUtil.setServerUrl(brandering.getInspectionImages());
        if (flashings != null)
            imageUtil.setServerUrl(flashings.getInspectionImages());
        if (geyserInstallation != null)
            imageUtil.setServerUrl(geyserInstallation.getInspectionImages());
        if (concreteRoofs != null)
            imageUtil.setServerUrl(concreteRoofs.getInspectionImages());
        if (metalLath != null)
            imageUtil.setServerUrl(metalLath.getInspectionImages());
        if (plasterMix != null)
            imageUtil.setServerUrl(plasterMix.getInspectionImages());
        if (weepHoles != null)
            imageUtil.setServerUrl(weepHoles.getInspectionImages());
        if (glazing != null)
            imageUtil.setServerUrl(glazing.getInspectionImages());
        if (documentation != null)
            imageUtil.setServerUrl(documentation.getInspectionImages());
    }


    public void removeBase64() {
        ImageUtil imageUtil = new ImageUtil();
        if (wallPlate != null)
            imageUtil.removeBase64(wallPlate.getInspectionImages());
        if (timberQualititySize != null)
            imageUtil.removeBase64(timberQualititySize.getInspectionImages());
        if (purlinBeamsRafters != null)
            imageUtil.removeBase64(purlinBeamsRafters.getInspectionImages());
        if (roofPitch != null)
            imageUtil.removeBase64(roofPitch.getInspectionImages());
        if (nailPlatedTrusses != null)
            imageUtil.removeBase64(nailPlatedTrusses.getInspectionImages());
        if (siteMadeTrusses != null)
            imageUtil.removeBase64(siteMadeTrusses.getInspectionImages());
        if (hangersAndBrackets != null)
            imageUtil.removeBase64(hangersAndBrackets.getInspectionImages());
        if (bracing != null)
            imageUtil.removeBase64(bracing.getInspectionImages());
        if (poleStructuresThatched != null)
            imageUtil.removeBase64(poleStructuresThatched.getInspectionImages());
        if (battensAndPurlins != null)
            imageUtil.removeBase64(battensAndPurlins.getInspectionImages());
        if (roofCovering != null)
            imageUtil.removeBase64(roofCovering.getInspectionImages());
        if (underTileMembranes != null)
            imageUtil.removeBase64(underTileMembranes.getInspectionImages());
        if (valleyLining != null)
            imageUtil.removeBase64(valleyLining.getInspectionImages());
        if (beamFilling != null)
            imageUtil.removeBase64(beamFilling.getInspectionImages());
        if (fireWalls != null)
            imageUtil.removeBase64(fireWalls.getInspectionImages());
        if (brandering != null)
            imageUtil.removeBase64(brandering.getInspectionImages());
        if (flashings != null)
            imageUtil.removeBase64(flashings.getInspectionImages());
        if (geyserInstallation != null)
            imageUtil.removeBase64(geyserInstallation.getInspectionImages());
        if (concreteRoofs != null)
            imageUtil.removeBase64(concreteRoofs.getInspectionImages());
        if (metalLath != null)
            imageUtil.removeBase64(metalLath.getInspectionImages());
        if (plasterMix != null)
            imageUtil.removeBase64(plasterMix.getInspectionImages());
        if (weepHoles != null)
            imageUtil.removeBase64(weepHoles.getInspectionImages());
        if (glazing != null)
            imageUtil.removeBase64(glazing.getInspectionImages());
        if (documentation != null)
            imageUtil.removeBase64(documentation.getInspectionImages());
    }
}