package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 11/7/2016.
 */
public class Company {

    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;

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

    /**
     *
     * @return
     * The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     * The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}