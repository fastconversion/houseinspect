package com.houseinspect.activity.mainactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.houseinspect.R;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.ReAssigned;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.notification.FCMIdUpdateService;
import com.houseinspect.util.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_login);
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public void OnLogin(View view) {
        if(getEditTextStr(R.id.editText_LoginUserName).length() ==0){
            Toast.makeText(this,"Please fill valid username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(getEditTextStr(R.id.editText_LoginPassword).length() ==0){
            Toast.makeText(this,"Please fill valid password",Toast.LENGTH_SHORT).show();
            return;
        }
        doLogin();
    }

    private void doLogin() {
        showProgressDialog("Please wait");
        final String userName = getEditTextStr(R.id.editText_LoginUserName);
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.doLogin(getEditTextStr(R.id.editText_LoginUserName), getEditTextStr(R.id.editText_LoginPassword),
                new Callback<RegisterData>() {
            @Override
            public void success(Result<RegisterData> result) {
                File sharedPreferenceFile = new File("/data/data/com.houseinspect/shared_prefs/");
                File[] listFiles = sharedPreferenceFile.listFiles();
                if(listFiles != null)
                for (File file : listFiles) {
                    file.delete();
                }
                RegisterData registerData = result.data;
                registerData.setUserName(userName);

                List<HouseKeyDataModel> nonSubHomeListModels =  new ArrayList<>();
                for(HouseKeyDataModel nonSubHomeListModel: registerData.getNonSubHomeListModels()){
                    nonSubHomeListModels.add(nonSubHomeListModel);
                }
                List<HouseKeyDataModel> subsidyHomeListModels =  new ArrayList<>();
                for(HouseKeyDataModel nonSubHomeListModel: registerData.getSubHomeListModels()){
                    subsidyHomeListModels.add(nonSubHomeListModel);
                }
                registerData.getNonSubHomeListModels().clear();
                registerData.getSubHomeListModels().clear();
                ReAssigned reAssigned = registerData.getReAssigned();
                for(HouseKeyDataModel nonSubHomeListModel: reAssigned.getNonSubsidyKeyDataModels()){
                    nonSubHomeListModels.add(nonSubHomeListModel);
                }
                for(HouseKeyDataModel nonSubHomeListModel: reAssigned.getSubsidyKeyDataModels()){
                    subsidyHomeListModels.add(nonSubHomeListModel);
                }
                (new DataController(LoginActivity.this)).updateUserData(registerData);
                createPrevDataEntry(nonSubHomeListModels, subsidyHomeListModels);
                hideProgressDialog();
                startActivity();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                hideProgressDialog();
                if(code == 401){
                    Toast.makeText(LoginActivity.this,"Username and password not matched", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPrevDataEntry(List<HouseKeyDataModel> nonSubHomeListModelList, List<HouseKeyDataModel> subsidyHomeListModels) {
        for(HouseKeyDataModel nonSubHomeListModel: nonSubHomeListModelList){
            String key = Constants.getNonSubHouseKey(nonSubHomeListModel.getDemographicDetails());
            nonSubHomeListModel.setMobileKey(key);
            (new HouseEnrolledController(this)).addNewNonSubHome(key, nonSubHomeListModel);
        }

        for(HouseKeyDataModel nonSubHomeListModel: subsidyHomeListModels){
            String key = Constants.getSubHouseKey(nonSubHomeListModel.getDemographicDetails());
            nonSubHomeListModel.setMobileKey(key);
            (new HouseEnrolledController(this, true)).addNewNonSubHome(key, nonSubHomeListModel);
        }
    }

    private void startActivity() {
        Intent intentService = new Intent(this, FCMIdUpdateService.class);
        startService(intentService);
        Toast.makeText(LoginActivity.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    public String getEditTextStr(int viewId){
        return ((EditText) findViewById(viewId)).getText().toString().trim();
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

    //todo add sd card permission check...

}
