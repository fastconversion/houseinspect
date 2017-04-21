package com.houseinspect.activity.subItemActivity.formActivity.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.houseinspect.R;
import com.houseinspect.activity.adapter.SubStructureAdapter;
import com.houseinspect.model.HouseDataModel;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {


    private SubStructureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_inspection_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void initUI(Activity activity, ArrayList<String> subForms,
                       SubStructureAdapter.AdapterClickListener adapterClickListener,
                       HouseDataModel nonHouseDataModel, String mainForm) {
        if(adapter==null) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new SubStructureAdapter(activity, subForms, adapterClickListener, nonHouseDataModel, mainForm);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }else {
            adapter.notifyDataSetChanged();
        }
    }

}
