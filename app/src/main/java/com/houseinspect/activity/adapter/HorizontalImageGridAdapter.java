package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.houseinspect.R;
import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.controller.DemographicController;
import com.houseinspect.activity.subItemActivity.formActivity.form.base.FormBaseActivity;
import com.houseinspect.model.supportModel.ImageData;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.util.Constants;
import com.houseinspect.util.ImageUtil;

import java.util.List;

/**
 * Created by Lalit on 11/10/2016.
 */
public class HorizontalImageGridAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final Activity activity;
    private final List<ImageData> standImageList;
    private final DemographicController controller;
    private final FormBaseActivity formBaseActivity;
    private final RegisterData registerData;

    public HorizontalImageGridAdapter(Activity activity, List<ImageData> standImageList,
                                      DemographicController controller, FormBaseActivity formBaseActivity) {
        this.activity = activity;
        this.standImageList = standImageList;
        this.controller = controller;
        this.formBaseActivity = formBaseActivity;
        this.registerData = (new DataController(activity)).getUserData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(activity).inflate(R.layout.item_image_grid, parent, false);
        viewHolder = new ViewHolderNormal(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderNormal holder = (ViewHolderNormal) viewHolder;
        ImageData imageData = standImageList.get(position);
        if(imageData.getServerImageUrl() != null && imageData.getServerImageUrl().length()>0){
            Glide.with(activity)
                    .load(Constants.BASE_URL + standImageList.get(position).getServerImageUrl())
                    .into(holder.imageGrid);
        }else {
            Glide.with(activity)
                    .load(ImageUtil.getBitmapFromBase64(imageData.getBase64()))
                    .into(holder.imageGrid);
        }
        if(imageData.getSubmittedBy()== null){
            holder.removeImageGrid.setTag(imageData);
            holder.removeImageGrid.setOnClickListener(this);
            holder.removeImageGrid.setVisibility(View.VISIBLE);
        }
        else if(imageData.getSubmittedBy().getUserId().equalsIgnoreCase(registerData.getUserId())){
            holder.removeImageGrid.setTag(imageData);
            holder.removeImageGrid.setOnClickListener(this);
            holder.removeImageGrid.setVisibility(View.VISIBLE);
        }else {
            holder.removeImageGrid.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return standImageList.size();
    }

    @Override
    public void onClick(View view) {
        ImageData imagePath = (ImageData) view.getTag();
        if (controller != null) {
            controller.removeStandImage(imagePath);
            controller.notifyAdapter();
        } else if (formBaseActivity != null) {
            formBaseActivity.removeStandImage(imagePath);
            formBaseActivity.notifyAdapter();
        }
    }

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        ImageView imageGrid, removeImageGrid;

        public ViewHolderNormal(View view) {
            super(view);
            imageGrid = (ImageView) view.findViewById(R.id.imageView_imageGrid_image);
            removeImageGrid = (ImageView) view.findViewById(R.id.imageView_removeImage);
        }
    }
}
