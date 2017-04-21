package com.houseinspect.activity.mainactivity;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.model.supportModel.Company;

import java.util.List;

/**
 * Created by Lalit on 2/1/2017.
 */
public class CompanyAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final List<Company> filteredCompanyList;
    private final Activity activity;
    private OnAdapterClickListener listener;

    @Override
    public void onClick(View view) {
        Company company = (Company) view.getTag();
        listener.onAdapterItemClick(company);
    }

    public interface OnAdapterClickListener{
        void onAdapterItemClick(Company company);
    }

    public CompanyAdapter(List<Company> filteredCompanyList, Activity activity, OnAdapterClickListener listener) {
        this.filteredCompanyList = filteredCompanyList;
        this.activity = activity;
        this.listener  =  listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        view = LayoutInflater.from(activity).inflate(R.layout.item_company, parent, false);
        viewHolder = new ViewHolderCompany(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderCompany holderCompany = (ViewHolderCompany) holder;
        holderCompany.companyName.setText(filteredCompanyList.get(position).getCompanyName());
        holderCompany.mainLayout.setTag(filteredCompanyList.get(position));
        holderCompany.mainLayout.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return filteredCompanyList.size();
    }

    private class ViewHolderCompany extends RecyclerView.ViewHolder {
        View mainLayout;
        TextView companyName;
        public ViewHolderCompany(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.mainLayout);
            companyName = (TextView) view.findViewById(R.id.textView_companyName);
        }
    }
}
