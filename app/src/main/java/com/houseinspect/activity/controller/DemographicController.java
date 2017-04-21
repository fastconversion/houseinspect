package com.houseinspect.activity.controller;

import android.app.Activity;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.houseinspect.R;
import com.houseinspect.activity.adapter.HorizontalImageGridAdapter;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.supportModel.GpsCoordinate;
import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.model.supportModel.UserAccessData;
import com.houseinspect.util.Constants;
import com.houseinspect.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 11/10/2016.
 */
public class DemographicController {

    private final String houseType;
    private List<ImageData> standImageList = new ArrayList<>();
    private ImageData homeBuilderRegImage, enrollmentImage;
    Activity activity;
    private HorizontalImageGridAdapter adapter;
    private String province;
    private Location location;
    List<ImageData> deleteImageList = new ArrayList<>();
    RegisterData registerData;
    GpsCoordinate gpsCoordinate;
    List<GpsCoordinate> gpsCoordinateList = new ArrayList<>();
    private HouseKeyDataModel oldHouseKeyDataModel;

    public DemographicController(Activity activity, String houseType) {
        this.activity = activity;
        registerData = (new DataController(activity)).getUserData();
        this.houseType =  houseType;
    }

    public boolean canAddMoreStandImage() {
        if (standImageList.size() >= 5) {
            return false;
        }
        return true;
    }

    public void addStandImage(ImageData imagePath) {
        standImageList.add(imagePath);
        notifyAdapter();
    }

    public void setHomeBuilderRegImage(ImageData homeBuilderRegImage) {
        this.homeBuilderRegImage = homeBuilderRegImage;
        findViewById(R.id.layout_builderRegImage).setVisibility(View.VISIBLE);
        findViewById(R.id.button_builderRegImage).setVisibility(View.INVISIBLE);
        if (homeBuilderRegImage.getServerImageUrl() != null && homeBuilderRegImage.getServerImageUrl().length() > 0)
            Glide.with(activity)
                    .load(Constants.BASE_URL + homeBuilderRegImage.getServerImageUrl())
                    .into((ImageView) findViewById(R.id.imageView_imageGrid_BuilderRegImage));
        else
            Glide.with(activity)
                    .load(ImageUtil.getBitmapFromBase64(homeBuilderRegImage.getBase64()))
                    .into((ImageView) findViewById(R.id.imageView_imageGrid_BuilderRegImage));

    }

    public void setEnrollmentImage(ImageData enrollmentImage) {
        this.enrollmentImage = enrollmentImage;
        findViewById(R.id.layout_EnrollmentImage).setVisibility(View.VISIBLE);
        findViewById(R.id.button_EnrollmentImage).setVisibility(View.INVISIBLE);

        if (enrollmentImage.getServerImageUrl() != null && enrollmentImage.getServerImageUrl().length() > 0)
            Glide.with(activity)
                    .load(Constants.BASE_URL + enrollmentImage.getServerImageUrl())
                    .into((ImageView) findViewById(R.id.imageView_imageGrid_EnrollmentImage));
        else
            Glide.with(activity)
                    .load(ImageUtil.getBitmapFromBase64(enrollmentImage.getBase64()))
                    .into((ImageView) findViewById(R.id.imageView_imageGrid_EnrollmentImage));
    }

    public void removeEnrollmentImage() {
        if (enrollmentImage == null)
            return;
        if (enrollmentImage.getServerImageUrl() != null && enrollmentImage.getServerImageUrl().length() > 0)
            deleteImageList.add(ImageUtil.getDeletedImage(enrollmentImage.getServerImageUrl()));
        enrollmentImage = null;
        findViewById(R.id.layout_EnrollmentImage).setVisibility(View.GONE);
        findViewById(R.id.button_EnrollmentImage).setVisibility(View.VISIBLE);
    }

    public void removeHomeBuilderRegImage() {
        if (enrollmentImage == null)
            return;
        if (homeBuilderRegImage.getServerImageUrl() != null && homeBuilderRegImage.getServerImageUrl().length() > 0)
            deleteImageList.add(ImageUtil.getDeletedImage(homeBuilderRegImage.getServerImageUrl()));
        homeBuilderRegImage = null;
        findViewById(R.id.layout_builderRegImage).setVisibility(View.GONE);
        findViewById(R.id.button_builderRegImage).setVisibility(View.VISIBLE);
    }

    public void removeStandImage(ImageData imagePath) {
        if (imagePath.getServerImageUrl() != null && imagePath.getServerImageUrl().length() > 0)
            deleteImageList.add(ImageUtil.getDeletedImage(imagePath.getServerImageUrl()));
        standImageList.remove(imagePath);
    }

    public View findViewById(int viewId) {
        return activity.findViewById(viewId);
    }

