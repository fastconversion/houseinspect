package com.houseinspect.activity.mainactivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.houseinspect.R;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;

import retrofit.RetrofitError;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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

    public void onSubmit(View view) {
        String emailId = ((EditText) findViewById(R.id.editText_forgotEmail)).getText().toString().trim();
        if (emailId.length() == 0) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Constants.isValidEmail(emailId)) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog("Please wait");
        (new HouseLabApi()).getHouseLabService().forgotEmail(emailId, new Callback<SuccessResponse>() {
            @Override
            public void success(Result<SuccessResponse> result) {
                hideProgressDialog();
                showEmailSendAlert();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                hideProgressDialog();
                if (code == 401) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email id not found! Please Provide registered Email Id",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ForgotPasswordActivity.this, "Something went wrong! Please Try Again",
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                hideProgressDialog();
                Toast.makeText(ForgotPasswordActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEmailSendAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Success");
        alertDialog.setMessage("A link has been sent to your email for reset Password.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ForgotPasswordActivity.this.finish();
            }
        });
        alertDialog.show();
    }
}
