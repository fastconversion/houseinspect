package com.houseinspect.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;

import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.model.ServerImageResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lalit on 8/24/2016.
 */
public class ImageUtil {

    private static final String FOLDER_NAME = "HouseInspect";
    List<ServerImageResponse> serverImageResponseList;

    public static String compressImage(String filePath) {
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 512.0f;
        float maxWidth = 384.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        if (bmp != null)
            bmp.recycle();
        if (scaledBitmap != null)
            scaledBitmap.recycle();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static byte[] getBitmapFromBase64(String data) {
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
        return decodedString;
    }

    public static ImageData getImageData(String base64) {
        ImageData imageData = new ImageData();
        imageData.setBase64(base64);
        imageData.setMobileImageId("" + System.currentTimeMillis());
        return imageData;
    }

    public static ImageData getDeletedImage(String imageUrl) {
        ImageData imageData = new ImageData();
        imageData.setServerImageUrl(imageUrl);
        return imageData;
    }

    public ImageUtil(List<ServerImageResponse> serverImageResponseList) {
        this.serverImageResponseList = serverImageResponseList;
    }

    public void setServerUrl(List<ImageData> imageDataList) {
        if (imageDataList.size() == 0)
            return;
        for (ImageData imageData : imageDataList) {
            if (imageData.getServerImageUrl() == null) {
                ServerImageResponse serverImageResponse = getServerImageResponse(serverImageResponseList, imageData);
                if (serverImageResponse != null && serverImageResponse.getServerImageUrl().length() > 0) {
                    imageData.setServerImageUrl(serverImageResponse.getServerImageUrl());
                    serverImageResponseList.remove(serverImageResponse);
                }
            }
        }
    }

    private ServerImageResponse getServerImageResponse(List<ServerImageResponse> serverImageResponseList, ImageData imageData) {
        for (ServerImageResponse serverImageResponse : serverImageResponseList) {
            if (serverImageResponse.getMobileImageId().equalsIgnoreCase(imageData.getMobileImageId())) {
                return serverImageResponse;
            }
        }
        return null;
    }

    public ImageUtil() {
    }

    public void removeBase64(List<ImageData> imageDataList) {
        if (imageDataList != null && imageDataList.size() > 0)
            for (ImageData imageData : imageDataList) {
                if (imageData.getServerImageUrl() != null) {
                    imageData.setBase64(null);
                    imageData.setMobileImageId(null);
                }
            }
    }

    public static String getCompressImage(String imagePath) {
            String fileName = getFilename();
        FileOutputStream fos = null;
        try {
            String base64ImageData = compressImage(imagePath);
            byte[] decodedString = Base64.decode(base64ImageData, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            fos = new FileOutputStream(fileName);
            decodedByte.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            decodedByte.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    private static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), FOLDER_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }
}
