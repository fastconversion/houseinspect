package com.houseinspect.activity.controller;

import com.houseinspect.model.FormModel.Carpentry;
import com.houseinspect.model.FormModel.Electrical;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.FormModel.Plumbing;
import com.houseinspect.model.FormModel.PracticalCompletion;
import com.houseinspect.model.FormModel.StormWater;
import com.houseinspect.model.FormModel.SubStructure;
import com.houseinspect.model.FormModel.SuperStructure;
import com.houseinspect.model.FormModel.WaterProofing;

/**
 * Created by Lalit on 11/14/2016.
 */
public class OldDataAvailabilityCheck {


    public static InspectionParameter getInspectionParameter(HouseDataModel houseDataModel, String mainForm,
                                                             String formType) {
        if (houseDataModel == null)
            return null;
        switch (mainForm) {
            case "Sub-structure/Foundation":
                if (houseDataModel.getSubStructure() == null)
                    return null;
                else
                    return getSubStructureInspectionParameter(formType, houseDataModel.getSubStructure());
            case "Super-structure":
                if (houseDataModel.getSuperStructure() == null)
                    return null;
                else
                    return getSuperStructureInspectionParameter(formType, houseDataModel.getSuperStructure());
            case "Practical Completion":
                if (houseDataModel.getPracticalCompletion() == null)
                    return null;
                else
                    return getPracticalCompletionInspectionParameter(formType, houseDataModel.getPracticalCompletion());
            case "Storm Water":
                if (houseDataModel.getStormWater() == null)
                    return null;
                else
                    return getStormWaterInspectionParameter(formType, houseDataModel.getStormWater());
            case "Carpentry":
                if (houseDataModel.getCarpentry() == null)
                    return null;
                else
                    return getCarpentryInspectionParameter(formType, houseDataModel.getCarpentry());
            case "Plumbing":
                if (houseDataModel.getPlumbing() == null)
                    return null;
                else
                    return getPlumbingInspectionParameter(formType, houseDataModel.getPlumbing());
            case "Electrical":
                if (houseDataModel.getElectrical() == null)
                    return null;
                else
                    return getElectricalInspectionParameter(formType, houseDataModel.getElectrical());
            case "Water Proofing":
                if (houseDataModel.getWaterProofing() == null)
                    return null;
                else
                    return getWaterProofingInspectionParameter(formType, houseDataModel.getWaterProofing());
            default:
        }
        return null;
    }

    private static InspectionParameter getWaterProofingInspectionParameter(String formType, WaterProofing waterProofing) {
        switch (formType) {
            case "Fit for purpose":
                return waterProofing.getFitForPurpose();
            case "Full-bores":
                return waterProofing.getFullBores();

        }
        return null;
    }

    private static InspectionParameter getElectricalInspectionParameter(String formType, Electrical electrical) {
        switch (formType) {
            case "Conduets built in securely":
                return electrical.getConduetsBuiltInSecurely();
            case "Conduets built in < 1/3":
                return electrical.getConduetsBuiltIn13();
            case "Concrete cover":
                return electrical.getConcreteCover();
            case "Fittings securely fixed":
                return electrical.getFittingsSecurelyFixed();
            case "Fittings plumb and square":
                return electrical.getFittingsPlumbAndSquare();
        }
        return null;
    }

    private static InspectionParameter getPlumbingInspectionParameter(String formType, Plumbing plumbing) {
        switch (formType) {
            case "Pipes built in":
                return plumbing.getPipesBuiltIn();
            case "Pipes buily in < 1/3":
                return plumbing.getPipesBuilyIn13();
            case "Pipes cast in < 50%":
                return plumbing.getPipesCastIn50();
            case "Fittings securely fixed":
                return plumbing.getFittingsSecurelyFixed();
            case "Fittings plumb and square":
                return plumbing.getFittingsPlumbAndSquare();

        }
        return null;
    }

    private static InspectionParameter getCarpentryInspectionParameter(String formType, Carpentry carpentry) {
        switch (formType) {
            case "Quality":
                return carpentry.getQuality();
            case "Damage to brickwork":
                return carpentry.getDamageToBrickwork();
            case "Damage to concrete":
                return carpentry.getDamageToConcrete();
        }
        return null;
    }

    private static InspectionParameter getStormWaterInspectionParameter(String formType, StormWater stormWater) {
        switch (formType) {
            case "Water Ponding":
                return stormWater.getWaterPonding();
            case "Slab above NGL":
                return stormWater.getSlabAboveNGL();
            case "Sewer trenches":
                return stormWater.getSewerTrenches();
            case "High banks":
                return stormWater.getHighBanks();
            case "Boundary walls":
                return stormWater.getBoundaryWalls();
            case "Pools, etc.":
                return stormWater.getPoolsEtc();
            case "Documentation":
                return stormWater.getDocumentation();
        }
        return null;
    }

