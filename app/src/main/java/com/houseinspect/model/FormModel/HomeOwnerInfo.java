package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.FamilyMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 11/14/2016.
 */
public class HomeOwnerInfo {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("family_income")
    @Expose
    private String familyIncome;
    @SerializedName("living_people_number")
    @Expose
    private String livingPeopleNumber;
    @SerializedName("living_people")
    @Expose
    private List<FamilyMember> livingPeople = new ArrayList<FamilyMember>();
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }


    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     *
     * @param idNumber
     * The id_number
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     *
     * @param nationality
     * The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     *
     * @return
     * The race
     */
    public String getRace() {
        return race;
    }

    /**
     *
     * @param race
     * The race
     */
    public void setRace(String race) {
        this.race = race;
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
     * The familyIncome
     */
    public String getFamilyIncome() {
        return familyIncome;
    }

    /**
     *
     * @param familyIncome
     * The family_income
     */
    public void setFamilyIncome(String familyIncome) {
        this.familyIncome = familyIncome;
    }

    /**
     *
     * @return
     * The livingPeopleNumber
     */
    public String getLivingPeopleNumber() {
        return livingPeopleNumber;
    }

    /**
     *
     * @param livingPeopleNumber
     * The living_people_number
     */
    public void setLivingPeopleNumber(String livingPeopleNumber) {
        this.livingPeopleNumber = livingPeopleNumber;
    }

    public List<FamilyMember> getLivingPeople() {
        return livingPeople;
    }

    public void setLivingPeople(List<FamilyMember> livingPeople) {
        this.livingPeople = livingPeople;
    }


}