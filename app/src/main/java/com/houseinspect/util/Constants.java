package com.houseinspect.util;

import android.content.Context;
import android.content.Intent;

import com.houseinspect.activity.controller.DataController;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.supportModel.UserAccessData;

/**
 * Created by Lalit on 11/8/2016.
 */
public class Constants {
    //http://fcdemoserver.com/php/houseinspect/
    //http://houselab.co.za/houseinspect/
    public static final String BASE_URL = "http://houselab.co.za/houseinspect/";
    public static final String API_URL = BASE_URL+"HouseInspectApi/";
    public static final String APP_TAG = "HOUSE_INSPECT";

    public static final String FORM_NAME = "com.hins.formname";
    public static final String SUB_FORMS = "com.hins.subitem";
    public static final String MAIN_FORM_NAME = "Mainform";
    public static final String NON_SUB_HOUSE_KEY = "non_sub_house_key";
    public static final String HOUSE_TYPE = "house_type";

    public static final String NON_SUBSIDY_TYPE = "non_sub_type";
    public static final String SUBSIDY_TYPE = "sub_type";
    public static final String HOUSE_KEY = "house_key";
    public static final String IS_REASSIGN = "is_reassigned";
    public static final String REGISTRATION_COMPLETE = "fcm_data_key";
    public static final String FCM_SHARED_PREF = "fcm_key_file";
    public static final String COMPANY_LIST = "com.hins.companyList";
    public static final String COMPANY = "com.hins.company";

    public static String getNonSubHouseKey(DemographicDetails demographicDetails){
        String newKey = demographicDetails.getProvince().replaceAll(" ","_")+"_"+demographicDetails.getTownship()+"_"+
                demographicDetails.getExtension()+"_"+demographicDetails.getWard()+"_"+demographicDetails.getStreetName()
                +"_"+demographicDetails.getStandName()+"_"+demographicDetails.getHouseNumber();
        return newKey;
    }

    public static String getSubHouseKey(DemographicDetails demographicDetails){
        String newKey = "subsidy_"+demographicDetails.getProvince().replaceAll(" ","_")+"_"+demographicDetails.getTownship()+"_"+
                demographicDetails.getExtension()+"_"+demographicDetails.getWard()+"_"+demographicDetails.getStreetName()
                +"_"+demographicDetails.getStandName()+"_"+demographicDetails.getHouseNumber();
        return newKey;
    }

    public static UserAccessData getUserAccessData(Context context) {
        RegisterData registerData = (new DataController(context)).getUserData();
        UserAccessData userAccessData = new UserAccessData();
        userAccessData.setUserId(registerData.getUserId());
        userAccessData.setUserRole(registerData.getRole());
        userAccessData.setCompanyId(registerData.getCompany().getCompanyId());
        return userAccessData;
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
