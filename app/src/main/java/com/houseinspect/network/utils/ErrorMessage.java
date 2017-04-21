package com.houseinspect.network.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by King on 28/01/16.
 */
public class ErrorMessage {

    @SerializedName("error")
    @Expose
    private Error error;

    /**
     *
     * @return
     * The error
     */
    public Error getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Error error) {
        this.error = error;
    }

}