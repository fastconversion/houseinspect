package com.houseinspect.activity.mainactivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.CircleTransform;
import com.houseinspect.util.DateUtil;
import com.houseinspect.util.ImageUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit.RetrofitError;

public class ProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MenuItem editSaveMenu;
    private RegisterData registerData;
    private Toolbar toolbar;
    private STATE state = STATE.EDIT;
    private String newImage;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initBaseUi();
        initViewUI();
    }

    private void initViewUI() {
        if (registerData == null)
            registerData = (new DataController(this)).getUserData();
        setTextViewText(R.id.textView_profileName, registerData.getForename() + " " + registerData.getSurname());
        setTextViewText(R.id.textView_profileEmail, registerData.getEmail());
        setTextViewText(R.id.textView_profileDOB, registerData.getDateOfBirth());
        setTextViewText(R.id.textView_profileMobile, registerData.getMobileNumber());
        setTextViewText(R.id.textView_profileCompanyName, registerData.getCompany().getCompanyName());
        setTextViewText(R.id.textView_profileRole, registerData.getRole());

        if (registerData.getProfileImage() != null) {
            if (registerData.getProfileImage().getServerImageUrl() != null) {
                Glide.with(this)
                        .load(registerData.getProfileImage().getServerImageUrl())
                        .bitmapTransform(new CircleTransform(ProfileActivity.this))
                        .into((ImageView) findViewById(R.id.imageView_profileImage));
            } else {
                Glide.with(this)
                        .load(ImageUtil.getBitmapFromBase64(registerData.getProfileImage().getBase64()))
                        .bitmapTransform(new CircleTransform(ProfileActivity.this))
                        .into((ImageView) findViewById(R.id.imageView_profileImage));
            }
        }

        findViewById(R.id.content_view_profile).setVisibility(View.VISIBLE);
        findViewById(R.id.content_edit_profile).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageView_profileImageCapture).setVisibility(View.INVISIBLE);
    }

    private void setTextViewText(int viewID, String text) {
        ((TextView) findViewById(viewID)).setText(text);
    }

    private void setEditViewText(int viewID, String text) {
        ((EditText) findViewById(viewID)).setText(text);
    }

    private String getEditedText(int viewId) {
        View view = findViewById(viewId);
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else {
            return ((TextView) view).getText().toString().trim();
        }
    }

    private void initBaseUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        ((AppBarLayout) findViewById(R.id.appbar)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    if (editSaveMenu != null) {
                        editSaveMenu.setVisible(true);
                    }
                } else {
                    if (editSaveMenu != null) {
                        editSaveMenu.setVisible(false);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        editSaveMenu = menu.findItem(R.id.action_EditSave);
        editSaveMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_EditSave:
                onEditSaveClick(null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEditSaveClick(View view) {
        switch (state) {
            case EDIT:
                state = STATE.EDITING;
                editSaveMenu.setTitle("Save");
                ((FloatingActionButton) findViewById(R.id.fabButton))
                        .setImageResource(R.drawable.ic_primary_tick);
                initEditView();
                break;
            case EDITING:
                uploadToServer();

                break;
        }
    }

    private void uploadToServer() {
        if (getEditedText(R.id.editText_profileForeName).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Fore name", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please fill Fore name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getEditedText(R.id.editText_profileSurName).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Sur name", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please fill Sur name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getEditedText(R.id.editText_profileMobile).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Mobile", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Please fill Mobile", Toast.LENGTH_SHORT).show();
            return;
        }

        final RegisterData registerDataUpdate = new RegisterData();
        registerDataUpdate.setForename(getEditedText(R.id.editText_profileForeName));
        registerDataUpdate.setSurname(getEditedText(R.id.editText_profileSurName));
        registerDataUpdate.setMobileNumber(getEditedText(R.id.editText_profileMobile));
        registerDataUpdate.setDateOfBirth(getEditedText(R.id.textView_profileEditDateOfBirth));
        registerDataUpdate.setUserName(this.registerData.getUserName());
        registerDataUpdate.setUserId(this.registerData.getUserId());
        if (newImage != null) {
            ImageData imageData = new ImageData();
            imageData.setBase64(newImage);
            registerDataUpdate.setProfileImage(imageData);
        } else {
            if (this.registerData.getProfileImage() != null && this.registerData.getProfileImage().getServerImageUrl() != null) {
                ImageData imageData = new ImageData();
                imageData.setServerImageUrl(this.registerData.getProfileImage().getServerImageUrl());
                registerDataUpdate.setProfileImage(imageData);

            }
        }
        showProgressDialog("Please wait");
        registerDataUpdate.setNonSubHomeListModels(null);
        registerDataUpdate.setSubHomeListModels(null);
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.updateProfile((new Gson()).toJson(registerDataUpdate), new Callback<SuccessResponse>() {
            @Override
            public void success(Result<SuccessResponse> result) {
                state = STATE.EDIT;
                editSaveMenu.setTitle("Edit");
                registerData.setForename(registerDataUpdate.getForename());
                registerData.setSurname(registerDataUpdate.getSurname());
                registerData.setDateOfBirth(registerDataUpdate.getDateOfBirth());
                registerData.setMobileNumber(registerDataUpdate.getMobileNumber());
                registerData.setProfileImage(registerDataUpdate.getProfileImage());
                ((FloatingActionButton) findViewById(R.id.fabButton))
                        .setImageResource(R.drawable.ic_action_edit);
                initViewUI();
                saveRegisterData();
                hideProgressDialog();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                hideProgressDialog();
                Toast.makeText(ProfileActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });


    }

    private void saveRegisterData() {
        DataController controller = new DataController(this);
        controller.updateUserData(registerData);
    }

    private void initEditView() {
        findViewById(R.id.content_view_profile).setVisibility(View.INVISIBLE);
        findViewById(R.id.content_edit_profile).setVisibility(View.VISIBLE);
        findViewById(R.id.imageView_profileImageCapture).setVisibility(View.VISIBLE);

        setEditViewText(R.id.editText_profileForeName, registerData.getForename());
        setEditViewText(R.id.editText_profileSurName, registerData.getSurname());
        setEditViewText(R.id.editText_profileMobile, registerData.getMobileNumber());
        setTextViewText(R.id.textView_profileEditDateOfBirth, registerData.getDateOfBirth());
    }

    public void onCaptureImage(View view) {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED && cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            EasyImage.openChooserWithGallery(this, "Pick source", 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    } else
                        EasyImage.openChooserWithGallery(ProfileActivity.this, "Pick source", 0);
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                newImage = ImageUtil.compressImage(imageFile.getPath());
                Glide.with(ProfileActivity.this)
                        .load(ImageUtil.getBitmapFromBase64(newImage))
                        .bitmapTransform(new CircleTransform(ProfileActivity.this))
                        .into((ImageView) findViewById(R.id.imageView_profileImage));
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
            }
        });
    }

    public void selectDateOfBirth(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ProfileActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Select DOB");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ((TextView) findViewById(R.id.textView_profileEditDateOfBirth)).setText(DateUtil.getDateStr(calendar, DateUtil.DATE_FORMAT_1));
    }

    public enum STATE {
        EDITING, EDIT
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