    public void notifyAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new HorizontalImageGridAdapter(activity, standImageList, this, null);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public DemographicDetails getDemographicDetail() {
        DemographicDetails demographicDetails = new DemographicDetails();
        if (province == null) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please select Province", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please select Province", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setProvince(province);
        if (getEdiText(R.id.editText_demographic_townVillage).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Township/Village", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill Township/Village", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setTownship(getEdiText(R.id.editText_demographic_townVillage));


        if (getEdiText(R.id.editText_demographic_Extension).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Extension", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill Extension", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setExtension(getEdiText(R.id.editText_demographic_Extension));


        if (getEdiText(R.id.editText_demographic_Ward).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Ward", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill ward", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setWard(getEdiText(R.id.editText_demographic_Ward));


        if (getEdiText(R.id.editText_demographic_Street_Name).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Street Name", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill Street Name", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setStreetName(getEdiText(R.id.editText_demographic_Street_Name));

        if (getEdiText(R.id.editText_demographic_Stand_Name).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill Stand Name", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill Stand Name", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setStandName(getEdiText(R.id.editText_demographic_Stand_Name));


        if (getEdiText(R.id.editText_demographic_houseNumber).length() == 0) {
            Snackbar.make(findViewById(R.id.mainLayout), "Please fill House Number", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please fill House Number", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setHouseNumber(getEdiText(R.id.editText_demographic_houseNumber));

        if(houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            if (getEdiText(R.id.editText_demographic_houseSubsidyType).length() == 0) {
                Snackbar.make(findViewById(R.id.mainLayout), "Please fill Subsidy Type", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(activity, "Please fill Subsidy Type", Toast.LENGTH_SHORT).show();
                return null;
            }
            demographicDetails.setSubsidyType(getEdiText(R.id.editText_demographic_houseSubsidyType));
        }

        if (standImageList.size() > 0) {
            demographicDetails.setStandImages(standImageList);
        }

        if (getEdiText(R.id.editText_demographic_Development_Name).length() > 0) {
            demographicDetails.setDevelopmentName(getEdiText(R.id.editText_demographic_Development_Name));
        }


        if (getEdiText(R.id.editText_demographic_Regd_Home_Builder_Name).length() > 0) {
            demographicDetails.setRegHomeBuilderName(getEdiText(R.id.editText_demographic_Regd_Home_Builder_Name));
        }


        if (getEdiText(R.id.editText_demographic_Home_Builder_Reg_Number).length() == 0) {
            demographicDetails.setHomeBuilderRegNumber(getEdiText(R.id.editText_demographic_Home_Builder_Reg_Number));
        }

        if (homeBuilderRegImage != null) {
            demographicDetails.setHomeBuilderRegImage(homeBuilderRegImage);
        }

        if (getEdiText(R.id.editText_demographic_Home_builder_Tel_Number).length() > 0) {
            demographicDetails.setHomeBuilderTelNumber(getEdiText(R.id.editText_demographic_Home_builder_Tel_Number));
        }


        if (getEdiText(R.id.editText_demographic_Enrollment_No).length() == 0) {
            demographicDetails.setEnrollmentNumber(getEdiText(R.id.editText_demographic_Enrollment_No));
        }

        if (enrollmentImage != null) {
            demographicDetails.setEnrollmentImage(enrollmentImage);
        }

        if (getEdiText(R.id.editText_demographic_Unit_Number).length() > 0) {
            demographicDetails.setUnitNumber(getEdiText(R.id.editText_demographic_Unit_Number));
        }

        if (location != null) {
            if (gpsCoordinate == null) {
                gpsCoordinate = new GpsCoordinate();
                if (oldHouseKeyDataModel == null || oldHouseKeyDataModel.getExpectedDate() == null) {
                    gpsCoordinateList.add(gpsCoordinate);
                } else
                    demographicDetails.setReGpsCoordinateList(gpsCoordinate);
            }
            gpsCoordinate.setLat("" + location.getLatitude());
            gpsCoordinate.setLng("" + location.getLongitude());
            UserAccessData userAccessData = new UserAccessData();
            userAccessData.setUserId(registerData.getUserId());
            userAccessData.setCompanyId(registerData.getCompany().getCompanyId());
            userAccessData.setUserRole(registerData.getRole());
            gpsCoordinate.setUserAccessData(userAccessData);
            demographicDetails.setGpsCoordinateList(gpsCoordinateList);
        } else {
            Snackbar.make(findViewById(R.id.mainLayout), "Please Capture GPS location", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(activity, "Please Capture GPS location", Toast.LENGTH_SHORT).show();
            return null;
        }
        demographicDetails.setDeletedImages(deleteImageList);
        return demographicDetails;
    }

    private String getEdiText(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString().trim();
    }

    public void setGPSLocation(Location location) {
        this.location = location;
    }

    public void setDemographicDetail(DemographicDetails demographicDetail) {
        province = demographicDetail.getProvince();
        ((TextView) findViewById(R.id.textView_demoGraphic_Province)).setText(province);
        ((EditText) findViewById(R.id.editText_demographic_townVillage)).setText(demographicDetail.getTownship());
        findViewById(R.id.editText_demographic_townVillage).setEnabled(false);
        ((EditText) findViewById(R.id.editText_demographic_Extension)).setText(demographicDetail.getExtension());
        findViewById(R.id.editText_demographic_Extension).setEnabled(false);
        ((EditText) findViewById(R.id.editText_demographic_Ward)).setText(demographicDetail.getWard());
        findViewById(R.id.editText_demographic_Ward).setEnabled(false);
        ((EditText) findViewById(R.id.editText_demographic_Street_Name)).setText(demographicDetail.getStreetName());
        findViewById(R.id.editText_demographic_Street_Name).setEnabled(false);
        ((EditText) findViewById(R.id.editText_demographic_Stand_Name)).setText(demographicDetail.getStandName());
        findViewById(R.id.editText_demographic_Stand_Name).setEnabled(false);
        ((EditText) findViewById(R.id.editText_demographic_houseNumber)).setText(demographicDetail.getHouseNumber());
        findViewById(R.id.editText_demographic_houseNumber).setEnabled(false);

        if(houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
            ((EditText) findViewById(R.id.editText_demographic_houseSubsidyType)).setText(""+demographicDetail.getSubsidyType());
        }

        if (demographicDetail.getStandImages() != null && demographicDetail.getStandImages().size() > 0) {
            adapter = null;
            for (ImageData image : demographicDetail.getStandImages())
                standImageList.add(image);
            notifyAdapter();
        }
        if (demographicDetail.getDevelopmentName() != null)
            ((EditText) findViewById(R.id.editText_demographic_Development_Name)).setText(demographicDetail.getDevelopmentName());
        if (demographicDetail.getRegHomeBuilderName() != null)
            ((EditText) findViewById(R.id.editText_demographic_Regd_Home_Builder_Name)).setText(demographicDetail.getRegHomeBuilderName());
        if (demographicDetail.getHomeBuilderRegNumber() != null)
            ((EditText) findViewById(R.id.editText_demographic_Home_Builder_Reg_Number))
                    .setText(demographicDetail.getHomeBuilderRegNumber());

        if (demographicDetail.getHomeBuilderRegImage() != null)
            setHomeBuilderRegImage(demographicDetail.getHomeBuilderRegImage());

        if (demographicDetail.getHomeBuilderTelNumber() != null)
            ((EditText) findViewById(R.id.editText_demographic_Home_builder_Tel_Number))
                    .setText(demographicDetail.getHomeBuilderTelNumber());
        if (demographicDetail.getEnrollmentNumber() != null)
            ((EditText) findViewById(R.id.editText_demographic_Enrollment_No))
                    .setText(demographicDetail.getEnrollmentNumber());
        if (demographicDetail.getEnrollmentImage() != null)
            setEnrollmentImage(demographicDetail.getEnrollmentImage());

        if (demographicDetail.getUnitNumber() != null)
            ((EditText) findViewById(R.id.editText_demographic_Unit_Number))
                    .setText(demographicDetail.getUnitNumber());

        if (demographicDetail.getGpsCoordinateList().size() > 0) {
            for (GpsCoordinate gpsCoordinate : demographicDetail.getGpsCoordinateList())
                gpsCoordinateList.add(gpsCoordinate);
            if (oldHouseKeyDataModel == null || oldHouseKeyDataModel.getExpectedDate() == null) {
                gpsCoordinate = getUserGpsCoordinate(demographicDetail.getGpsCoordinateList());
                if (gpsCoordinate != null) {
                    location = new Location("");
                    location.setLatitude(Double.parseDouble(gpsCoordinate.getLat()));
                    location.setLongitude(Double.parseDouble(gpsCoordinate.getLng()));
                    ((TextView) findViewById(R.id.textView_demoGraphic_GPS))
                            .setText("Lat-" + location.getLatitude()
                                    + ",Lng-" + location.getLongitude());
                }
            } else {
                if (demographicDetail.getReGpsCoordinateList() != null) {
                    gpsCoordinate = demographicDetail.getReGpsCoordinateList();
                    location = new Location("");
                    location.setLatitude(Double.parseDouble(gpsCoordinate.getLat()));
                    location.setLongitude(Double.parseDouble(gpsCoordinate.getLng()));
                    ((TextView) findViewById(R.id.textView_demoGraphic_GPS))
                            .setText("Lat-" + location.getLatitude()
                                    + ",Lng-" + location.getLongitude());
                }
            }
        }
    }

    private GpsCoordinate getUserGpsCoordinate(List<GpsCoordinate> gpsCoordinateList) {
        for (GpsCoordinate gpsCoordinate : gpsCoordinateList) {
            if (gpsCoordinate.getUserAccessData() != null
                    && gpsCoordinate.getUserAccessData().getUserId().equalsIgnoreCase(registerData.getUserId()))
                return gpsCoordinate;
        }
        return null;
    }

    public void clearAllImage() {
        standImageList.clear();
        notifyAdapter();
        removeEnrollmentImage();
        removeHomeBuilderRegImage();
    }

    public void seyHouseKeyModel(HouseKeyDataModel oldHouseKeyDataModel) {
        this.oldHouseKeyDataModel = oldHouseKeyDataModel;
    }
}
