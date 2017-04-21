package com.houseinspect.activity.mainactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.houseinspect.HouseInspectApplication;
import com.houseinspect.R;
import com.houseinspect.activity.adapter.SubItemMainAdapter;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.controller.HouseDataFileController;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.activity.subItemActivity.formActivity.main.DemographicDetailActivity;
import com.houseinspect.activity.subItemActivity.formActivity.main.HomeOwnerDetailActivity;
import com.houseinspect.activity.subItemActivity.formActivity.main.InspectionCategoryActivity;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.ServerImageResponse;
import com.houseinspect.model.SubmitHouseResult;
import com.houseinspect.model.supportModel.GpsCoordinate;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;
import com.houseinspect.util.Logger;
import com.houseinspect.util.MyConvertor;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class HouseNewEditActivity extends AppCompatActivity
        implements SubItemMainAdapter.ItemClickListener, DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_RESULT_CODE = 101;
    private String[] subsidyTagList;
    private String homeKey;
    private HouseDataModel houseDataModel;
    private SubItemMainAdapter adapter;
    private ProgressDialog progressDialog;
    String houseType;
    RegisterData registerData;
    boolean isReinspect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_new_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerData = (new DataController(this)).getUserData();
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        isReinspect = getIntent().getBooleanExtra(Constants.IS_REASSIGN, false);
        if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
            getSupportActionBar().setTitle("NON SUBSIDY ENROLLMENT");
        } else {
            getSupportActionBar().setTitle("SUBSIDY ENROLLMENT");
        }
        if (getIntent().hasExtra(Constants.NON_SUB_HOUSE_KEY)) {
            homeKey = getIntent().getStringExtra(Constants.NON_SUB_HOUSE_KEY);
            HouseInspectApplication.getmInstance().setNonSubKey(homeKey);
            houseDataModel = (new HouseDataFileController(this, homeKey)).getNonHouseDataModel();
            HouseInspectApplication.getmInstance().setNonHouseDataModel(houseDataModel);
            if(houseDataModel.getStartTime() == null){
                houseDataModel.setStartTime(System.currentTimeMillis()/1000L);
            }
        } else {
            homeKey = null;
            HouseInspectApplication.getmInstance().setNonSubKey(homeKey);
        }
    }

    private void initUi() {
        if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
            subsidyTagList = getResources().getStringArray(R.array.non_subsidy_home);
        } else {
            subsidyTagList = getResources().getStringArray(R.array.subsidy_home);
        }
        if (adapter == null) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new SubItemMainAdapter(this, subsidyTagList, this, houseDataModel);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadFile() {
        showProgressDialog("Please wait");
        String houseData = (new Gson()).toJson(houseDataModel);
        HouseDataModel houseDataModelNew = (new Gson()).fromJson(houseData, HouseDataModel.class);
        houseDataModelNew.removeServerUrlBase64();
        houseDataModelNew.setUserAccessData(Constants.getUserAccessData(this));
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            houseDataModelNew.setHouseType("subsidy");
        } else {
            houseDataModelNew.setHouseType("nonsubsidy");
        }
        String uid = "0";
        if (houseDataModelNew.getUid() != null) {
            uid = houseDataModelNew.getUid();
        }
        Long startTime =  houseDataModelNew.getStartTime();
        houseDataModelNew.setStartTime(null);
        Long endTime =  (System.currentTimeMillis()/1000L);
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        final String loggerData = (new Gson()).toJson(houseDataModelNew)+"\n\nUId:"+uid+"\n\nstartTime: "+startTime+"\n\nendTime"+endTime;
        service.uploadInspectionData((new Gson()).toJson(houseDataModelNew), uid, null,startTime,
                endTime,new Callback<SubmitHouseResult>() {
            @Override
            public void success(Result<SubmitHouseResult> result) {

                Logger.getInstance(HouseNewEditActivity.this).writeData(loggerData + (new Gson()).toJson(result.data));
                updateDataModels(result.data);
                hideProgressDialog();
                HouseNewEditActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                hideProgressDialog();
                if (code == 401) {
                    Toast.makeText(HouseNewEditActivity.this, "You are no longer assign to this house",
                            Toast.LENGTH_SHORT).show();
                    HouseEnrolledController.removeFromList(HouseNewEditActivity.this, houseType, homeKey);
                    HouseNewEditActivity.this.finish();
                }else if(code == 406){
                    String data = (new String(((TypedByteArray)error.getResponse().getBody()).getBytes())).toString();
                    Toast.makeText(HouseNewEditActivity.this, "Duplicate Entry!",
                            Toast.LENGTH_SHORT).show();

                }else
                    Logger.getInstance(HouseNewEditActivity.this).writeData(loggerData + error.getMessage());
                    Toast.makeText(HouseNewEditActivity.this, "Something went wrong! Please Try Again",
                            Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNetworkFail(String errorMessage) {
                hideProgressDialog();
                Toast.makeText(HouseNewEditActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openConfirmationActivity() {
        DemographicDetails demographicDetails = houseDataModel.getDemographicDetails();
        if (demographicDetails.getGpsCoordinateList().size() > 0 && !isGpsCoordinateAttached(demographicDetails.getGpsCoordinateList())) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please Capture GPS location in Demographic", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(HouseNewEditActivity.this, "Please Capture GPS location in Demographic", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, SeniorInspectorConfirmationActivity.class);
        intent.putExtra(SeniorInspectorConfirmationActivity.HOUSE_DATA_MODEL, (new Gson()).toJson(houseDataModel));
        intent.putExtra(SeniorInspectorConfirmationActivity.HOUSE_TYPE, houseType);
        this.startActivityForResult(intent, REQUEST_RESULT_CODE);
    }

    private void updateDataModels(SubmitHouseResult submitHouseResult) {
        updateImageUrls(submitHouseResult.getServerImageResponses());
        houseDataModel.setUid("" + submitHouseResult.getUid());
        DemographicDetails demographicDetails = houseDataModel.getDemographicDetails();
        String houseKey;
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            houseKey = Constants.getSubHouseKey(demographicDetails);
        } else {
            houseKey = Constants.getNonSubHouseKey(demographicDetails);
        }
        (new HouseDataFileController(HouseNewEditActivity.this, houseKey)).updateNonHouseModel(houseDataModel);
        HouseKeyDataModel nonSubHomeListModel = new HouseKeyDataModel();
        nonSubHomeListModel.setUniqueKey(houseDataModel.getUid());
        nonSubHomeListModel.setDemographicDetails(demographicDetails);
        nonSubHomeListModel.setMobileKey(houseKey);
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            (new HouseEnrolledController(HouseNewEditActivity.this, true)).updateHouseKey(houseKey, nonSubHomeListModel);
        } else {
            (new HouseEnrolledController(HouseNewEditActivity.this)).updateHouseKey(houseKey, nonSubHomeListModel);
        }
    }

    private void updateImageUrls(List<ServerImageResponse> serverImageResponses) {
        if (houseDataModel.getDemographicDetails() != null)
            houseDataModel.getDemographicDetails().setServerUrl(serverImageResponses);

        if (houseDataModel.getSubStructure() != null)
            houseDataModel.getSubStructure().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getSuperStructure() != null)
            houseDataModel.getSuperStructure().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getPracticalCompletion() != null)
            houseDataModel.getPracticalCompletion().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getStormWater() != null)
            houseDataModel.getStormWater().setAllInspectionServerUrl(serverImageResponses);

        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE))
            return;

        if (houseDataModel.getCarpentry() != null)
            houseDataModel.getCarpentry().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getPlumbing() != null)
            houseDataModel.getPlumbing().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getElectrical() != null)
            houseDataModel.getElectrical().setAllInspectionServerUrl(serverImageResponses);

        if (houseDataModel.getWaterProofing() != null)
            houseDataModel.getWaterProofing().setAllInspectionServerUrl(serverImageResponses);
    }

    @Override
    public void onAdapterItemClick(int position) {
        Intent intent = null;
        ArrayList<String> subItemList;
        if (position != 0 && homeKey == null) {
            Toast.makeText(this, "Please first fill Demographic Detail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE) && position == 7) {
            //for matching with the list...of nonSubsidy and subsidy...
            position = 11;
        }
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE) && position == 6) {
            //for matching with the list...of nonSubsidy and subsidy...
            position = 10;
        }
        switch (position) {
            case 0:
                intent = new Intent(this, DemographicDetailActivity.class);
                break;
            case 1:
                intent = new Intent(this, HomeOwnerDetailActivity.class);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                break;
            case 2:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_sub_structure));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[2]));
                break;
            case 3:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_super_structure));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[3]));
                break;
            case 4:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources()
                        .getStringArray(R.array.non_practical_completion));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[4]));
                break;
            case 5:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_storm_water));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[5]));
                break;
            case 6:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_carpentry));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[6]));
                break;
            case 7:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_plumbing));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[7]));
                break;
            case 8:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_electrical));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[8]));
                break;
            case 9:
                intent = new Intent(this, InspectionCategoryActivity.class);
                subItemList = MyConvertor.getArrayList(getResources().getStringArray(R.array.non_Water_Proofing));
                intent.putStringArrayListExtra(Constants.SUB_FORMS, subItemList);
                intent.putExtra(Constants.HOUSE_TYPE, houseType);
                intent.putExtra(Constants.HOUSE_KEY, homeKey);
                intent.putExtra(Constants.MAIN_FORM_NAME, MyConvertor.getTitle(subsidyTagList[9]));
                break;
            case 10:
                if (isReinspect) {
                    if (houseDataModel.getReInspectorCompletionDate() != null) {
                        completiontype = COMPLETIONTYPE.REINSPECT;
                        pickDate();
                    } else {
                        completiontype = COMPLETIONTYPE.REINSPECT;
                        pickDate();
                    }
                } else {
                    if (registerData.getRole().equalsIgnoreCase("INSPECTOR")) {
                        if (houseDataModel.getInspectorCompletionDate() != null) {
                            completiontype = COMPLETIONTYPE.INSPECTOR;
                            pickDate();
                        } else {
                            completiontype = COMPLETIONTYPE.INSPECTOR;
                            pickDate();
                        }
                    } else {
                        if (houseDataModel.getSeniorInspectorCompletionDate() != null) {
                            completiontype = COMPLETIONTYPE.SINSPECTOR;
                            pickDate();
                        } else {
                            completiontype = COMPLETIONTYPE.SINSPECTOR;
                            pickDate();
                        }
                    }
                }
                break;
            case 11:
                if (houseDataModel != null && houseDataModel.getDemographicDetails() != null) {
                    verifyKungfu();
                } else {
                    Snackbar.make(findViewById(R.id.mainLayout), "Please fill Demographic Detail", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(HouseNewEditActivity.this, "Please fill Demographic Detail", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        if (intent != null) {
            intent.putExtra(Constants.HOUSE_TYPE, houseType);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }
    }

    private void pickDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                HouseNewEditActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Select Completion Date");
    }

    private void verifyKungfu() {
        if (isReinspect){
            if (houseDataModel.getReInspectorCompletionDate() == null) {
                Snackbar.make(findViewById(R.id.mainLayout), "Please provide practical completion Date", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(HouseNewEditActivity.this, "Please provide practical completion Date", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (houseDataModel == null || houseDataModel.getDemographicDetails() == null) {
            Toast.makeText(this, "Sorry! No Data recorded found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (registerData.getRole().equalsIgnoreCase("SENIOR-INSPECTOR")) {
            if (houseDataModel.getSeniorInspectorCompletionDate() == null) {
                Snackbar.make(findViewById(R.id.mainLayout), "Please provide practical completion Date", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(HouseNewEditActivity.this, "Please provide practical completion Date", Toast.LENGTH_SHORT).show();
                return;
            }
            openConfirmationActivity();
            return;
        }
        uploadFile();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeKey == null) {
            homeKey = HouseInspectApplication.getmInstance().getHomeKey();
            if (homeKey != null) {
                houseDataModel = (new HouseDataFileController(this, homeKey)).getNonHouseDataModel();
                HouseInspectApplication.getmInstance()
                        .setNonHouseDataModel(houseDataModel);
                if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
                    subsidyTagList = getResources().getStringArray(R.array.non_subsidy_home);
                } else {
                    subsidyTagList = getResources().getStringArray(R.array.subsidy_home);
                }
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                adapter = new SubItemMainAdapter(this, subsidyTagList, this, houseDataModel);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        }
        initUi();
    }

    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        if (progressDialog.isShowing())
            progressDialog.cancel();
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                HouseEnrolledController.removeFromList(HouseNewEditActivity.this, houseType, homeKey);
                HouseNewEditActivity.this.finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(Constants.APP_TAG, "");
            }
        }
    }

    private boolean isGpsCoordinateAttached(List<GpsCoordinate> gpsCoordinateList) {
        if (isReinspect) {
            if (houseDataModel.getDemographicDetails().getReGpsCoordinateList() == null) {
                return false;
            }
        }
        for (GpsCoordinate gpsCoordinate : gpsCoordinateList) {
            if (gpsCoordinate.getUserAccessData() != null
                    && gpsCoordinate.getUserAccessData().getUserId().equalsIgnoreCase(registerData.getUserId()))
                return true;
        }
        return false;
    }

    public COMPLETIONTYPE completiontype;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        switch (completiontype) {
            case INSPECTOR:
                houseDataModel.setInspectorCompletionDate("" + (calendar.getTimeInMillis() / 1000L));
                break;
            case SINSPECTOR:
                houseDataModel.setSeniorInspectorCompletionDate("" + (calendar.getTimeInMillis() / 1000L));
                break;
            case REINSPECT:
                houseDataModel.setReInspectorCompletionDate("" + (calendar.getTimeInMillis() / 1000L));
                break;
        }
        (new HouseDataFileController(this, homeKey)).updateNonHouseModel(houseDataModel);
        adapter.notifyDataSetChanged();
    }

    public enum COMPLETIONTYPE {
        INSPECTOR, SINSPECTOR, REINSPECT
    }
}
