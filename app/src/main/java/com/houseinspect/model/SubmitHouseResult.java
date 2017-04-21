package com.houseinspect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 11/23/2016.
 */
public class SubmitHouseResult {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("server_all_image_url")
    @Expose
    private List<ServerImageResponse> serverImageResponses =  new ArrayList<>();


    public List<ServerImageResponse> getServerImageResponses() {
        return serverImageResponses;
    }

    public void setServerImageResponses(List<ServerImageResponse> serverImageResponses) {
        this.serverImageResponses = serverImageResponses;
    }

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