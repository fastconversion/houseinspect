package com.houseinspect.activity.mainactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SeniorInspectorSubmission;
import com.houseinspect.model.SubmitHouseResult;
import com.houseinspect.model.supportModel.UserAccessData;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;

import retrofit.RetrofitError;

public class SeniorInspectorConfirmationActivity extends AppCompatActivity {
    public static final String HOUSE_DATA_MODEL = "house_model_data";
    public static final String HOUSE_TYPE = "house_type";
    private HouseDataModel houseDataModel;
    private String uid = "0";
    private String comment;
    private RegisterData registerData;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_inspector_confirmation);
        setTitle("Confirmation");
        initDataWithUI(getIntent());
        registerData = (new DataController(this)).getUserData();
    }

    private void initDataWithUI(Intent intent) {
        String houseDataModelStr = intent.getStringExtra(HOUSE_DATA_MODEL);
        String houseType = intent.getStringExtra(HOUSE_TYPE);
        houseDataModel = (new Gson()).fromJson(houseDataModelStr, HouseDataModel.class);
        houseDataModel.removeServerUrlBase64();
        houseDataModel.setUserAccessData(Constants.getUserAccessData(this));
        if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            houseDataModel.setHouseType("subsidy");
        } else {
            houseDataModel.setHouseType("nonsubsidy");
        }
        if (houseDataModel.getUid() != null) {
            uid = houseDataModel.getUid();
        }
    }

    public void uploadToServer(SeniorInspectorSubmission seniorInspectorSubmission) {
        showProgressDialog("Please wait");
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        final Long startTime = houseDataModel.getStartTime();
        Long endTime =  (System.currentTimeMillis()/1000L);
        houseDataModel.setStartTime(null);
        service.uploadInspectionData((new Gson()).toJson(houseDataModel), uid
                , (new Gson()).toJson(seniorInspectorSubmission),startTime, endTime, new Callback<SubmitHouseResult>() {
                    @Override
                    public void success(Result<SubmitHouseResult> result) {
                        houseDataModel.setStartTime(startTime);
                        hideProgressDialog();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        SeniorInspectorConfirmationActivity.this.finish();
                    }

                    @Override
                    public void failure(RetrofitError error, int code) {
                        hideProgressDialog();
                        Toast.makeText(SeniorInspectorConfirmationActivity.this, "Something went wrong! Please Try Again",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNetworkFail(String errorMessage) {
                        hideProgressDialog();
                        Toast.makeText(SeniorInspectorConfirmationActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onCheckSubmission(View view) {
        boolean isApproved = true;
        int radioButtonId = ((RadioGroup) findViewById(R.id.radioGroup_approval)).getCheckedRadioButtonId();
        comment = ((EditText) findViewById(R.id.editText_finalComment)).getText().toString().trim();
        if (radioButtonId == R.id.radioButton_decline) {
            if (comment.length() == 0) {
                Toast.makeText(this, "Please write comment", Toast.LENGTH_SHORT).show();
                return;
            }
            isApproved = false;
        }
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_iAgree);
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please agree first", Toast.LENGTH_SHORT).show();
            return;
        }
        SeniorInspectorSubmission seniorInspectorSubmission = new SeniorInspectorSubmission();
        seniorInspectorSubmission.setApproved(isApproved);
        if (comment != null)
            seniorInspectorSubmission.setFinalComment(comment);
        seniorInspectorSubmission.setSubmissionDate("" + (System.currentTimeMillis() / 1000L));
        UserAccessData userAccessData = new UserAccessData();
        userAccessData.setUserId(registerData.getUserId());
        userAccessData.setUserRole(registerData.getRole());
        userAccessData.setCompanyId(registerData.getCompany().getCompanyId());
        seniorInspectorSubmission.setHouseId(uid);
        seniorInspectorSubmission.setSubmittedByUser(userAccessData);
        uploadToServer(seniorInspectorSubmission);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
