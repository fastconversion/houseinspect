package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.model.FormModel.DemographicDetails;
import com.houseinspect.util.DateUtil;

import java.util.List;

/**
 * Created by Lalit on 11/15/2016.
 */
public class HouseListAdapter extends RecyclerView.Adapter implements View.OnClickListener {


    private final List<HouseKeyDataModel> nonSubHomeList;
    private final Activity activity;
    private final OnHouseClick listener;

    private final int NORMAL = 0, REINSPECT=1;

    public HouseListAdapter(List<HouseKeyDataModel> nonSubHomeList, Activity activity, OnHouseClick listener) {
        this.nonSubHomeList =  nonSubHomeList;
        this.activity = activity;
        this.listener =  listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;//
        if(viewType == NORMAL){
            view = LayoutInflater.from(activity).inflate(R.layout.item_stored_house, parent, false);
            viewHolder = new ViewHolderNormal(view);
        }else {

            view = LayoutInflater.from(activity).inflate(R.layout.item_stored_reinspect_house, parent, false);
            viewHolder = new ViewHolderReinspect(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HouseKeyDataModel nonSubHomeListModel = nonSubHomeList.get(position);
        if(viewHolder instanceof ViewHolderNormal) {
            ViewHolderNormal holder = (ViewHolderNormal) viewHolder;
            DemographicDetails demographicDetails = nonSubHomeListModel.getDemographicDetails();
            holder.mainLayout.setBackgroundColor(activity.getResources().getColor(getColorRes(position)));
            holder.mainLayout.setTag(nonSubHomeList.get(position));
            holder.province.setText(demographicDetails.getProvince());
            holder.township.setText(demographicDetails.getTownship());
            holder.ward.setText(demographicDetails.getWard());
            holder.extension.setText(demographicDetails.getExtension());
            holder.hNumber.setText(demographicDetails.getHouseNumber());
            holder.mainLayout.setOnClickListener(this);
        }else {
            ViewHolderReinspect holder = (ViewHolderReinspect) viewHolder;
            DemographicDetails demographicDetails = nonSubHomeListModel.getDemographicDetails();
            holder.mainLayout.setBackgroundColor(activity.getResources().getColor(getColorRes(position)));
            holder.expectedDate.setText("Expected Inspection Date : "
                    + DateUtil.convertDateString(nonSubHomeListModel.getExpectedDate(),"yyyy-MM-dd", "dd-MM-yyyy"));
            holder.mainLayout.setTag(nonSubHomeList.get(position));
            holder.province.setText(demographicDetails.getProvince());
            holder.township.setText(demographicDetails.getTownship());
            holder.ward.setText(demographicDetails.getWard());
            holder.extension.setText(demographicDetails.getExtension());
            holder.hNumber.setText(demographicDetails.getHouseNumber());
            holder.mainLayout.setOnClickListener(this);
        }
    }

    public int getColorRes(int position) {
        if (position % 2 == 0)
            return R.color.gridYellowColor;
        return R.color.whiteColor;
    }
    @Override
    public int getItemCount() {
        return nonSubHomeList.size();
    }

    @Override
    public int getItemViewType(int position){
        HouseKeyDataModel nonSubHomeListModel = nonSubHomeList.get(position);
        if(nonSubHomeListModel.getExpectedDate() != null)
            return REINSPECT;
        return NORMAL;
    }

    @Override
    public void onClick(View view) {
        HouseKeyDataModel nonSubHomeListModel= (HouseKeyDataModel) view.getTag();
        if(listener != null)
            listener.onHouseKeyOpen(nonSubHomeListModel);
    }

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        View mainLayout;
        TextView province, township, ward, extension, hNumber;
        public ViewHolderNormal(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.layoutMain);
            province = (TextView) view.findViewById(R.id.textView_houseListProvince);
            township = (TextView) view.findViewById(R.id.textView_houseListTownShip);
            ward = (TextView) view.findViewById(R.id.textView_houseListWard);
            extension = (TextView) view.findViewById(R.id.textView_houseListExtension);
            hNumber = (TextView) view.findViewById(R.id.textView_houseListHouseNumber);
        }
    }

    public interface OnHouseClick{
        void onHouseKeyOpen(HouseKeyDataModel houseKey);
    }

    private class ViewHolderReinspect extends RecyclerView.ViewHolder {
        View mainLayout;
        TextView province, township, ward, extension, hNumber, expectedDate;
        public ViewHolderReinspect(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.layoutMain);
            province = (TextView) view.findViewById(R.id.textView_houseListProvince);
            township = (TextView) view.findViewById(R.id.textView_houseListTownShip);
            ward = (TextView) view.findViewById(R.id.textView_houseListWard);
            extension = (TextView) view.findViewById(R.id.textView_houseListExtension);
            hNumber = (TextView) view.findViewById(R.id.textView_houseListHouseNumber);
            expectedDate = (TextView) view.findViewById(R.id.textView_reinspectDate);
        }
    }
}

