package com.houseinspect.activity.mainactivity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.houseinspect.R;
import com.houseinspect.activity.adapter.HouseListAdapter;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.controller.HouseDataFileController;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

public class HouseEnrolledActivity extends AppCompatActivity implements HouseListAdapter.OnHouseClick {
    private List<HouseKeyDataModel> houseKeyDataModelList = new ArrayList<>();
    private List<HouseKeyDataModel> mainHouseKeyDataModelList = new ArrayList<>();
    private HouseListAdapter adapter;
    private ProgressDialog progressDialog;
    private String houseType;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private boolean isReAssigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_enrolled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        houseType = getIntent().getStringExtra(Constants.HOUSE_TYPE);
        isReAssigned = getIntent().getBooleanExtra(Constants.IS_REASSIGN, false);
        String title = null;
        if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
            title = "Non Subsidy House";
        } else {
            title = "Subsidy House";
        }
        if (isReAssigned) {
            title = "Re-" + title;
        }
        getSupportActionBar().setTitle(title);

        initView();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new HouseListAdapter(houseKeyDataModelList, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onHouseKeyOpen(HouseKeyDataModel nonSubHomeListModel) {
        if ((new HouseDataFileController(this, nonSubHomeListModel.getMobileKey())).isDataAvailable()) {
            startNextActivity(nonSubHomeListModel);
        } else {
            getHouseData(nonSubHomeListModel);
        }
    }

    private void startNextActivity(HouseKeyDataModel nonSubHomeListModel) {
        Intent intent = new Intent(this, HouseNewEditActivity.class);
        intent.putExtra(Constants.NON_SUB_HOUSE_KEY, nonSubHomeListModel.getMobileKey());
        intent.putExtra(Constants.HOUSE_TYPE, houseType);
        intent.putExtra(Constants.IS_REASSIGN, isReAssigned);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            List<HouseKeyDataModel> houseKeyDataModelList;
            if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE))
                houseKeyDataModelList = (new HouseEnrolledController(this)).getAllNonSubHomes(isReAssigned);
            else
                houseKeyDataModelList = (new HouseEnrolledController(this, true)).getAllNonSubHomes(isReAssigned);
            this.mainHouseKeyDataModelList.clear();
            this.houseKeyDataModelList.clear();
            for (HouseKeyDataModel key : houseKeyDataModelList) {
                this.mainHouseKeyDataModelList.add(key);
            }
            filterHouse("");
            adapter.notifyDataSetChanged();
        }
    }

    public void getHouseData(final HouseKeyDataModel nonSubHomeListModel) {
        showProgressDialog("Loading Data...");
        final RegisterData registerData = (new DataController(this)).getUserData();
        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.getNonSubHouseData(nonSubHomeListModel.getUniqueKey(), registerData.getRole(),
                registerData.getUserId(), new Callback<HouseDataModel>() {
                    @Override
                    public void success(Result<HouseDataModel> result) {
                        HouseDataModel nonHouseDataModel = result.data;
                        nonHouseDataModel.setUid(nonSubHomeListModel.getUniqueKey());
                        nonHouseDataModel.setUserAccessData(null);
                        (new HouseDataFileController(HouseEnrolledActivity.this, nonSubHomeListModel.getMobileKey()))
                                .updateNonHouseModel(nonHouseDataModel);
                        hideProgressDialog();
                        startNextActivity(nonSubHomeListModel);
                    }

                    @Override
                    public void failure(RetrofitError error, int code) {
                        hideProgressDialog();
                        Toast.makeText(HouseEnrolledActivity.this, "Something went wrong! Please Try Again",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNetworkFail(String errorMessage) {
                        hideProgressDialog();
                        Toast.makeText(HouseEnrolledActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            MenuItemCompat.setOnActionExpandListener(searchItem,
                    new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem menuItem) {
                            filterHouse("");
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                            filterHouse("");
                            return true;
                        }
                    });
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    onSearchTextChange(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void onSearchTextChange(String houseNumber) {
        if (mainHouseKeyDataModelList.size() == 0) {
            return;
        } else {
            filterHouse(houseNumber);
        }
    }

    private void filterHouse(String houseNumber) {
        if (mainHouseKeyDataModelList.size() == 0)
            return;
        if (houseNumber.length() == 0) {
            houseKeyDataModelList.clear();
            for (HouseKeyDataModel houseKeyDataModel : mainHouseKeyDataModelList) {
                houseKeyDataModelList.add(houseKeyDataModel);
            }
        } else {
            houseKeyDataModelList.clear();
            for (HouseKeyDataModel houseKeyDataModel : mainHouseKeyDataModelList) {
                if (houseKeyDataModel.getDemographicDetails().getHouseNumber().startsWith(houseNumber))
                    houseKeyDataModelList.add(houseKeyDataModel);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
