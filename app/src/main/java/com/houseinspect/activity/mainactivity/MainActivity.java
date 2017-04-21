package com.houseinspect.activity.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.houseinspect.HouseInspectApplication;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.util.CircleTransform;
import com.houseinspect.util.Constants;
import com.houseinspect.util.ImageUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    MenuItem subsidyItem, nonSubsidyItem, reSubsidyItem, reNonSubsidyItem;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBaseUI();
    }

    private void setUpBaseUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        subsidyItem = menu.findItem(R.id.nav_SubHouse);
        nonSubsidyItem = menu.findItem(R.id.nav_NonSubHouse);
        reNonSubsidyItem = menu.findItem(R.id.nav_reInspect_nonSub);
        reSubsidyItem = menu.findItem(R.id.nav_reInspect_sub);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_drawer_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RegisterData registerData = (new DataController(this)).getUserData();
        getSupportActionBar().setTitle(registerData.getSurname() + " " + registerData.getForename());
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        openActivity(menuItem.getItemId());
                        return true;
                    }
                });
    }

    private void openActivity(int itemId) {
        switch (itemId) {
            case R.id.nav_NonSubHouse:
                Intent intent = new Intent(this, HouseEnrolledActivity.class);
                intent.putExtra(Constants.HOUSE_TYPE, Constants.NON_SUBSIDY_TYPE);
                intent.putExtra(Constants.IS_REASSIGN, false);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_SubHouse:
                Intent newIntent = new Intent(this, HouseEnrolledActivity.class);
                newIntent.putExtra(Constants.HOUSE_TYPE, Constants.SUBSIDY_TYPE);
                newIntent.putExtra(Constants.IS_REASSIGN, false);
                startActivity(newIntent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.nav_reInspect_nonSub:
                Intent intentre = new Intent(this, HouseEnrolledActivity.class);
                intentre.putExtra(Constants.HOUSE_TYPE, Constants.NON_SUBSIDY_TYPE);
                intentre.putExtra(Constants.IS_REASSIGN, true);
                startActivity(intentre);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_reInspect_sub:
                Intent newIntentRE = new Intent(this, HouseEnrolledActivity.class);
                newIntentRE.putExtra(Constants.HOUSE_TYPE, Constants.SUBSIDY_TYPE);
                newIntentRE.putExtra(Constants.IS_REASSIGN, true);
                startActivity(newIntentRE);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_update_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_changePassword:
                Intent passwordIntent = new Intent(this, ChangePasswordActivity.class);
                startActivity(passwordIntent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_about_us:
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.nav_logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.FCM_SHARED_PREF, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isUploaded", false);
                editor.commit();
                DataController dataController = new DataController(this);
                dataController.clearAllData();
                HouseEnrolledController.removeAllEntry(this);

                File sharedPreferenceFile = new File("/data/data/com.houseinspect/shared_prefs/");
                File[] listFiles = sharedPreferenceFile.listFiles();
                if (listFiles != null)
                    for (File file : listFiles) {
                            file.delete();
                    }
                Intent finishIntent = new Intent(this, LoginActivity.class);
                startActivity(finishIntent);
                MainActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openSubsidy(View view) {
        HouseInspectApplication.getmInstance().setHomeKey(null);
        HouseInspectApplication.getmInstance().setNonHouseDataModel(null);
        Intent intent = new Intent(this, HouseNewEditActivity.class);
        intent.putExtra(Constants.HOUSE_TYPE, Constants.SUBSIDY_TYPE);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void openNonSubsidy(View view) {
        HouseInspectApplication.getmInstance().setHomeKey(null);
        HouseInspectApplication.getmInstance().setNonHouseDataModel(null);
        Intent intent = new Intent(this, HouseNewEditActivity.class);
        intent.putExtra(Constants.HOUSE_TYPE, Constants.NON_SUBSIDY_TYPE);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onResume() {
        super.onResume();
        int nonSubHouse = (new HouseEnrolledController(this)).getAllNonSubHomes(false).size();
        int subHouse = (new HouseEnrolledController(this, true)).getAllNonSubHomes(false).size();
        int reNonSubHouse = (new HouseEnrolledController(this)).getAllNonSubHomes(true).size();
        int reSubHouse = (new HouseEnrolledController(this, true)).getAllNonSubHomes(true).size();
        if (nonSubHouse > 0) {
            nonSubsidyItem.setTitle("Non Subsidy House(" + nonSubHouse + ")");
        } else {
            nonSubsidyItem.setTitle("Non Subsidy House");
        }

        if (reNonSubHouse > 0) {
            reNonSubsidyItem.setTitle("Non Subsidy House(" + reNonSubHouse + ")");
        } else {
            reNonSubsidyItem.setTitle("Non Subsidy House");
        }

        if (subHouse > 0) {
            subsidyItem.setTitle("Subsidy House(" + subHouse + ")");
        } else {
            subsidyItem.setTitle("Subsidy House");
        }

        if (reSubHouse > 0) {
            reSubsidyItem.setTitle("Subsidy House(" + reSubHouse + ")");
        } else {
            reSubsidyItem.setTitle("Subsidy House");
        }

        RegisterData registerData = (new DataController(this)).getUserData();
        ((TextView) headerView.findViewById(R.id.textView_header_username)).setText(registerData.getForename() + " " + registerData.getSurname());
        ((TextView) headerView.findViewById(R.id.textView_header_location)).setText("");
        if (registerData.getProfileImage() != null) {
            if (registerData.getProfileImage().getServerImageUrl() != null) {
                Glide.with(this)
                        .load(registerData.getProfileImage().getServerImageUrl())
                        .bitmapTransform(new CircleTransform(MainActivity.this))
                        .into((ImageView) headerView.findViewById(R.id.imageView_header_userImage));
            } else {
                Glide.with(this)
                        .load(ImageUtil.getBitmapFromBase64(registerData.getProfileImage().getBase64()))
                        .bitmapTransform(new CircleTransform(MainActivity.this))
                        .into((ImageView) headerView.findViewById(R.id.imageView_header_userImage));
            }
        }
    }

}

