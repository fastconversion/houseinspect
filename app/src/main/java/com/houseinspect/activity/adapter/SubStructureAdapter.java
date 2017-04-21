package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.activity.controller.OldDataAvailabilityCheck;
import com.houseinspect.model.supportModel.InspectionParameter;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.util.DateUtil;
import com.houseinspect.util.MyConvertor;

import java.util.ArrayList;

/**
 * Created by Lalit on 11/10/2016.
 */
public class SubStructureAdapter extends RecyclerView.Adapter implements View.OnClickListener {


    private final Activity activity;
    private final ArrayList<String> subItemList;
    private final AdapterClickListener listener;
    private final HouseDataModel houseDataModel;
    private final String mainForm;
    private final int NORMAL = 0, DONE = 1;

    public SubStructureAdapter(Activity activity, ArrayList<String> subItemList,
                               AdapterClickListener listener,
                               HouseDataModel houseDataModel, String mainForm) {
        this.activity = activity;
        this.subItemList = subItemList;
        this.listener = listener;
        this.houseDataModel = houseDataModel;
        this.mainForm = mainForm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case NORMAL:
                view = LayoutInflater.from(activity).inflate(R.layout.item_sub_list_normal, parent, false);
                viewHolder = new ViewHolderNormal(view);
                break;
            case DONE:
                view = LayoutInflater.from(activity).inflate(R.layout.item_sub_list_updated, parent, false);
                viewHolder = new ViewHolderUpdated(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolderNormal) {
            ViewHolderNormal holder = (ViewHolderNormal) viewHolder;
            String itemTag = subItemList.get(position);
            holder.itemName.setText(itemTag);
            holder.mainView.setTag(position);
            holder.mainView.setBackgroundColor(activity.getResources().getColor(getColorRes(position)));
            holder.mainView.setOnClickListener(this);
        } else {
            buildUpdated((ViewHolderUpdated) viewHolder, position);
        }
    }

    private void buildUpdated(ViewHolderUpdated holder, int position) {
        String itemTag = subItemList.get(position);
        InspectionParameter inspectionParameter
                = OldDataAvailabilityCheck.getInspectionParameter(houseDataModel, mainForm,
                MyConvertor.getTitle(itemTag));
        holder.itemName.setText(itemTag);
        holder.mainView.setTag(position);
        holder.updatedOn.setText("Updated: " + DateUtil.getDateStrFromTimeStamp
                (inspectionParameter.getInspectionUpdatedOn()));
        holder.mainView.setBackgroundColor(activity.getResources().getColor(getColorRes(position)));
        holder.mainView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        String title = subItemList.get(position);
        listener.onAdapterItemClick(title);
    }

    public interface AdapterClickListener {
        void onAdapterItemClick(String title);
    }

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        TextView itemName;
        View mainView;

        public ViewHolderNormal(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.textView_sub_item_name);
            mainView = view.findViewById(R.id.mainLayout);
        }
    }

    public int getColorRes(int position) {
        if (position % 2 == 0)
            return R.color.whiteColor;
        return R.color.gridYellowColor;
    }

    @Override
    public int getItemViewType(int position) {
        String itemTag = subItemList.get(position);
        InspectionParameter inspectionParameter
                = OldDataAvailabilityCheck.getInspectionParameter(houseDataModel, mainForm,
                MyConvertor.getTitle(itemTag).trim());
        if (inspectionParameter == null)
            return NORMAL;
        return DONE;
    }

    private class ViewHolderUpdated extends RecyclerView.ViewHolder {
        TextView itemName, updatedOn;
        View mainView;

        public ViewHolderUpdated(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.textView_sub_item_name);
            updatedOn = (TextView) view.findViewById(R.id.textView_sub_item_updated);
            mainView = view.findViewById(R.id.mainLayout);
        }
    }
}
