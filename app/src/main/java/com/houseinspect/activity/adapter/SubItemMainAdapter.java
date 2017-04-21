package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.util.DateUtil;

/**
 * Created by Lalit on 11/8/2016.
 */
public class SubItemMainAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final int NORMAL = 0;
    private static final int DONE = 1, SUBMIT = 2, COMPLETION_DATE = 3;
    private final String[] subsidyTagList;
    private final Activity activity;
    private final ItemClickListener listener;
    private final HouseDataModel houseDataModel;

    public SubItemMainAdapter(Activity activity, String[] subsidyTagList,
                              ItemClickListener listener, HouseDataModel houseDataModel) {
        this.subsidyTagList = subsidyTagList;
        this.activity = activity;
        this.listener = listener;
        this.houseDataModel = houseDataModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case NORMAL:
                view = LayoutInflater.from(activity).inflate(R.layout.item_enroll_normal, parent, false);
                viewHolder = new ViewHolderNormal(view);
                break;
            case DONE:
                view = LayoutInflater.from(activity).inflate(R.layout.item_enroll_done, parent, false);
                viewHolder = new ViewHolderDone(view);
                break;
            case SUBMIT:
                view = LayoutInflater.from(activity).inflate(R.layout.item_submit, parent, false);
                viewHolder = new ViewHolderSubmit(view);
                break;
            case COMPLETION_DATE:
                view = LayoutInflater.from(activity).inflate(R.layout.item_completion_date, parent, false);
                viewHolder = new ViewHolderCompletion(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String listItemStr = subsidyTagList[position];
        if (position == 10 || listItemStr.equalsIgnoreCase("Practically Completed On")) {
            ViewHolderCompletion completion = (ViewHolderCompletion) viewHolder;
            String enrollTag = subsidyTagList[position];
            completion.enrollTag.setText(enrollTag);
            String text = "";
            if(houseDataModel!= null) {
                if (houseDataModel.getInspectorCompletionDate() != null) {
                    text = "Inspector :" + DateUtil.getDateStrFromTimeStamp(houseDataModel.getInspectorCompletionDate());
                }
                if (houseDataModel.getSeniorInspectorCompletionDate() != null) {
                    if (text != null)
                        text = text + "\n";
                    text = text + "Senior Inspector :" + DateUtil.getDateStrFromTimeStamp(houseDataModel.getSeniorInspectorCompletionDate());
                }
                if (houseDataModel.getReInspectorCompletionDate() != null) {
                    if (text != null)
                        text = text + "\n";
                    text = text + "Re inspection Completion:" + DateUtil.getDateStrFromTimeStamp(houseDataModel.getReInspectorCompletionDate());
                }
            }
            if (text.length() > 0)
                completion.updatedOn.setText(text);
            else completion.updatedOn.setText("No completion Date Available!");
            completion.mainLayout.setTag(position);
            completion.mainLayout.setOnClickListener(this);
        } else if (position == 11 || listItemStr.equalsIgnoreCase("submit")) {
            ViewHolderSubmit holderSubmit = (ViewHolderSubmit) viewHolder;
            holderSubmit.submitButton.setTag(position);
            holderSubmit.submitButton.setOnClickListener(this);
        } else if (viewHolder instanceof ViewHolderNormal) {
            ViewHolderNormal holder = (ViewHolderNormal) viewHolder;
            String enrollTag = subsidyTagList[position];
            holder.enrollTag.setText(enrollTag);
            holder.mainLayout.setTag(position);
            holder.mainLayout.setOnClickListener(this);
        } else {
            bindDoneView((ViewHolderDone) viewHolder, position);
        }
    }

    private void bindDoneView(ViewHolderDone holder, int position) {
        String updatedOn = "";
        switch (position) {
            case 0:
                updatedOn = houseDataModel.getDemographicDetails().getUpdatedOn();
                break;
            case 1:
                updatedOn = houseDataModel.getHomeOwnerInfo().getUpdatedOn();
                break;
            case 2:
                updatedOn = houseDataModel.getSubStructure().getUpdatedOn();
                break;
            case 3:
                updatedOn = houseDataModel.getSuperStructure().getUpdatedOn();
                break;
            case 4:
                updatedOn = houseDataModel.getPracticalCompletion().getUpdatedOn();
                break;
            case 5:
                updatedOn = houseDataModel.getStormWater().getUpdatedOn();
                break;
            case 6:
                updatedOn = houseDataModel.getCarpentry().getUpdatedOn();
                break;
            case 7:
                updatedOn = houseDataModel.getPlumbing().getUpdatedOn();
                break;
            case 8:
                updatedOn = houseDataModel.getElectrical().getUpdatedOn();
                break;
            case 9:
                updatedOn = houseDataModel.getWaterProofing().getUpdatedOn();
                break;
        }
        String enrollTag = subsidyTagList[position];
        holder.enrollTag.setText(enrollTag);
        holder.updatedOn.setText("Updated: " + DateUtil.getDateStrFromTimeStamp(updatedOn));
        holder.mainLayout.setTag(position);
        holder.mainLayout.setOnClickListener(this);
    }

    @Override
    public int getItemViewType(int position) {
        String listItemStr = subsidyTagList[position];
        if (position == 11 || listItemStr.equalsIgnoreCase("submit"))
            return SUBMIT;
        if (listItemStr.equalsIgnoreCase("Practically Completed On"))
            return COMPLETION_DATE;
        if (houseDataModel == null) {
            return NORMAL;
        } else {
            switch (position) {
                case 0:
                    if (houseDataModel.getDemographicDetails() == null)
                        return NORMAL;
                    else
                        return DONE;
                case 1:
                    if (houseDataModel.getHomeOwnerInfo() == null)
                        return NORMAL;
                    else
                        return DONE;
                case 2:
                    if (houseDataModel.getSubStructure() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 3:
                    if (houseDataModel.getSuperStructure() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 4:
                    if (houseDataModel.getPracticalCompletion() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 5:
                    if (houseDataModel.getStormWater() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 6:
                    if (houseDataModel.getCarpentry() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 7:
                    if (houseDataModel.getPlumbing() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 8:
                    if (houseDataModel.getElectrical() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 9:
                    if (houseDataModel.getWaterProofing() != null) {
                        return DONE;
                    } else {
                        return NORMAL;
                    }
                case 10:
                    return COMPLETION_DATE;
                case 11:
                    return SUBMIT;

            }
        }
        return NORMAL;
    }

    @Override
    public int getItemCount() {
        return subsidyTagList.length;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        listener.onAdapterItemClick(position);
    }

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        TextView enrollTag;
        View mainLayout;

        public ViewHolderNormal(View view) {
            super(view);
            enrollTag = (TextView) view.findViewById(R.id.textView_enrollName);
            mainLayout = view.findViewById(R.id.layoutMain);
        }
    }

    public interface ItemClickListener {
        void onAdapterItemClick(int position);
    }

    private class ViewHolderDone extends RecyclerView.ViewHolder {
        TextView enrollTag, updatedOn;
        View mainLayout;

        public ViewHolderDone(View view) {
            super(view);
            enrollTag = (TextView) view.findViewById(R.id.textView_enrollName);
            updatedOn = (TextView) view.findViewById(R.id.textView_enrollUpdated);
            mainLayout = view.findViewById(R.id.layoutMain);
        }
    }

    private class ViewHolderSubmit extends RecyclerView.ViewHolder {
        View submitButton;

        public ViewHolderSubmit(View view) {
            super(view);
            submitButton = view.findViewById(R.id.button_submit);
        }
    }

    private class ViewHolderCompletion extends RecyclerView.ViewHolder {
        TextView enrollTag, updatedOn;
        View mainLayout;

        public ViewHolderCompletion(View view) {
            super(view);
            enrollTag = (TextView) view.findViewById(R.id.textView_enrollName);
            updatedOn = (TextView) view.findViewById(R.id.textView_enrollUpdated);
            mainLayout = view.findViewById(R.id.layoutMain);
        }
    }

}
