package com.houseinspect.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lalit on 8/25/2016.
 */
public class Logger {

    private static Logger instance;
   // private final Context context;
    private  File myFile;
    private boolean debugOn =  true;

    public static Logger getInstance(Context context) {
        if (instance == null) {
            instance = new Logger(context);
        }
        return instance;
    }

    private Logger(Context context) {
       /* this.context = context;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) //make permission check only give access if permission is granted
            {
            File file = new File(Environment.getExternalStorageDirectory(), "HouseInspect");
            if (!file.exists()) {
                file.mkdir();
            }
            String path = file.getPath();
            myFile = new File(path + "/logger.txt");
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
    //TYPE(API CALL) ----DATE ------- (API URL) --------
    //RESPONSE:dsjkbkd
    public void writeData(String logMessage) {
      /*  if(!debugOn)
            return;
        if(myFile ==  null)
            return;
        if (myFile.exists()) {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new FileOutputStream(myFile.getPath(), true));
                String message = logMessage;
                printWriter.write(message);
            } catch (Exception e) {

            }finally {
                if (printWriter != null) {
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }*/
    }

}
