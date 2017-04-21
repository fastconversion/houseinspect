package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 11/18/2016.
 */
public class UserAccessData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("user_role")
    @Expose
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     *
     * @param companyId
     * The company_id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}