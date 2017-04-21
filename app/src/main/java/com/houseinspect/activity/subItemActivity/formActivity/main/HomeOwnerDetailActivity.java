package com.houseinspect.activity.subItemActivity.formActivity.main;

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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.houseinspect.HouseInspectApplication;
import com.houseinspect.R;
import com.houseinspect.activity.adapter.FamilyMemberAdapter;
import com.houseinspect.activity.controller.HouseDataFileController;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.activity.dialog.AddFamilyMemberDialog;
import com.houseinspect.model.supportModel.FamilyMember;
import com.houseinspect.model.FormModel.HomeOwnerInfo;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.util.Constants;
import com.houseinspect.util.DateUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeOwnerDetailActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, AddFamilyMemberDialog.OnFamilyMemberAdded,
        FamilyMemberAdapter.OnRemoveMemberListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.CAMERA};
    private String[] raceList;
    private String[] familyIncomeList;
    List<FamilyMember> familyMembers = new ArrayList<>();
    private FamilyMemberAdapter adapter;
    private HomeOwnerInfo homeOwnerInfo;
    private Calendar calendar;
    private HouseDataModel nonHouseDataModel;
    private String houseType,houseKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_home_owner_detail);
        setTitle("HOME OWNER DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        houseKey = getIntent().getStringExtra(Constants.HOUSE_KEY);
        nonHouseDataModel = HouseInspectApplication.getmInstance().getNonHouseDataModel();
        raceList = getResources().getStringArray(R.array.race);
        familyIncomeList = getResources().getStringArray(R.array.family_income);
        if (nonHouseDataModel.getHomeOwnerInfo() != null) {
            homeOwnerInfo = nonHouseDataModel.getHomeOwnerInfo();
            setHomeOwnerInfo();
        }
    }

    private void setHomeOwnerInfo() {
        if (homeOwnerInfo == null)
            return;
        if (homeOwnerInfo.getFirstName() != null)
            setEditText(R.id.editText_homeOwner_firstName, homeOwnerInfo.getFirstName());

        if (homeOwnerInfo.getLastName() != null)
            setEditText(R.id.editText_homeOwner_LastName, homeOwnerInfo.getLastName());

        if (homeOwnerInfo.getIdNumber() != null)
            setTextView(R.id.textView_homeOwner_scanCode, homeOwnerInfo.getIdNumber());

        if (homeOwnerInfo.getMobileNumber() != null)
            setEditText(R.id.editText_homeOwner_mobile, homeOwnerInfo.getMobileNumber());

        if (homeOwnerInfo.getGender() != null && homeOwnerInfo.getGender().equalsIgnoreCase("female")) {
            ((RadioButton) findViewById(R.id.radioButton_female)).setChecked(true);
        }
        if (homeOwnerInfo.getNationality() != null && homeOwnerInfo.getNationality().equalsIgnoreCase("non sa citizens")) {
            ((RadioButton) findViewById(R.id.radioButton_nonSaCitizen)).setChecked(true);
        }
        if (homeOwnerInfo.getRace() != null)
            setTextView(R.id.textView_homeOwner_race, homeOwnerInfo.getRace());
        if (homeOwnerInfo.getDateOfBirth() != null) {
            long timeStamp = Long.parseLong(homeOwnerInfo.getDateOfBirth()) * 1000L;
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
            setTextView(R.id.textView_homeOwner_Date_of_birth, DateUtil.getDateStr(calendar, DateUtil.DATE_FORMAT_1));
        }

        if (homeOwnerInfo.getFamilyIncome() != null)
            setTextView(R.id.textView_homeOwner_Family_Income, homeOwnerInfo.getFamilyIncome());

        if (homeOwnerInfo.getLivingPeople() != null && homeOwnerInfo.getLivingPeople().size() > 0) {
            for (FamilyMember familyMember : homeOwnerInfo.getLivingPeople()) {
                familyMembers.add(familyMember);
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new FamilyMemberAdapter(this, familyMembers, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.notifyDataSetChanged();
        }

    }

    private void setEditText(int viewId, String text) {
        ((EditText) findViewById(viewId)).setText(text);
    }

    private void setTextView(int viewId, String text) {
        ((TextView) findViewById(viewId)).setText(text);
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

    public void openScanner(View view) {
        verifyCameraPermissions(this);
    }

    public void openRaceList(View view) {
        PopupMenu popup = new PopupMenu(HomeOwnerDetailActivity.this, view);
        int count = 0;
        for (String race : raceList) {
            popup.getMenu().add(Menu.NONE, count, Menu.NONE, race);
            count++;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                ((TextView) findViewById(R.id.textView_homeOwner_race)).setText(raceList[id]);
                return true;
            }
        });
        popup.show();
    }

    public void onAddMorePeople(View view) {
        AddFamilyMemberDialog addFamilyMemberDialog = new AddFamilyMemberDialog(this, this);
        addFamilyMemberDialog.show();
    }

    public void openCalender(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                HomeOwnerDetailActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Select DOB");
    }

    public void verifyCameraPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            new IntentIntegrator(this).initiateScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeOwnerDetailActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                } else {
                    new IntentIntegrator(this).initiateScan();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                String scannedCode = result.getContents();
                ((TextView) findViewById(R.id.textView_homeOwner_scanCode)).setText(scannedCode);
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ((TextView) findViewById(R.id.textView_homeOwner_Date_of_birth)).setText(DateUtil.getDateStr(calendar, DateUtil.DATE_FORMAT_1));
    }

    public void openFamilyIncome(View view) {
        PopupMenu popup = new PopupMenu(HomeOwnerDetailActivity.this, view);
        int count = 0;
        for (String income : familyIncomeList) {
            popup.getMenu().add(Menu.NONE, count, Menu.NONE, income);
            count++;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                ((TextView) findViewById(R.id.textView_homeOwner_Family_Income)).setText(familyIncomeList[id]);
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onNewMemberAdded(FamilyMember familyMember) {
        familyMembers.add(familyMember);
        if (adapter == null) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new FamilyMemberAdapter(this, familyMembers, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemoveMember(FamilyMember familyMember) {
        familyMembers.remove(familyMember);
        adapter.notifyDataSetChanged();
    }

    public void onSaveDraft(View view) {
        int count = 0;
        HomeOwnerInfo homeOwnerInfo = new HomeOwnerInfo();
        if (getEditText(R.id.editText_homeOwner_firstName).length() > 0) {
            homeOwnerInfo.setFirstName(getEditText(R.id.editText_homeOwner_firstName));
            count++;
        }
        if (getEditText(R.id.editText_homeOwner_LastName).length() > 0) {
            homeOwnerInfo.setLastName(getEditText(R.id.editText_homeOwner_LastName));
            count++;
        }
        if (((TextView) findViewById(R.id.textView_homeOwner_scanCode)).getText().toString().length() > 0) {
            homeOwnerInfo.setIdNumber(((TextView) findViewById(R.id.textView_homeOwner_scanCode)).getText().toString());
            count++;
        }

        if (getEditText(R.id.editText_homeOwner_mobile).length() > 0) {
            homeOwnerInfo.setMobileNumber(getEditText(R.id.editText_homeOwner_mobile));
            count++;
        }
        homeOwnerInfo.setGender(((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup_homeOwner_Gender))
                .getCheckedRadioButtonId())).getText().toString());
        homeOwnerInfo.setNationality(((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup_homeOwner_Nationality))
                .getCheckedRadioButtonId())).getText().toString());

        if (((TextView) findViewById(R.id.textView_homeOwner_race)).getText().toString().length() > 0) {
            homeOwnerInfo.setRace(((TextView) findViewById(R.id.textView_homeOwner_race)).getText().toString());
            count++;
        }
        if (calendar != null) {
            homeOwnerInfo.setDateOfBirth("" + (calendar.getTimeInMillis() / 1000L));
            count++;
        }

        if (((TextView) findViewById(R.id.textView_homeOwner_Family_Income)).getText().toString().length() > 0) {
            homeOwnerInfo.setFamilyIncome(((TextView) findViewById(R.id.textView_homeOwner_Family_Income)).getText().toString());
            count++;
        }

        if (familyMembers.size() > 0) {
            homeOwnerInfo.setLivingPeople(familyMembers);
            count++;

        }
        if (count > 0) {
            homeOwnerInfo.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            nonHouseDataModel.setHomeOwnerInfo(homeOwnerInfo);
            nonHouseDataModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
            (new HouseDataFileController(this, HouseInspectApplication.getmInstance().getHomeKey()))
                    .updateNonHouseModel(nonHouseDataModel);
            HouseEnrolledController houseEnrolledController;
            if (houseType.equalsIgnoreCase(Constants.SUBSIDY_TYPE)) {
                houseEnrolledController = new HouseEnrolledController(this, true);
            } else {
                houseEnrolledController = new HouseEnrolledController(this);
            }
            houseEnrolledController.updateHouseKeyModel(houseKey);
        } else {
            Snackbar.make(findViewById(R.id.mainLayout), "Form not completed", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Form not completed", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }

    private String getEditText(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString().trim();
    }


    public void onHouseReset(View view) {
        ((EditText) findViewById(R.id.editText_homeOwner_firstName)).setText("");
        ((EditText) findViewById(R.id.editText_homeOwner_LastName)).setText("");
        ((TextView) findViewById(R.id.textView_homeOwner_scanCode)).setText("");
        ((EditText) findViewById(R.id.editText_homeOwner_mobile)).setText("");
        ((RadioButton) findViewById(R.id.radioButton_male)).setChecked(true);
        ((RadioButton) findViewById(R.id.radioButton_saCitizen)).setChecked(true);
        ((TextView) findViewById(R.id.textView_homeOwner_race)).setText("");
        ((TextView) findViewById(R.id.textView_homeOwner_Date_of_birth)).setText("");
        ((TextView) findViewById(R.id.textView_homeOwner_Family_Income)).setText("");
        familyMembers.clear();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
}
