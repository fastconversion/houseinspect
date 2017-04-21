package com.houseinspect.activity.subItemActivity.formActivity.form.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.adapter.HorizontalImageGridAdapter;
import com.houseinspect.activity.subItemActivity.formActivity.main.PreviousCommentActivity;
import com.houseinspect.model.supportModel.CommentUser;
import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.model.FormModel.InspectionComment;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.util.DecimalDigitsInputFilter;
import com.houseinspect.util.ImageUtil;
import com.houseinspect.view.InspectionCheckBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class FormBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public String selectedParameter = "0";
    public List<ImageData> imagePaths = new ArrayList<>();
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int REQUEST_PERMISSION = 101;
    private HorizontalImageGridAdapter adapter;
    private boolean isCompulsary = true;
    private InspectionParameter inspectionParameter;
    private OnInspectionSubmit inspectionComplete;
    List<ImageData> deletedImages = new ArrayList<>();
    private RegisterData registerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_form_base);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUi();
        EasyImage.configuration(this)
                .setImagesFolderName("HouseLabInspect")
                .saveInRootPicturesDirectory();
        inspectionParameter = new InspectionParameter();
        DataController dataController = new DataController(this);
        registerData = dataController.getUserData();
        findViewById(R.id.textView_prevComment).setVisibility(View.GONE);
    }

    private void initUi() {
        ((EditText) findViewById(R.id.editText_baseForm_measurement))
                .setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});
        findViewById(R.id.inspect_one).setOnClickListener(this);
        findViewById(R.id.inspect_two).setOnClickListener(this);
        findViewById(R.id.inspect_three).setOnClickListener(this);
        findViewById(R.id.inspect_four).setOnClickListener(this);
        findViewById(R.id.inspect_five).setOnClickListener(this);
        ((InspectionCheckBox) findViewById(R.id.inspect_one)).setChecked();

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

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void addStandImage(View view) {
        int count =  addedByMeImages();
        if (count >= 5) {
            Snackbar.make(view, "You can't add more than 5 images", Snackbar.LENGTH_SHORT).show();
        } else
            verifyPermissions(this);
    }

    private int addedByMeImages() {
        int count = 0;
        for(ImageData imageData :  imagePaths){
            if(imageData.getSubmittedBy().getUserId().equalsIgnoreCase(registerData.getUserId())){
                count++;
            }
        }
        return count;
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
            EasyImage.openCamera(FormBaseActivity.this, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(FormBaseActivity.this, "Cannot add image", Toast.LENGTH_SHORT).show();
                } else {
                    EasyImage.openCamera(FormBaseActivity.this, 0);
                }
            }
        }
    }

    public void onSaveDraft(View view) {
        if (isCompulsary) {
            if (((EditText) findViewById(R.id.editText_baseForm_measurement)).getText().toString().trim().length() == 0) {
                Snackbar.make(view, "Please fill Measurement field", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (((EditText) findViewById(R.id.editText_baseForm_comment)).getText().toString().trim().length() == 0) {
                Snackbar.make(view, "Please fill Comment field", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        inspectionParameter.setInspectionValue(selectedParameter);
        InspectionComment inspectionComment = new InspectionComment();
        CommentUser commentUser = new CommentUser();
        commentUser.setUserId(registerData.getUserId());
        commentUser.setUserName(registerData.getUserName());
        commentUser.setUserRole(registerData.getRole());
        inspectionComment.setCommentUser(commentUser);
        inspectionComment.setComment(((EditText) findViewById(R.id.editText_baseForm_comment))
                .getText().toString().trim());
        inspectionComment.setCommentDate("" + (System.currentTimeMillis() / 1000L));
        inspectionParameter.getInspectionCommentList().add(inspectionComment);
        inspectionParameter.setInspectionMeasurement(((EditText) findViewById(R.id.editText_baseForm_measurement))
                .getText().toString().trim());
        inspectionParameter.setInspectionImages(imagePaths);
        inspectionComplete.onInspectionComplete(inspectionParameter);
        inspectionParameter.setInspectionDeleteImages(deletedImages);
    }

    public void setOnInspectionComplete(OnInspectionSubmit inspectionComplete) {
        this.inspectionComplete = inspectionComplete;
    }

    public void setInspectionParameter(InspectionParameter oldInspectionParameter) {
        inspectionParameter = oldInspectionParameter;
        for (ImageData image : inspectionParameter.getInspectionImages())
            imagePaths.add(image);
        notifyAdapter();
        if (inspectionParameter.getInspectionCommentList().size() > 0) {
            findViewById(R.id.textView_prevComment).setVisibility(View.VISIBLE);
        }
        /*InspectionComment inspectionComment = getPrevInspectionCommentByUser(inspectionParameter.getInspectionCommentList());
        if (inspectionComment != null) {
            ((EditText) findViewById(R.id.editText_baseForm_comment))
                    .setText(inspectionComment.getComment());
        }*/
        if (inspectionParameter.getInspectionMeasurement() != null) {
            ((EditText) findViewById(R.id.editText_baseForm_measurement))
                    .setText(inspectionParameter.getInspectionMeasurement());
        }
        switch (inspectionParameter.getInspectionValue()) {
            case "0":
                setInspectionCheckBox(findViewById(R.id.inspect_one));
                break;
            case "0.5":
                setInspectionCheckBox(findViewById(R.id.inspect_two));
                break;
            case "1":
                setInspectionCheckBox(findViewById(R.id.inspect_three));
                break;
            case "Not Visible(NV)":
                setInspectionCheckBox(findViewById(R.id.inspect_four));
                break;
            case "Not Applicable(NA)":
                setInspectionCheckBox(findViewById(R.id.inspect_five));
                break;
        }
    }

    public void openPrevComment(View view) {
        Intent intent = new Intent(this, PreviousCommentActivity.class);
        intent.putExtra(PreviousCommentActivity.INSPECTION_COMMENTS,
                (new Gson()).toJson(inspectionParameter.getInspectionCommentList()));
        startActivity(intent);
        //todo make it on activity result...for update edited objects...
    }

    public interface OnInspectionSubmit {
        void onInspectionComplete(InspectionParameter inspectionParameter);
    }

    @Override
    public void onClick(View view) {
        setInspectionCheckBox(view);
    }

    public void setInspectionCheckBox(View view) {
        selectedParameter = ((InspectionCheckBox) view).getText();
        if (selectedParameter.equalsIgnoreCase("0") || selectedParameter.equalsIgnoreCase("0.5")) {
            isCompulsary = true;
        } else {
            isCompulsary = false;
        }
        ((InspectionCheckBox) view).setChecked();
        if (view.getId() != R.id.inspect_one)
            ((InspectionCheckBox) findViewById(R.id.inspect_one)).setUnchecked();

        if (view.getId() != R.id.inspect_two)
            ((InspectionCheckBox) findViewById(R.id.inspect_two)).setUnchecked();

        if (view.getId() != R.id.inspect_three)
            ((InspectionCheckBox) findViewById(R.id.inspect_three)).setUnchecked();

        if (view.getId() != R.id.inspect_four)
            ((InspectionCheckBox) findViewById(R.id.inspect_four)).setUnchecked();

        if (view.getId() != R.id.inspect_five)
            ((InspectionCheckBox) findViewById(R.id.inspect_five)).setUnchecked();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                ImageData imageData = ImageUtil.getImageData(ImageUtil.compressImage(imageFile.getPath()));
                CommentUser commentUser = new CommentUser();
                commentUser.setUserRole(registerData.getRole());
                commentUser.setUserId(registerData.getUserId());
                commentUser.setUserName(registerData.getUserName());
                imageData.setSubmittedBy(commentUser);
                imagePaths.add(imageData);
                notifyAdapter();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
            }
        });

    }

    public void notifyAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(FormBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new HorizontalImageGridAdapter(FormBaseActivity.this, imagePaths, null, this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void removeStandImage(ImageData imagePath) {
        if (imagePath.getServerImageUrl() != null && imagePath.getServerImageUrl().length() > 0)
            deletedImages.add(ImageUtil.getDeletedImage(imagePath.getServerImageUrl()));
        imagePaths.remove(imagePath);
        notifyAdapter();
    }

}
