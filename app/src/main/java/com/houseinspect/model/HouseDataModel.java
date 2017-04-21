package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.FormModel.Carpentry;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.FormModel.Electrical;
import com.houseinspect.model.FormModel.HomeOwnerInfo;
import com.houseinspect.model.FormModel.Plumbing;
import com.houseinspect.model.FormModel.PracticalCompletion;
import com.houseinspect.model.FormModel.StormWater;
import com.houseinspect.model.FormModel.SubStructure;
import com.houseinspect.model.FormModel.SuperStructure;
import com.houseinspect.model.FormModel.WaterProofing;
import com.houseinspect.model.supportModel.UserAccessData;

/**
 * Created by Lalit on 11/14/2016.
 */
public class HouseDataModel {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("Demographic Details")
    @Expose
    private DemographicDetails demographicDetails;
    @SerializedName("House Owner Detail")
    @Expose
    private HomeOwnerInfo homeOwnerInfo;
    @SerializedName("Sub-structure/Foundation")
    @Expose
    private SubStructure subStructure;
    @SerializedName("Super-structure")
    @Expose
    private SuperStructure superStructure;
    @SerializedName("Practical Completion")
    @Expose
    private PracticalCompletion practicalCompletion;
    @SerializedName("Storm Water")
    @Expose
    private StormWater stormWater;
    @SerializedName("Carpentry")
    @Expose
    private Carpentry carpentry;
    @SerializedName("Plumbing")
    @Expose
    private Plumbing plumbing;
    @SerializedName("Electrical")
    @Expose
    private Electrical electrical;
    @SerializedName("Water Proofing")
    @Expose
    private WaterProofing waterProofing;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("user")
    @Expose
    private UserAccessData userAccessData;
    @SerializedName("type")
    @Expose
    private String houseType;
    @SerializedName("inspector_completion_date")
    @Expose
    private String inspectorCompletionDate;
    @SerializedName("senior_inspector_completion_date")
    @Expose
    private String seniorInspectorCompletionDate;
    @SerializedName("reinspection_completion_date")
    @Expose
    private String reInspectorCompletionDate;
    @SerializedName("start_time")
    @Expose
    private Long startTime;
    public String getInspectorCompletionDate() {
        return inspectorCompletionDate;
    }

    public void setInspectorCompletionDate(String inspectorCompletionDate) {
        this.inspectorCompletionDate = inspectorCompletionDate;
    }

    public String getSeniorInspectorCompletionDate() {
        return seniorInspectorCompletionDate;
    }

    public void setSeniorInspectorCompletionDate(String seniorInspectorCompletionDate) {
        this.seniorInspectorCompletionDate = seniorInspectorCompletionDate;
    }

    public String getReInspectorCompletionDate() {
        return reInspectorCompletionDate;
    }

    public void setReInspectorCompletionDate(String reInspectorCompletionDate) {
        this.reInspectorCompletionDate = reInspectorCompletionDate;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public HomeOwnerInfo getHomeOwnerInfo() {
        return homeOwnerInfo;
    }

    public void setHomeOwnerInfo(HomeOwnerInfo homeOwnerInfo) {
        this.homeOwnerInfo = homeOwnerInfo;
    }

    public DemographicDetails getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public SubStructure getSubStructure() {
        return subStructure;
    }

    public void setSubStructure(SubStructure subStructure) {
        this.subStructure = subStructure;
    }

    public SuperStructure getSuperStructure() {
        return superStructure;
    }

    public void setSuperStructure(SuperStructure superStructure) {
        this.superStructure = superStructure;
    }

    public PracticalCompletion getPracticalCompletion() {
        return practicalCompletion;
    }

    public void setPracticalCompletion(PracticalCompletion practicalCompletion) {
        this.practicalCompletion = practicalCompletion;
    }

    public StormWater getStormWater() {
        return stormWater;
    }

    public void setStormWater(StormWater stormWater) {
        this.stormWater = stormWater;
    }

    public Carpentry getCarpentry() {
        return carpentry;
    }

    public void setCarpentry(Carpentry carpentry) {
        this.carpentry = carpentry;
    }

    public Plumbing getPlumbing() {
        return plumbing;
    }

    public void setPlumbing(Plumbing plumbing) {
        this.plumbing = plumbing;
    }

    public Electrical getElectrical() {
        return electrical;
    }

    public void setElectrical(Electrical electrical) {
        this.electrical = electrical;
    }

    public WaterProofing getWaterProofing() {
        return waterProofing;
    }

    public void setWaterProofing(WaterProofing waterProofing) {
        this.waterProofing = waterProofing;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public UserAccessData getUserAccessData() {
        return userAccessData;
    }

    public void setUserAccessData(UserAccessData userAccessData) {
        this.userAccessData = userAccessData;
    }

    public void removeServerUrlBase64() {
        if (demographicDetails != null) {
            demographicDetails.removeBase64();
        }
        if (subStructure != null) {
            subStructure.removeBase64();
        }
        if (superStructure != null) {
            superStructure.removeBase64();
        }
        if (practicalCompletion != null) {
            practicalCompletion.removeBase64();
        }
        if (stormWater != null) {
            stormWater.removeBase64();
        }
        if (carpentry != null) {
            carpentry.removeBase64();
        }
        if (plumbing != null) {
            plumbing.removeBase64();
        }
        if (electrical != null) {
            electrical.removeBase64();
        }
        if (waterProofing != null) {
            waterProofing.removeBase64();
        }
    }

}
