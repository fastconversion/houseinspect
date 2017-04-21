package com.houseinspect.model.supportModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 11/9/2016.
 */
public class FamilyMember {

    @SerializedName("member_name")
    @Expose
    private String memberName;
    @SerializedName("member_age")
    @Expose
    private String memberAge;


    /**
     *
     * @return
     * The memberName
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     *
     * @param memberName
     * The member_name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     *
     * @return
     * The memberAge
     */
    public String getMemberAge() {
        return memberAge;
    }

    /**
     *
     * @param memberAge
     * The member_age
     */
    public void setMemberAge(String memberAge) {
        this.memberAge = memberAge;
    }

}