package com.houseinspect.activity.subItemActivity.formActivity.main;

import android.content.Intent;
import android.os.Bundle;

import com.houseinspect.HouseInspectApplication;
import com.houseinspect.R;
import com.houseinspect.activity.adapter.SubStructureAdapter;
import com.houseinspect.activity.subItemActivity.formActivity.form.InspectionFormActivity;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.util.Constants;
import com.houseinspect.util.MyConvertor;

import java.util.ArrayList;

public class InspectionCategoryActivity extends BaseActivity
        implements SubStructureAdapter.AdapterClickListener {

    private String mainForm;
    private HouseDataModel houseDataModel;
    private ArrayList<String> subForms;
    private String houseType;
    private String houseKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainForm = getIntent().getStringExtra(Constants.MAIN_FORM_NAME);
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        houseKey = getIntent().getStringExtra(Constants.HOUSE_KEY);
        super.setTitle(mainForm);
        subForms = getIntent().getStringArrayListExtra(Constants.SUB_FORMS);
    }

    @Override
    public void onAdapterItemClick(String title) {
        Intent intent =  new Intent(this, InspectionFormActivity.class);
        intent.putExtra(Constants.MAIN_FORM_NAME, mainForm);
        intent.putExtra(Constants.FORM_NAME, MyConvertor.getTitle(title));
        intent.putExtra(Constants.HOUSE_TYPE, houseType);
        intent.putExtra(Constants.HOUSE_KEY , houseKey);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onResume(){
        super.onResume();
        houseDataModel = HouseInspectApplication.getmInstance().getNonHouseDataModel();
        super.initUI(InspectionCategoryActivity.this, subForms, this, houseDataModel, mainForm);
    }
}
