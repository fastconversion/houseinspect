package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.ReAssigned;
import com.houseinspect.model.supportModel.Company;
import com.houseinspect.model.supportModel.GpsCoordinate;
import com.houseinspect.model.supportModel.Identity;
import com.houseinspect.model.supportModel.ImageData;

import java.util.ArrayList;

/**
 * Created by Lalit on 11/7/2016.
 */
public class RegisterData{
    @SerializedName("gps_coordinate")
    @Expose
    private GpsCoordinate gpsCoordinate;

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("forename")
    @Expose
    private String forename;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("identity")
    @Expose
    private Identity identity;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("company")
    @Expose
    private Company company;

    @SerializedName("idImagePath")
    @Expose
    private String idImagePath;
    @SerializedName("signaturePath")
    @Expose
    private String signaturepath;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("Non-Subsidy")
    @Expose
    private ArrayList<HouseKeyDataModel> nonSubHomeListModels = new ArrayList<>();

    @SerializedName("Subsidy")
    @Expose
    private ArrayList<HouseKeyDataModel> subHomeListModels = new ArrayList<>();

    @SerializedName("re-assigned")
    @Expose
    private ReAssigned reAssigned;

    @SerializedName("profile_image")
    @Expose
    private ImageData profileImage;


    public GpsCoordinate getGpsCoordinate() {
        return gpsCoordinate;
    }

    public void setGpsCoordinate(GpsCoordinate gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public ImageData getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageData profileImage) {
        this.profileImage = profileImage;
    }

    public ReAssigned getReAssigned() {
        return reAssigned;
    }

    public void setReAssigned(ReAssigned reAssigned) {
        this.reAssigned = reAssigned;
    }

    /**
     *
     * @return
     * The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     * The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     * The forename
     */
    public String getForename() {
        return forename;
    }

    /**
     *
     * @param forename
     * The forename
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     *
     * @return
     * The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The date_of_birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The identity
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     *
     * @param identity
     * The identity
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    /**
     *
     * @return
     * The mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     *
     * @param mobileNumber
     * The mobile_number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The company
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    public String getIdImagePath() {
        return idImagePath;
    }

    public void setIdImagePath(String idImagePath) {
        this.idImagePath = idImagePath;
    }

    public String getSignaturepath() {
        return signaturepath;
    }

    public void setSignaturepath(String signaturepath) {
        this.signaturepath = signaturepath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<HouseKeyDataModel> getNonSubHomeListModels() {
        return nonSubHomeListModels;
    }

    public void setNonSubHomeListModels(ArrayList<HouseKeyDataModel> nonSubHomeListModels) {
        this.nonSubHomeListModels = nonSubHomeListModels;
    }

    public ArrayList<HouseKeyDataModel> getSubHomeListModels() {
        return subHomeListModels;
    }

    public void setSubHomeListModels(ArrayList<HouseKeyDataModel> subHomeListModels) {
        this.subHomeListModels = subHomeListModels;
    }

}