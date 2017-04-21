package com.houseinspect.activity.mainactivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.houseinspect.R;
import com.houseinspect.model.supportModel.Company;
import com.houseinspect.util.Constants;
import com.houseinspect.util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

public class CompanySearchActivity extends AppCompatActivity implements CompanyAdapter.OnAdapterClickListener {

    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    Toolbar toolbar;
    List<Company> mainCompanyList;
    List<Company> filteredCompanyList = new ArrayList<>();
    private CompanyAdapter adapter;
    private Company selectedCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);
        KeyboardUtil.setupKeyboardUI(findViewById(R.id.mainLayout), this);
        mainCompanyList = (new Gson()).fromJson(getIntent().getStringExtra(Constants.COMPANY_LIST),
                new TypeToken<List<Company>>() {}.getType());
        Log.d(Constants.APP_TAG,"Size:"+mainCompanyList.size());
        initRecyclerView();
    }

    private void initRecyclerView() {
        filteredCompanyList.clear();
        for(Company company :  mainCompanyList)
            filteredCompanyList.add(company);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CompanyAdapter(filteredCompanyList, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                            //filterHouse("");
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                            //filterHouse("");
                            return true;
                        }
                    });
            searchView = (SearchView) searchItem.getActionView();
            searchView.onActionViewExpanded();
            searchView.setQueryHint("Search Company");
            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            v.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void onSearchTextChange(String newText) {
        if(newText.length() > 0){
            newText = newText.toLowerCase();
            if(mainCompanyList.size() == 0)
                return;
            filteredCompanyList.clear();
            for(Company company : mainCompanyList){
                String companyName = company.getCompanyName().toLowerCase();
                if(companyName.startsWith(newText))
                    filteredCompanyList.add(company);
            }
            adapter.notifyDataSetChanged();
        }else {
            filteredCompanyList.clear();
            for(Company company : mainCompanyList){
                    filteredCompanyList.add(company);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onAdapterItemClick(Company company) {
        this.selectedCompany =  company;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setCustomTitle(null);
        builder.setMessage("You have selected "+company.getCompanyName()+".Please Confirm");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CompanySearchActivity.this.finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void finish() {
        if(selectedCompany != null) {
            Bundle conData = new Bundle();
            conData.putString(Constants.COMPANY, (new Gson()).toJson(selectedCompany));
            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_OK, intent);
        }
        super.finish();

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
}
