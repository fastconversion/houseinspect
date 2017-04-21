package com.houseinspect.activity.subItemActivity.formActivity.form;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.houseinspect.HouseInspectApplication;
import com.houseinspect.activity.controller.HouseDataFileController;
import com.houseinspect.R;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.activity.mainactivity.MainActivity;
import com.houseinspect.activity.subItemActivity.formActivity.form.base.FormBaseActivity;
import com.houseinspect.activity.controller.OldDataAvailabilityCheck;
import com.houseinspect.model.FormModel.Carpentry;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.FormModel.Electrical;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.ServerImageResponse;
import com.houseinspect.model.SubmitHouseResult;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.FormModel.Plumbing;
import com.houseinspect.model.FormModel.PracticalCompletion;
import com.houseinspect.model.FormModel.StormWater;
import com.houseinspect.model.FormModel.SubStructure;
import com.houseinspect.model.FormModel.SuperStructure;
import com.houseinspect.model.FormModel.WaterProofing;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;

import java.util.List;

import retrofit.RetrofitError;

public class InspectionFormActivity extends FormBaseActivity
        implements FormBaseActivity.OnInspectionSubmit {

    String[] nonSubStructure;

    SubStructure subStructure;
    SuperStructure superStructure;
    PracticalCompletion practicalCompletion;
    StormWater stormWater;
    Carpentry carpentry;
    Plumbing plumbing;
    Electrical electrical;
    WaterProofing waterProofing;

    private String formType;
    private String mainFormName;

    HouseDataModel nonHouseDataModel;
    private String houseType;
    private String houseKey;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nonSubStructure = getResources().getStringArray(R.array.non_sub_structure);
        formType = getIntent().getStringExtra(Constants.FORM_NAME).trim();
        mainFormName = getIntent().getStringExtra(Constants.MAIN_FORM_NAME).trim();
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        houseKey = getIntent().getStringExtra(Constants.HOUSE_KEY);

        super.setTitle(formType);
        super.setOnInspectionComplete(this);
        nonHouseDataModel = HouseInspectApplication.getmInstance().getNonHouseDataModel();
        InspectionParameter inspectionParameter
                = OldDataAvailabilityCheck.getInspectionParameter(nonHouseDataModel, mainFormName, formType);
        if (inspectionParameter != null) {
            super.setInspectionParameter(inspectionParameter);
        }
        initializeObject(mainFormName);
    }

    private void initializeObject(String mainFormName) {
        switch (mainFormName) {
            case "Sub-structure/Foundation":
                if (nonHouseDataModel.getSubStructure() == null) {
                    subStructure = new SubStructure();
                } else {
                    subStructure = nonHouseDataModel.getSubStructure();
                }
                break;
            case "Super-structure":
                if (nonHouseDataModel.getSuperStructure() == null) {
                    superStructure = new SuperStructure();
                } else {
                    superStructure = nonHouseDataModel.getSuperStructure();
                }
                break;
            case "Practical Completion":
                if (nonHouseDataModel.getPracticalCompletion() == null) {
                    practicalCompletion = new PracticalCompletion();
                } else {
                    practicalCompletion = nonHouseDataModel.getPracticalCompletion();
                }
                break;
            case "Storm Water":
                if (nonHouseDataModel.getStormWater() == null) {
                    stormWater = new StormWater();
                } else {
                    stormWater = nonHouseDataModel.getStormWater();
                }
                break;
            case "Carpentry":
                if (nonHouseDataModel.getCarpentry() == null) {
                    carpentry = new Carpentry();
                } else {
                    carpentry = nonHouseDataModel.getCarpentry();
                }
                break;
            case "Plumbing":
                if (nonHouseDataModel.getPlumbing() == null) {
                    plumbing = new Plumbing();
                } else {
                    plumbing = nonHouseDataModel.getPlumbing();
                }
                break;
            case "Electrical":
                if (nonHouseDataModel.getElectrical() == null) {
                    electrical = new Electrical();
                } else {
                    electrical = nonHouseDataModel.getElectrical();
                }
                break;
            case "Water Proofing":
                if (nonHouseDataModel.getWaterProofing() == null) {
                    waterProofing = new WaterProofing();
                } else {
                    waterProofing = nonHouseDataModel.getWaterProofing();
                }
                break;
        }
    }

    @Override
    public void onInspectionComplete(InspectionParameter inspectionParameter) {
        inspectionParameter.setInspectionUpdatedOn("" + (System.currentTimeMillis() / 1000L));
        switch (mainFormName) {
            case "Sub-structure/Foundation":
                setSubStructure(inspectionParameter);
                nonHouseDataModel.setSubStructure(subStructure);
                break;
            case "Super-structure":
                setSuperStructure(inspectionParameter);
                nonHouseDataModel.setSuperStructure(superStructure);
                break;
            case "Practical Completion":
                setPracticalCompletion(inspectionParameter);
                nonHouseDataModel.setPracticalCompletion(practicalCompletion);
                break;
            case "Storm Water":
                setStormWater(inspectionParameter);
                nonHouseDataModel.setStormWater(stormWater);
                break;
            case "Carpentry":
                setCarpentry(inspectionParameter);
                nonHouseDataModel.setCarpentry(carpentry);
                break;
            case "Plumbing":
                setPlumbing(inspectionParameter);
                nonHouseDataModel.setPlumbing(plumbing);
                break;
            case "Electrical":
                setElectrical(inspectionParameter);
                nonHouseDataModel.setElectrical(electrical);
                break;
            case "Water Proofing":
                setWaterProofing(inspectionParameter);
                nonHouseDataModel.setWaterProofing(waterProofing);
                break;
        }
        nonHouseDataModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
        (new HouseDataFileController(this, HouseInspectApplication.getmInstance().getHomeKey()))
                .updateNonHouseModel(nonHouseDataModel);
        HouseEnrolledController houseEnrolledController;
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            houseEnrolledController = new HouseEnrolledController(this, true);
        } else {
            houseEnrolledController = new HouseEnrolledController(this);
        }
        houseEnrolledController.updateHouseKeyModel(houseKey);
        uploadFile(houseKey);
    }

    private void setSubStructure(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Soil Classification":
                subStructure.setSoilClassification(inspectionParameter);
                break;
            case "Dolomite areas":
                subStructure.setDolomiteAreas(inspectionParameter);
                break;
            case "Site clearance":
                subStructure.setSiteClearance(inspectionParameter);
                break;
            case "Water logged site":
                subStructure.setWaterLoggedSite(inspectionParameter);
                break;
            case "Excavations":
                subStructure.setExcavations(inspectionParameter);
                break;
            case "Raft slabs":
                subStructure.setRaftSlabs(inspectionParameter);
                break;
            case "Reinforcement":
                subStructure.setReinforcement(inspectionParameter);
                break;
            case "Concrete":
                subStructure.setConcrete(inspectionParameter);
                break;
            case "Masonry":
                subStructure.setMasonry(inspectionParameter);
                break;
            case "Infill of masonry":
                subStructure.setInfillOfMasonry(inspectionParameter);
                break;
            case "Brickforce W/Ties":
                subStructure.setBrickforceWTies(inspectionParameter);
                break;
            case "Filling":
                subStructure.setFilling(inspectionParameter);
                break;
            case "Surface water disposal":
                subStructure.setSurfaceWaterDisposal(inspectionParameter);
                break;
            case "Dpm under floors":
                subStructure.setDpmUnderFloors(inspectionParameter);
                break;
            case "Fabric reinforcement":
                subStructure.setFabricReinforcement(inspectionParameter);
                break;
            case "Concrete surface beds":
                subStructure.setConcreteSurfaceBeds(inspectionParameter);
                break;
            case "Construction joints":
                subStructure.setConstructionJoints(inspectionParameter);
                break;
            case "Basement split level":
                subStructure.setBasementSplitLevel(inspectionParameter);
                break;
            case "Suspended timber floors":
                subStructure.setSuspendedTimberFloors(inspectionParameter);
                break;
            case "Documentation":
                subStructure.setDocumentation(inspectionParameter);
                break;
        }
        subStructure.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setSuperStructure(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "DPC":
                superStructure.setdPC(inspectionParameter);
                break;
            case "Cavity Walls":
                superStructure.setCavityWalls(inspectionParameter);
                break;
            case "Masonry":
                superStructure.setMasonry(inspectionParameter);
                break;
            case "Mortar":
                superStructure.setMortar(inspectionParameter);
                break;
            case "RC concrete":
                superStructure.setrCConcrete(inspectionParameter);
                break;
            case "Alignment of corners":
                superStructure.setAlignmentOfCorners(inspectionParameter);
                break;
            case "Masonry panels":
                superStructure.setMasonryPanels(inspectionParameter);
                break;
            case "Intersection of walls":
                superStructure.setIntersectionOfWalls(inspectionParameter);
                break;
            case "Building in of frames":
                superStructure.setBuildingInOfFrames(inspectionParameter);
                break;
            case "Chasing":
                superStructure.setChasing(inspectionParameter);
                break;
            case "Brick force W/ties":
                superStructure.setBrickForceWTies(inspectionParameter);
                break;
            case "Control Joints":
                superStructure.setControlJoints(inspectionParameter);
                break;
            case "Staircases":
                superStructure.setStaircases(inspectionParameter);
                break;
            case "Circular masonry":
                superStructure.setCircularMasonry(inspectionParameter);
                break;
            case "Lintel design and bearing":
                superStructure.setLintelDesignAndBearing(inspectionParameter);
                break;
            case "Suspended Floors":
                superStructure.setSuspendedFloors(inspectionParameter);
                break;
            case "Joints in slabs":
                superStructure.setJointsInSlabs(inspectionParameter);
                break;
            case "Roof anchors":
                superStructure.setRoofAnchors(inspectionParameter);
                break;
            case "Non-standard system":
                superStructure.setNonStandardSystem(inspectionParameter);
                break;
            case "Timber construction":
                superStructure.setTimberConstruction(inspectionParameter);
                break;
            case "Documentation":
                superStructure.setDocumentation(inspectionParameter);
                break;
        }
        superStructure.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setPracticalCompletion(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Wall Plate":
                practicalCompletion.setWallPlate(inspectionParameter);
                break;
            case "Timber Qualitity Size":
                practicalCompletion.setTimberQualititySize(inspectionParameter);
                break;
            case "Purlin, beams rafters":
                practicalCompletion.setPurlinBeamsRafters(inspectionParameter);
                break;
            case "Roof Pitch":
                practicalCompletion.setRoofPitch(inspectionParameter);
                break;
            case "Nail plated trusses":
                practicalCompletion.setNailPlatedTrusses(inspectionParameter);
                break;
            case "Site made trusses":
                practicalCompletion.setSiteMadeTrusses(inspectionParameter);
                break;
            case "Hangers and brackets":
                practicalCompletion.setHangersAndBrackets(inspectionParameter);
                break;
            case "Bracing":
                practicalCompletion.setBracing(inspectionParameter);
                break;
            case "Pole structures(Thatched)":
                practicalCompletion.setPoleStructuresThatched(inspectionParameter);
                break;
            case "Battens and purlins":
                practicalCompletion.setBattensAndPurlins(inspectionParameter);
                break;
            case "Roof covering":
                practicalCompletion.setRoofCovering(inspectionParameter);
                break;
            case "Under tile membranes":
                practicalCompletion.setUnderTileMembranes(inspectionParameter);
                break;
            case "Valley lining":
                practicalCompletion.setValleyLining(inspectionParameter);
                break;
            case "Beam filling":
                practicalCompletion.setBeamFilling(inspectionParameter);
                break;
            case "Fire Walls":
                practicalCompletion.setFireWalls(inspectionParameter);
                break;
            case "Brandering":
                practicalCompletion.setBrandering(inspectionParameter);
                break;
            case "Flashings":
                practicalCompletion.setFlashings(inspectionParameter);
                break;
            case "Geyser installation":
                practicalCompletion.setGeyserInstallation(inspectionParameter);
                break;
            case "Concrete roofs":
                practicalCompletion.setConcreteRoofs(inspectionParameter);
                break;
            case "Metal lath":
                practicalCompletion.setMetalLath(inspectionParameter);
                break;
            case "Plaster mix":
                practicalCompletion.setPlasterMix(inspectionParameter);
                break;
            case "Weep holes":
                practicalCompletion.setWeepHoles(inspectionParameter);
                break;
            case "Glazing":
                practicalCompletion.setGlazing(inspectionParameter);
                break;
            case "Documentation":
                practicalCompletion.setDocumentation(inspectionParameter);
                break;

        }
        practicalCompletion.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setStormWater(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Water Ponding":
                stormWater.setWaterPonding(inspectionParameter);
                break;
            case "Slab above NGL":
                stormWater.setSlabAboveNGL(inspectionParameter);
                break;
            case "Sewer trenches":
                stormWater.setSewerTrenches(inspectionParameter);
                break;
            case "High banks":
                stormWater.setHighBanks(inspectionParameter);
                break;
            case "Boundary walls":
                stormWater.setBoundaryWalls(inspectionParameter);
                break;
            case "Pools, etc.":
                stormWater.setPoolsEtc(inspectionParameter);
                break;
            case "Documentation":
                stormWater.setDocumentation(inspectionParameter);
                break;
        }
        stormWater.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setCarpentry(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Quality":
                carpentry.setQuality(inspectionParameter);
                break;
            case "Damage to brickwork":
                carpentry.setDamageToBrickwork(inspectionParameter);
                break;
            case "Damage to concrete":
                carpentry.setDamageToConcrete(inspectionParameter);
                break;

        }
        carpentry.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setPlumbing(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Pipes built in":
                plumbing.setPipesBuiltIn(inspectionParameter);
                break;
            case "Pipes buily in < 1/3":
                plumbing.setPipesBuilyIn13(inspectionParameter);
                break;
            case "Pipes cast in < 50%":
                plumbing.setPipesCastIn50(inspectionParameter);
                break;
            case "Fittings securely fixed":
                plumbing.setFittingsSecurelyFixed(inspectionParameter);
                break;
            case "Fittings plumb and square":
                plumbing.setFittingsPlumbAndSquare(inspectionParameter);
                break;

        }
        plumbing.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setElectrical(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Conduets built in securely":
                electrical.setConduetsBuiltInSecurely(inspectionParameter);
                break;
            case "Conduets built in < 1/3":
                electrical.setConduetsBuiltIn13(inspectionParameter);
                break;
            case "Concrete cover":
                electrical.setConcreteCover(inspectionParameter);
                break;
            case "Fittings securely fixed":
                electrical.setFittingsSecurelyFixed(inspectionParameter);
                break;
            case "Fittings plumb and square":
                electrical.setFittingsPlumbAndSquare(inspectionParameter);
                break;
        }
        electrical.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void setWaterProofing(InspectionParameter inspectionParameter) {
        switch (formType) {
            case "Fit for purpose":
                waterProofing.setFitForPurpose(inspectionParameter);
                break;
            case "Full-bores":
                waterProofing.setFullBores(inspectionParameter);
                break;

        }
        waterProofing.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
    }

    private void uploadFile(final String houseKey) {
        showProgressDialog("Please wait");
        String houseData = (new Gson()).toJson(nonHouseDataModel);
        HouseDataModel houseDataModelNew = (new Gson()).fromJson(houseData, HouseDataModel.class);
        houseDataModelNew.removeServerUrlBase64();
        houseDataModelNew.setUserAccessData(Constants.getUserAccessData(this));
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            houseDataModelNew.setHouseType("subsidy");
        } else {
            houseDataModelNew.setHouseType("nonsubsidy");
        }
        String uid = "0";
        if (houseDataModelNew.getUid() != null) {
            uid = houseDataModelNew.getUid();
        }
        Long startTime =  houseDataModelNew.getStartTime();
        houseDataModelNew.setStartTime(null);
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.uploadInspectionData((new Gson()).toJson(houseDataModelNew), uid, null,startTime,
                null,new Callback<SubmitHouseResult>() {
            @Override
            public void success(Result<SubmitHouseResult> result) {
                updateDataModels(result.data, houseKey, nonHouseDataModel);
                hideProgressDialog();
                InspectionFormActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                 hideProgressDialog();
                if (code == 401) {
                    Toast.makeText(InspectionFormActivity.this, "You are no longer assign to this house",
                            Toast.LENGTH_SHORT).show();
                    HouseEnrolledController.removeFromList(InspectionFormActivity.this, houseType, houseKey);
                    Intent intent = new Intent(InspectionFormActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    InspectionFormActivity.this.finish();
                }
                InspectionFormActivity.this.finish();
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                hideProgressDialog();
                InspectionFormActivity.this.finish();
            }
        });
    }

    private void updateDataModels(SubmitHouseResult submitHouseResult, String houseKey, HouseDataModel houseDataModel) {
        updateImageUrls(submitHouseResult.getServerImageResponses(), houseDataModel);
        houseDataModel.setUid("" + submitHouseResult.getUid());
        HouseInspectApplication.getmInstance().getNonHouseDataModel().setUid("" + submitHouseResult.getUid());
        DemographicDetails demographicDetails = houseDataModel.getDemographicDetails();
        (new HouseDataFileController(InspectionFormActivity.this, houseKey)).updateNonHouseModel(houseDataModel);
        HouseKeyDataModel nonSubHomeListModel = new HouseKeyDataModel();
        nonSubHomeListModel.setUniqueKey(houseDataModel.getUid());
        nonSubHomeListModel.setDemographicDetails(demographicDetails);
        nonSubHomeListModel.setMobileKey(houseKey);
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            (new HouseEnrolledController(InspectionFormActivity.this, true)).updateHouseKey(houseKey, nonSubHomeListModel);
        } else {
            (new HouseEnrolledController(InspectionFormActivity.this)).updateHouseKey(houseKey, nonSubHomeListModel);
        }
    }

    private void updateImageUrls(List<ServerImageResponse> serverImageResponses, HouseDataModel houseDataModel) {
        if (houseDataModel.getDemographicDetails() != null)
            houseDataModel.getDemographicDetails().setServerUrl(serverImageResponses);

        if (houseDataModel.getSubStructure() != null)
            houseDataModel.getSubStructure().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getSuperStructure() != null)
            houseDataModel.getSuperStructure().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getPracticalCompletion() != null)
            houseDataModel.getPracticalCompletion().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getStormWater() != null)
            houseDataModel.getStormWater().setAllInspectionServerUrl(serverImageResponses);

        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE))
            return;

        if (houseDataModel.getCarpentry() != null)
            houseDataModel.getCarpentry().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getPlumbing() != null)
            houseDataModel.getPlumbing().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getElectrical() != null)
            houseDataModel.getElectrical().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getWaterProofing() != null)
            houseDataModel.getWaterProofing().setAllInspectionServerUrl(serverImageResponses);
    }

    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        if (progressDialog.isShowing())
            progressDialog.cancel();
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

}