    private static InspectionParameter getPracticalCompletionInspectionParameter(String formType, PracticalCompletion practicalCompletion) {
        switch (formType) {
            case "Wall Plate":
                return practicalCompletion.getWallPlate();
            case "Timber Qualitity Size":
                return practicalCompletion.getTimberQualititySize();
            case "Purlin, beams rafters":
                return practicalCompletion.getPurlinBeamsRafters();
            case "Roof Pitch":
                return practicalCompletion.getRoofPitch();
            case "Nail plated trusses":
                return practicalCompletion.getNailPlatedTrusses();
            case "Site made trusses":
                return practicalCompletion.getSiteMadeTrusses();
            case "Hangers and brackets":
                return practicalCompletion.getHangersAndBrackets();
            case "Bracing":
                return practicalCompletion.getBracing();
            case "Pole structures(Thatched)":
                return practicalCompletion.getPoleStructuresThatched();
            case "Battens and purlins":
                return practicalCompletion.getBattensAndPurlins();
            case "Roof covering":
                return practicalCompletion.getRoofCovering();
            case "Under tile membranes":
                return practicalCompletion.getUnderTileMembranes();
            case "Valley lining":
                return practicalCompletion.getValleyLining();
            case "Beam filling":
                return practicalCompletion.getBeamFilling();
            case "Fire Walls":
                return practicalCompletion.getFireWalls();
            case "Brandering":
                return practicalCompletion.getBrandering();
            case "Flashings":
                return practicalCompletion.getFlashings();
            case "Geyser installation":
                return practicalCompletion.getGeyserInstallation();
            case "Concrete roofs":
                return practicalCompletion.getConcreteRoofs();
            case "Metal lath":
                return practicalCompletion.getMetalLath();
            case "Plaster mix":
                return practicalCompletion.getPlasterMix();
            case "Weep holes":
                return practicalCompletion.getWeepHoles();
            case "Glazing":
                return practicalCompletion.getGlazing();
            case "Documentation":
                return practicalCompletion.getDocumentation();

        }
        return null;
    }

    private static InspectionParameter getSuperStructureInspectionParameter(String formType, SuperStructure superStructure) {
        switch (formType) {
            case "DPC":
                return superStructure.getdPC();
            case "Cavity Walls":
                return superStructure.getCavityWalls();
            case "Masonry":
                return superStructure.getMasonry();
            case "Mortar":
                return superStructure.getMortar();
            case "RC concrete":
                return superStructure.getrCConcrete();
            case "Alignment of corners":
                return superStructure.getAlignmentOfCorners();
            case "Masonry panels":
                return superStructure.getMasonryPanels();
            case "Intersection of walls":
                return superStructure.getIntersectionOfWalls();
            case "Building in of frames":
                return superStructure.getBuildingInOfFrames();
            case "Chasing":
                return superStructure.getChasing();
            case "Brick force W/ties":
                return superStructure.getBrickForceWTies();
            case "Control Joints":
                return superStructure.getControlJoints();
            case "Staircases":
                return superStructure.getStaircases();
            case "Circular masonry":
                return superStructure.getCircularMasonry();
            case "Lintel design and bearing":
                return superStructure.getLintelDesignAndBearing();
            case "Suspended Floors":
                return superStructure.getSuspendedFloors();
            case "Joints in slabs":
                return superStructure.getJointsInSlabs();
            case "Roof anchors":
                return superStructure.getRoofAnchors();
            case "Non-standard system":
                return superStructure.getNonStandardSystem();
            case "Timber construction":
                return superStructure.getTimberConstruction();
            case "Documentation":
                return superStructure.getDocumentation();

        }
        return null;
    }

    private static InspectionParameter getSubStructureInspectionParameter(String formType, SubStructure subStructure) {
        switch (formType) {
            case "Soil Classification":
                return subStructure.getSoilClassification();
            case "Dolomite areas":
                return subStructure.getDolomiteAreas();
            case "Site clearance":
                return subStructure.getSiteClearance();
            case "Water logged site":
                return subStructure.getWaterLoggedSite();
            case "Excavations":
                return subStructure.getExcavations();
            case "Raft slabs":
                return subStructure.getRaftSlabs();
            case "Reinforcement":
                return subStructure.getReinforcement();
            case "Concrete":
                return subStructure.getConcrete();
            case "Masonry":
                return subStructure.getMasonry();
            case "Infill of masonry":
                return subStructure.getInfillOfMasonry();
            case "Brickforce W/Ties":
                return subStructure.getBrickforceWTies();
            case "Filling":
                return subStructure.getFilling();
            case "Surface water disposal":
                return subStructure.getSurfaceWaterDisposal();
            case "Dpm under floors":
                return subStructure.getDpmUnderFloors();
            case "Fabric reinforcement":
                return subStructure.getFabricReinforcement();
            case "Concrete surface beds":
                return subStructure.getConcreteSurfaceBeds();
            case "Construction joints":
                return subStructure.getConstructionJoints();
            case "Basement split level":
                return subStructure.getBasementSplitLevel();
            case "Suspended timber floors":
                return subStructure.getSuspendedTimberFloors();
            case "Documentation":
                return subStructure.getDocumentation();
        }
        return null;
    }
}
