package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 12/13/2016.
 */
public class ReAssigned {

    @SerializedName("Subsidy")
    @Expose
    private List<HouseKeyDataModel> subsidyKeyDataModels = new ArrayList<>();
    @SerializedName("Non-Subsidy")
    @Expose
    private List<HouseKeyDataModel> nonSubsidyKeyDataModels = new ArrayList<>();

    public List<HouseKeyDataModel> getSubsidyKeyDataModels() {
        return subsidyKeyDataModels;
    }

    public void setSubsidyKeyDataModels(List<HouseKeyDataModel> subsidyKeyDataModels) {
        this.subsidyKeyDataModels = subsidyKeyDataModels;
    }

    public List<HouseKeyDataModel> getNonSubsidyKeyDataModels() {
        return nonSubsidyKeyDataModels;
    }

    public void setNonSubsidyKeyDataModels(List<HouseKeyDataModel> nonSubsidyKeyDataModels) {
        this.nonSubsidyKeyDataModels = nonSubsidyKeyDataModels;
    }
}