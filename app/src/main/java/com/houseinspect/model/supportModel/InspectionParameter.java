package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.FormModel.InspectionComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 11/12/2016.
 */
public class InspectionParameter {

    @SerializedName("inspection_value")
    @Expose
    private String inspectionValue;
    @SerializedName("inspection_measurement")
    @Expose
    private String inspectionMeasurement;
    @SerializedName("inspection_comment")
    @Expose
    private List<InspectionComment> inspectionCommentList = new ArrayList<>();
    @SerializedName("inspection_updated_on")
    @Expose
    private String inspectionUpdatedOn;
    @SerializedName("inspection_images")
    @Expose
    private List<ImageData> inspectionImages = new ArrayList<ImageData>();
    @SerializedName("inspection_delete_images")
    @Expose
    private List<ImageData> inspectionDeleteImages = new ArrayList<ImageData>();

    public List<ImageData> getInspectionDeleteImages() {
        return inspectionDeleteImages;
    }

    public void setInspectionDeleteImages(List<ImageData> inspectionDeleteImages) {
        this.inspectionDeleteImages = inspectionDeleteImages;
    }

    /**
     * @return The inspectionValue
     */
    public String getInspectionValue() {
        return inspectionValue;
    }

    /**
     * @param inspectionValue The inspection_value
     */
    public void setInspectionValue(String inspectionValue) {
        this.inspectionValue = inspectionValue;
    }

    /**
     * @return The inspectionMeasurement
     */
    public String getInspectionMeasurement() {
        return inspectionMeasurement;
    }

    /**
     * @param inspectionMeasurement The inspection_measurement
     */
    public void setInspectionMeasurement(String inspectionMeasurement) {
        this.inspectionMeasurement = inspectionMeasurement;
    }

    public List<InspectionComment> getInspectionCommentList() {
        return inspectionCommentList;
    }

    public void setInspectionCommentList(List<InspectionComment> inspectionCommentList) {
        this.inspectionCommentList = inspectionCommentList;
    }

    public List<ImageData> getInspectionImages() {
        return inspectionImages;
    }

    public void setInspectionImages(List<ImageData> inspectionImages) {
        this.inspectionImages = inspectionImages;
    }

    public String getInspectionUpdatedOn() {
        return inspectionUpdatedOn;
    }

    public void setInspectionUpdatedOn(String inspectionUpdatedOn) {
        this.inspectionUpdatedOn = inspectionUpdatedOn;
    }
}