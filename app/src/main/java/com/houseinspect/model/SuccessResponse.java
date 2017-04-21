package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 11/8/2016.
 */
public class SuccessResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("uid")
    @Expose
    private Integer uid;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

}