package com.houseinspect.model.FormModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.houseinspect.model.supportModel.GpsCoordinate;
import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.model.ServerImageResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit on 11/14/2016.
 */
public class DemographicDetails {

    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("township")
    @Expose
    private String township;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("ward")
    @Expose
    private String ward;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("stand_name")
    @Expose
    private String standName;
    @SerializedName("stand_images")
    @Expose
    private List<ImageData> standImages = new ArrayList<ImageData>();
    @SerializedName("house_number")
    @Expose
    private String houseNumber;
    @SerializedName("development_name")
    @Expose
    private String developmentName;
    @SerializedName("reg_home_builder_name")
    @Expose
    private String regHomeBuilderName;
    @SerializedName("home_builder_reg_number")
    @Expose
    private String homeBuilderRegNumber;
    @SerializedName("home_builder_reg_image")
    @Expose
    private ImageData homeBuilderRegImage;
    @SerializedName("home_builder_tel_number")
    @Expose
    private String homeBuilderTelNumber;
    @SerializedName("enrollment_number")
    @Expose
    private String enrollmentNumber;
    @SerializedName("enrollment_image")
    @Expose
    private ImageData enrollmentImage;
    @SerializedName("unit_number")
    @Expose
    private String unitNumber;
    @SerializedName("gps_coordinate")
    @Expose
    private List<GpsCoordinate> gpsCoordinateList = new ArrayList<GpsCoordinate>();

    @SerializedName("subsidy_type")
    @Expose
    private String subsidyType;

    public String getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(String subsidyType) {
        this.subsidyType = subsidyType;
    }

    @SerializedName("re_inspect_gps_coordinate")
    @Expose
    GpsCoordinate reGpsCoordinateList;

    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("deleted_images")
    @Expose
    private List<ImageData> deletedImages = new ArrayList<ImageData>();

    public List<ImageData> getDeletedImages() {
        return deletedImages;
    }

    public void setDeletedImages(List<ImageData> deletedImages) {
        this.deletedImages = deletedImages;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * @return The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return The township
     */
    public String getTownship() {
        return township;
    }

    /**
     * @param township The township
     */
    public void setTownship(String township) {
        this.township = township;
    }

    /**
     * @return The extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension The extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return The ward
     */
    public String getWard() {
        return ward;
    }

    /**
     * @param ward The ward
     */
    public void setWard(String ward) {
        this.ward = ward;
    }

    /**
     * @return The streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName The street_name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public List<ImageData> getStandImages() {
        return standImages;
    }

    public void setStandImages(List<ImageData> standImages) {
        this.standImages = standImages;
    }

    /**
     * @return The houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber The house_number
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @return The developmentName
     */
    public String getDevelopmentName() {
        return developmentName;
    }

    /**
     * @param developmentName The development_name
     */
    public void setDevelopmentName(String developmentName) {
        this.developmentName = developmentName;
    }

    /**
     * @return The regHomeBuilderName
     */
    public String getRegHomeBuilderName() {
        return regHomeBuilderName;
    }

    /**
     * @param regHomeBuilderName The reg_home_builder_name
     */
    public void setRegHomeBuilderName(String regHomeBuilderName) {
        this.regHomeBuilderName = regHomeBuilderName;
    }

    public String getHomeBuilderRegNumber() {
        return homeBuilderRegNumber;
    }

    public void setHomeBuilderRegNumber(String homeBuilderRegNumber) {
        this.homeBuilderRegNumber = homeBuilderRegNumber;
    }

    public void setHomeBuilderRegImage(ImageData homeBuilderRegImage) {
        this.homeBuilderRegImage = homeBuilderRegImage;
    }

    public ImageData getHomeBuilderRegImage() {
        return homeBuilderRegImage;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setEnrollmentImage(ImageData enrollmentImage) {
        this.enrollmentImage = enrollmentImage;
    }

    public ImageData getEnrollmentImage() {
        return enrollmentImage;
    }

    /**
     * @return The homeBuilderTelNumber
     */
    public String getHomeBuilderTelNumber() {
        return homeBuilderTelNumber;
    }

    /**
     * @param homeBuilderTelNumber The home_builder_tel_number
     */
    public void setHomeBuilderTelNumber(String homeBuilderTelNumber) {
        this.homeBuilderTelNumber = homeBuilderTelNumber;
    }


    /**
     * @return The unitNumber
     */
    public String getUnitNumber() {
        return unitNumber;
    }

    /**
     * @param unitNumber The unit_number
     */
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public GpsCoordinate getReGpsCoordinateList() {
        return reGpsCoordinateList;
    }

    public void setReGpsCoordinateList(GpsCoordinate reGpsCoordinateList) {
        this.reGpsCoordinateList = reGpsCoordinateList;
    }

    public List<GpsCoordinate> getGpsCoordinateList() {
        return gpsCoordinateList;
    }

    public void setGpsCoordinateList(List<GpsCoordinate> gpsCoordinateList) {
        this.gpsCoordinateList = gpsCoordinateList;
    }

    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }


    public void setServerUrl(List<ServerImageResponse> serverImageResponseList) {
        if (serverImageResponseList.size() == 0)
            return;
        for (ImageData imageData : standImages) {
            if (imageData!= null && imageData.getServerImageUrl() == null) {
                ServerImageResponse serverImageResponse = getServerImageResponse(serverImageResponseList, imageData);
                if (serverImageResponse != null && serverImageResponse.getServerImageUrl().length() > 0) {
                    imageData.setServerImageUrl(serverImageResponse.getServerImageUrl());
                    serverImageResponseList.remove(serverImageResponse);
                }
            }
        }

        if (enrollmentImage != null) {
            if (enrollmentImage.getServerImageUrl() == null) {
                ServerImageResponse serverImageResponse = getServerImageResponse(serverImageResponseList, enrollmentImage);
                if (serverImageResponse != null) {
                    enrollmentImage.setServerImageUrl(serverImageResponse.getServerImageUrl());
                    serverImageResponseList.remove(serverImageResponse);
                }
            }
        }

        if (homeBuilderRegImage != null) {
            if (homeBuilderRegImage.getServerImageUrl() == null) {
                ServerImageResponse serverImageResponse = getServerImageResponse(serverImageResponseList, homeBuilderRegImage);
                if (serverImageResponse != null) {
                    homeBuilderRegImage.setServerImageUrl(serverImageResponse.getServerImageUrl());
                    serverImageResponseList.remove(serverImageResponse);
                }
            }
        }
    }

    public ServerImageResponse getServerImageResponse(List<ServerImageResponse> serverImageResponseList, ImageData imageData) {
        for (ServerImageResponse serverImageResponse : serverImageResponseList) {
            if (serverImageResponse.getMobileImageId().equalsIgnoreCase(imageData.getMobileImageId())) {
                return serverImageResponse;
            }
        }
        return null;
    }

    public void removeBase64() {
        for (ImageData imageData : standImages) {
            if (imageData.getServerImageUrl() != null) {
                imageData.setBase64(null);
                imageData.setMobileImageId(null);
            }
        }

        if (enrollmentImage != null) {
            if (enrollmentImage.getServerImageUrl() != null) {
                enrollmentImage.setBase64(null);
                enrollmentImage.setMobileImageId(null);
            }
        }

        if (homeBuilderRegImage != null) {
            if (homeBuilderRegImage.getServerImageUrl() != null) {
                homeBuilderRegImage.setBase64(null);
                homeBuilderRegImage.setMobileImageId(null);
            }
        }
    }
}