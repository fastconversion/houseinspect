package com.houseinspect.activity.subItemActivity.formActivity.main;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.houseinspect.HouseInspectApplication;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DemographicController;
import com.houseinspect.activity.controller.HouseDataFileController;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.util.Constants;
import com.houseinspect.util.ImageUtil;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class DemographicDetailActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int REQUEST_PERMISSION = 101;
    private static final int REQUEST_LOCATION = 102;
    private static final int REQUEST_PERMISSION_LOCATION = 103;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private ImageCaptureRequest imageCaptureRequest;
    private DemographicController controller;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    public long currentFetchedTime = 0;
    private ProgressDialog progressDialog;
    private Location captureLocation;
    HouseDataModel nonHouseDataModel;
    String houseType;
    HouseKeyDataModel oldHouseKeyDataModel = null;
    private boolean isProviceLock = false;
    private long startTime = (System.currentTimeMillis()/1000L);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_demographic_detail);
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        if(houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)){
            findViewById(R.id.textView_demographic_houseSubsidyTag).setVisibility(View.VISIBLE);
            findViewById(R.id.editText_demographic_houseSubsidyType).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.textView_demographic_houseSubsidyTag).setVisibility(View.GONE);
            findViewById(R.id.editText_demographic_houseSubsidyType).setVisibility(View.GONE);
        }
        setTitle("DEMOGRAPHIC DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nonHouseDataModel = HouseInspectApplication.getmInstance().getNonHouseDataModel();
        controller = new DemographicController(this, houseType);
        if (nonHouseDataModel.getDemographicDetails() != null) {
            isProviceLock =  true;
            if(nonHouseDataModel.getStartTime() != null){
                startTime = nonHouseDataModel.getStartTime();
            }
            String key = null;
            if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
                key = Constants.getNonSubHouseKey(nonHouseDataModel.getDemographicDetails());
                oldHouseKeyDataModel = (new HouseEnrolledController(this)).getHouseKeyModel(key);
            } else {
                key = Constants.getSubHouseKey(nonHouseDataModel.getDemographicDetails());
                oldHouseKeyDataModel = (new HouseEnrolledController(this, true)).getHouseKeyModel(key);
            }
            controller.seyHouseKeyModel(oldHouseKeyDataModel);
            controller.setDemographicDetail(nonHouseDataModel.getDemographicDetails());
        }else{

        }
        EasyImage.configuration(this)
                .setImagesFolderName("HouseLabInspect")
                .saveInRootPicturesDirectory();
    }

    public void openDropDownProvince(View view) {
        if(isProviceLock)
            return;
        PopupMenu popup = new PopupMenu(this, view);
        int count = 0;
        final String[] province = getResources().getStringArray(R.array.province);
        for (String race : province) {
            popup.getMenu().add(Menu.NONE, count, Menu.NONE, race);
            count++;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                controller.setProvince(province[id]);
                ((TextView) findViewById(R.id.textView_demoGraphic_Province)).setText(province[id]);
                return true;
            }
        });
        popup.show();
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void addHomeBuilderRegImage(View view) {
        imageCaptureRequest = ImageCaptureRequest.BUILDER;
        verifyPermissions(this);
    }

    public void removeBuilderRegImage(View view) {
        controller.removeHomeBuilderRegImage();
    }

    public void addEnrollmentImage(View view) {
        imageCaptureRequest = ImageCaptureRequest.ENROLLMENT;
        verifyPermissions(this);
    }

    public void removeEnrollmentImage(View view) {
        controller.removeEnrollmentImage();
    }

    public void verifyPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED || permissionCamera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );
        } else {
            EasyImage.openCamera(DemographicDetailActivity.this, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DemographicDetailActivity.this, "Cannot add image", Toast.LENGTH_SHORT).show();
                } else {
                    EasyImage.openCamera(DemographicDetailActivity.this, 0);
                }
            }
            break;
            case REQUEST_PERMISSION_LOCATION: {
                int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission != PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(DemographicDetailActivity.this, "Cannot Get Location", Toast.LENGTH_SHORT).show();
                }else {
                    enableLoc();
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
                switch (imageCaptureRequest) {
                    case STAND:
                        controller.addStandImage(ImageUtil.getImageData(ImageUtil.compressImage(imageFile.getPath())));
                        break;
                    case BUILDER:
                        controller.setHomeBuilderRegImage(ImageUtil.getImageData(ImageUtil.compressImage(imageFile.getPath())));
                        break;
                    case ENROLLMENT:
                        controller.setEnrollmentImage(ImageUtil.getImageData(ImageUtil.compressImage(imageFile.getPath())));
                        break;
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
            }
        });
        if (requestCode == REQUEST_LOCATION) {
            if (googleApiClient.isConnected())
                createLocationRequest();
        }


    }

    public void addStandImage(View view) {
        if (controller.canAddMoreStandImage()) {
            imageCaptureRequest = ImageCaptureRequest.STAND;
            verifyPermissions(this);
        } else {
            Snackbar.make(view, "You can't add more than 5 images", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void openGPSService(View view) {
        verifyGPSPermission();
    }

    enum ImageCaptureRequest {
        STAND, BUILDER, ENROLLMENT
    }

    private void verifyGPSPermission() {
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    REQUEST_PERMISSION_LOCATION
            );
        } else {
            enableLoc();
        }
    }

    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                        }
                    }).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(DemographicDetailActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SUCCESS:
                            createLocationRequest();
                            break;

                    }
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, DemographicDetailActivity.this);
        showProgressDialog("Getting Location");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        if (currentFetchedTime == 0) {
            currentFetchedTime = System.currentTimeMillis();
        } else {
            controller.setGPSLocation(location);
            captureLocation = location;
            hideProgressDialog();
            ((TextView) findViewById(R.id.textView_demoGraphic_GPS))
                    .setText("Lat-" + captureLocation.getLatitude()
                            + ",Lng-" + captureLocation.getLongitude());
            stopLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        googleApiClient.disconnect();
        googleApiClient = null;
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

    private String createKey(DemographicDetails demographicDetails) {

        String oldKey = HouseInspectApplication.getmInstance().getHomeKey();
        String newKey;
        if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
            newKey = Constants.getNonSubHouseKey(demographicDetails);
            oldHouseKeyDataModel = (new HouseEnrolledController(this)).getHouseKeyModel(oldKey);
        } else {
            newKey = Constants.getSubHouseKey(demographicDetails);
            oldHouseKeyDataModel = (new HouseEnrolledController(this, true)).getHouseKeyModel(oldKey);
        }
        HouseInspectApplication.getmInstance().setHomeKey(newKey);
        if (oldKey == null || oldKey.equalsIgnoreCase(newKey)) {
            HouseKeyDataModel nonSubHomeListModel = new HouseKeyDataModel();
            nonSubHomeListModel.setUniqueKey(nonHouseDataModel.getUid());
            nonSubHomeListModel.setDemographicDetails(demographicDetails);
            nonSubHomeListModel.setMobileKey(newKey);
            nonSubHomeListModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            if (oldHouseKeyDataModel != null)
                nonSubHomeListModel.setExpectedDate(oldHouseKeyDataModel.getExpectedDate());

            if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE))
                (new HouseEnrolledController(this)).addNewNonSubHome(newKey, nonSubHomeListModel);
            else
                (new HouseEnrolledController(this, true)).addNewNonSubHome(newKey, nonSubHomeListModel);
            return newKey;
        } else {
            (new HouseEnrolledController(this)).removeHomeKey(oldKey);
            HouseKeyDataModel nonSubHomeListModel = new HouseKeyDataModel();
            nonSubHomeListModel.setUniqueKey(nonHouseDataModel.getUid());
            nonSubHomeListModel.setDemographicDetails(demographicDetails);
            nonSubHomeListModel.setMobileKey(newKey);
            nonSubHomeListModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            if (oldHouseKeyDataModel != null)
                nonSubHomeListModel.setExpectedDate(oldHouseKeyDataModel.getExpectedDate());

            if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE))
                (new HouseEnrolledController(this)).addNewNonSubHome(newKey, nonSubHomeListModel);
            else
                (new HouseEnrolledController(this, true)).addNewNonSubHome(newKey, nonSubHomeListModel);
            HouseDataFileController.deleteOldFile(oldKey);
            return newKey;
        }
    }

    public void onSaveDraft(View view) {
        DemographicDetails demographicDetails = controller.getDemographicDetail();
        if (demographicDetails != null) {
            demographicDetails.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            nonHouseDataModel.setDemographicDetails(demographicDetails);
            nonHouseDataModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            String key = createKey(demographicDetails);
            nonHouseDataModel.setStartTime(startTime);
            (new HouseDataFileController(this, key)).updateNonHouseModel(nonHouseDataModel);
            this.finish();
        }
    }

    public void onFormReset(View view) {
        controller.setProvince(null);
        ((TextView) findViewById(R.id.textView_demoGraphic_Province)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_townVillage)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Extension)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Ward)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Street_Name)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Stand_Name)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_houseNumber)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Development_Name)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Regd_Home_Builder_Name)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Home_Builder_Reg_Number)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Home_builder_Tel_Number)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Enrollment_No)).setText("");
        ((EditText) findViewById(R.id.editText_demographic_Unit_Number)).setText("");
        ((TextView) findViewById(R.id.editText_demographic_Stand_Name)).setText("");
        controller.clearAllImage();
        controller.setGPSLocation(null);
        captureLocation = null;
        ((TextView) findViewById(R.id.textView_demoGraphic_GPS)).setText("");
    }
}
