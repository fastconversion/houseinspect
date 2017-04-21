package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 12/7/2016.
 */
public class CommentUser {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_role")
    @Expose
    private String userRole;

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
     * The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     * The userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     *
     * @param userRole
     * The user_role
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}