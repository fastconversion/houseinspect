package com.houseinspect.activity.mainactivity;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;

import retrofit.RetrofitError;

public class ChangePasswordActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RegisterData registerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Change Password");
        registerData = (new DataController(this)).getUserData();
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

    public void onChangePassword(View view) {
        if(getEdiText(R.id.editText_oldPassword).length() == 0){
            Snackbar.make(findViewById(R.id.mainLayout), "Please provide old password", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please select Province", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getEdiText(R.id.editText_newPassword).length() == 0){
            Snackbar.make(findViewById(R.id.mainLayout), "Please provide New password", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please provide New password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getEdiText(R.id.editText_newRePassword).length() == 0){
            Snackbar.make(findViewById(R.id.mainLayout), "Please provide Confirm password", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please provide Confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!getEdiText(R.id.editText_newRePassword).equalsIgnoreCase(getEdiText(R.id.editText_newPassword))){
            Snackbar.make(findViewById(R.id.mainLayout), "New password & Confirm password not matched", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "New password & Confirm password not matched", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog("Please wait...");
        HouseLabService service =  (new HouseLabApi()).getHouseLabService();
        service.changePassword(registerData.getUserId(), getEdiText(R.id.editText_oldPassword),
                getEdiText(R.id.editText_newPassword), new Callback<SuccessResponse>() {
                    @Override
                    public void success(Result<SuccessResponse> result) {
                        hideProgressDialog();
                        Toast.makeText(ChangePasswordActivity.this,"Password changed successfully", Toast.LENGTH_SHORT).show();
                        ChangePasswordActivity.this.finish();
                    }

                    @Override
                    public void failure(RetrofitError error, int code) {
                        hideProgressDialog();
                        if(code == 401){
                            Toast.makeText(ChangePasswordActivity.this,"Old Password not matched", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChangePasswordActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNetworkFail(String errorMessage) {
                        hideProgressDialog();
                        Toast.makeText(ChangePasswordActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private String getEdiText(int viewId) {
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
}
