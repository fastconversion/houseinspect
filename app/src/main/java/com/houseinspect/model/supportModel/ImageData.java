package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.CommentUser;

/**
 * Created by Lalit on 11/28/2016.
 */
public class ImageData {

    @SerializedName("base64")
    @Expose
    private String base64;
    @SerializedName("mobile_image_id")
    @Expose
    private String mobileImageId;
    @SerializedName("server_image_url")
    @Expose
    private String serverImageUrl;
    @SerializedName("submitted_by")
    @Expose
    private CommentUser submittedBy;


    public CommentUser getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(CommentUser submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     *
     * @return
     * The base64
     */
    public String getBase64() {
        return base64;
    }

    /**
     *
     * @param base64
     * The base64
     */
    public void setBase64(String base64) {
        this.base64 = base64;
    }

    /**
     *
     * @return
     * The mobileImageId
     */
    public String getMobileImageId() {
        return mobileImageId;
    }

    /**
     *
     * @param mobileImageId
     * The mobile_image_id
     */
    public void setMobileImageId(String mobileImageId) {
        this.mobileImageId = mobileImageId;
    }

    /**
     *
     * @return
     * The serverImageUrl
     */
    public String getServerImageUrl() {
        return serverImageUrl;
    }

    /**
     *
     * @param serverImageUrl
     * The server_image_url
     */
    public void setServerImageUrl(String serverImageUrl) {
        this.serverImageUrl = serverImageUrl;
    }

}