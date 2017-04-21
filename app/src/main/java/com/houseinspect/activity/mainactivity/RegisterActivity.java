package com.houseinspect.activity.mainactivity;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.gson.Gson;
import com.houseinspect.R;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.model.supportModel.Company;
import com.houseinspect.model.supportModel.GpsCoordinate;
import com.houseinspect.model.supportModel.Identity;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;
import com.houseinspect.util.DateUtil;
import com.houseinspect.util.ImageUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int SIGNATURE_CODE = 101;

    RegisterData registerData = new RegisterData();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private ProgressDialog progressDialog;
    private List<Company> companyList;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_LOCATION = 102;
    private LocationRequest mLocationRequest;
    public long currentFetchedTime = 0;
    private Location captureLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EasyImage.configuration(this)
                .setImagesFolderName("HouseLabInspect")
                .saveInRootPicturesDirectory();
        setContentView(R.layout.activity_register);
        loadCompanies();
    }

    private void loadCompanies() {
        showProgressDialog("Please wait");
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.loadComapnies(new Callback<List<Company>>() {
            @Override
            public void success(Result<List<Company>> result) {
                companyList = result.data;
                hideProgressDialog();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                hideProgressDialog();
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                hideProgressDialog();
            }
        });
    }

    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED && cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            EasyImage.openChooserWithGallery(RegisterActivity.this, "Pick source", 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RegisterActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(RegisterActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    } else
                        EasyImage.openChooserWithGallery(RegisterActivity.this, "Pick source", 0);
                }
            }
            break;
            case REQUEST_PERMISSION_LOCATION: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RegisterActivity.this, "Cannot Get Location", Toast.LENGTH_SHORT).show();
                } else {
                    enableLoc();
                }
            }
            break;
        }
    }

    public void onAddSignature(View view) {
        if (registerData.getSignaturepath() == null)
            startActivityForResult(new Intent(this, SignatureActivity.class), SIGNATURE_CODE);
        else {
            registerData.setSignaturepath(null);
            findViewById(R.id.textView_removeSignature).setVisibility(View.GONE);
            findViewById(R.id.textView_signature).setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 998) {
            if(resultCode== Activity.RESULT_OK){
                Bundle res = data.getExtras();
                String result = res.getString(Constants.COMPANY);
                Company company = (new Gson()).fromJson(result, Company.class);
                ((TextView) findViewById(R.id.textView_selectedCompany)).setText(company.getCompanyName());
                registerData.setCompany(company);
            }
            return;
        } else if (requestCode == SIGNATURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result != null) {
                    registerData.setSignaturepath(result);
                    findViewById(R.id.textView_removeSignature).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView_signature).setVisibility(View.GONE);
                }
            }
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    //Some error handling
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    registerData.setIdImagePath(ImageUtil.getCompressImage(imageFile.getPath()));
                    findViewById(R.id.imageView_selectImage).setVisibility(View.GONE);
                    findViewById(R.id.button_removeIdentity).setVisibility(View.VISIBLE);
                    Glide.with(RegisterActivity.this)
                            .load(registerData.getIdImagePath())
                            .into((ImageView) findViewById(R.id.imageView_imageGrid_image));
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                }
            });
        }

        if (requestCode == REQUEST_LOCATION) {
            if (googleApiClient.isConnected())
                createLocationRequest();
        }
    }

    public void onImagePick(View view) {
        verifyStoragePermissions(this);
    }

    public void openDatePicker(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterActivity.this,
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
        registerData.setDateOfBirth(DateUtil.getDateStr(calendar, DateUtil.DATE_FORMAT_1));
        ((TextView) findViewById(R.id.textView_registerdateBirth)).setText(registerData.getDateOfBirth());
    }

    public void removeIdAttachment(View view) {
        registerData.setIdImagePath(null);
        findViewById(R.id.imageView_selectImage).setVisibility(View.VISIBLE);
        findViewById(R.id.button_removeIdentity).setVisibility(View.GONE);
    }

    public void onRegister(View view) {
        registerData.setSurname(getEditTextStr(R.id.editText_registerSurname));
        registerData.setForename(getEditTextStr(R.id.editText_registerForename));
        Identity identity = new Identity();
        int radioIdentityId = ((RadioGroup) findViewById(R.id.radioGroup_identity)).getCheckedRadioButtonId();
        if (radioIdentityId == R.id.radioButton_Passport)
            identity.setIdentityType("PASSPORT");
        else
            identity.setIdentityType("ID");
        identity.setIdentityNumber(getEditTextStr(R.id.editText_registerSurname));
        registerData.setIdentity(identity);
        registerData.setMobileNumber(getEditTextStr(R.id.editText_registerMobileNumber));
        registerData.setEmail(getEditTextStr(R.id.editText_registerEmail));
        int roleCheckedId = ((RadioGroup) findViewById(R.id.radioGroup_role)).getCheckedRadioButtonId();
        if (roleCheckedId == R.id.radioButton_SeniorInspector)
            registerData.setRole("SENIOR-INSPECTOR");
        else
            registerData.setRole("INSPECTOR");
        submitToServer();
    }

    private void submitToServer() {
        if (captureLocation == null) {
            Toast.makeText(this, "Please select your GPS Location", Toast.LENGTH_SHORT).show();
            return;
        }
        GpsCoordinate gpsCoordinate = new GpsCoordinate();
        gpsCoordinate.setLat("" + captureLocation.getLatitude());
        gpsCoordinate.setLng("" + captureLocation.getLongitude());
        registerData.setGpsCoordinate(gpsCoordinate);

        if (registerData.getIdImagePath() == null || registerData.getSignaturepath() == null) {
            Toast.makeText(this, "Form is not Properly Filled", Toast.LENGTH_SHORT).show();
            return;
        }
        TypedFile idFile = new TypedFile("multipart/form-data", new File(registerData.getIdImagePath()));
        TypedFile signatureFile = new TypedFile("multipart/form-data", new File(registerData.getSignaturepath()));
        String userInfo = (new Gson()).toJson(registerData);
        showProgressDialog("Please wait");
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.registerNow(idFile, signatureFile, userInfo, new Callback<SuccessResponse>() {
            @Override
            public void success(Result<SuccessResponse> result) {
                hideProgressDialog();
                Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                RegisterActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error, int code) {
                if (code == 409) {
                    Toast.makeText(RegisterActivity.this, "Email id already registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }

            @Override
            public void onNetworkFail(String errorMessage) {
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }

    public String getEditTextStr(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString();
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

    public void openCompany(View view) {
        if (companyList == null) {
            Toast.makeText(RegisterActivity.this, "No Company available", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, CompanySearchActivity.class);
        intent.putExtra(Constants.COMPANY_LIST, (new Gson()).toJson(companyList));
        startActivityForResult(intent, 998);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        /*

        PopupMenu popup = new PopupMenu(RegisterActivity.this, view);
        int count = 0;
        for (Company company : companyList) {
            popup.getMenu().add(Menu.NONE, count, Menu.NONE, company.getCompanyName());
            count++;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                ((TextView) findViewById(R.id.textView_selectedCompany)).setText(companyList.get(id).getCompanyName());
                registerData.setCompany(companyList.get(id));
                return true;
            }
        });
        popup.show();*/
    }

    public void addLocation(View view) {
        verifyGPSPermission();
    }

    private static final int REQUEST_PERMISSION_LOCATION = 103;

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
                                status.startResolutionForResult(RegisterActivity.this, REQUEST_LOCATION);
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

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, RegisterActivity.this);
        showProgressDialog("Getting Location");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        if (currentFetchedTime == 0) {
            currentFetchedTime = System.currentTimeMillis();
        } else {
            captureLocation = location;
            hideProgressDialog();
            ((TextView) findViewById(R.id.textView_registerLocation))
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

    public void onResetClick(View view) {
        ((EditText) findViewById(R.id.editText_registerSurname)).setText("");
        ((EditText) findViewById(R.id.editText_registerForename)).setText("");
        ((TextView) findViewById(R.id.textView_registerdateBirth)).setText("");
        ((RadioButton) findViewById(R.id.radioButton_SouthAfrican)).setChecked(true);
        ((EditText) findViewById(R.id.editText_registerIdNumber)).setText("");
        ((EditText) findViewById(R.id.editText_registerMobileNumber)).setText("");
        ((EditText) findViewById(R.id.editText_registerEmail)).setText("");
        ((RadioButton) findViewById(R.id.radioButton_inspector)).setChecked(true);
        ((TextView) findViewById(R.id.textView_selectedCompany)).setText("");
        registerData.setCompany(null);
        captureLocation = null;
        ((TextView) findViewById(R.id.textView_registerLocation)).setText("");
        registerData.setSignaturepath(null);
        registerData.setDateOfBirth(null);
        findViewById(R.id.textView_removeSignature).setVisibility(View.GONE);
        findViewById(R.id.textView_signature).setVisibility(View.VISIBLE);
        removeIdAttachment(null);
    }

}
