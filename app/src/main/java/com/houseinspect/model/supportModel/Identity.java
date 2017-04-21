package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 11/7/2016.
 */
public class Identity {

    @SerializedName("identity_type")
    @Expose
    private String identityType;
    @SerializedName("identity_number")
    @Expose
    private String identityNumber;

    /**
     *
     * @return
     * The identityType
     */
    public String getIdentityType() {
        return identityType;
    }

    /**
     *
     * @param identityType
     * The identity_type
     */
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    /**
     *
     * @return
     * The identityNumber
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     *
     * @param identityNumber
     * The identity_number
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

}