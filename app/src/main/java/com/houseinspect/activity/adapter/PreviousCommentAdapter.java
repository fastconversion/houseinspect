package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.model.FormModel.InspectionComment;
import com.houseinspect.util.DateUtil;

import java.util.List;

/**
 * Created by Lalit on 12/7/2016.
 */
public class PreviousCommentAdapter  extends RecyclerView.Adapter{

    private final Activity activity;
    private final List<InspectionComment> inspectionCommentList;


    public PreviousCommentAdapter(Activity activity, List<InspectionComment> inspectionCommentList) {
        this.inspectionCommentList = inspectionCommentList;
        this.activity =  activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(activity).inflate(R.layout.item_comment, parent, false);
        viewHolder = new ViewHolderNormal(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderNormal holder = (ViewHolderNormal) viewHolder;
        InspectionComment comment = inspectionCommentList.get(position);
        holder.commentTime.setText(DateUtil.getDateStrFromTimeStamp(comment.getCommentDate()));
        holder.comment.setText(comment.getComment());
        holder.commentUser.setText(comment.getCommentUser().getUserName());
    }

    @Override
    public int getItemCount() {
        return inspectionCommentList.size();
    }

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        TextView comment, commentUser, commentTime;
        public ViewHolderNormal(View view) {
            super(view);
            comment = (TextView) view.findViewById(R.id.textView_comment);
            commentUser = (TextView) view.findViewById(R.id.textView_commentUserName);
            commentTime= (TextView) view.findViewById(R.id.textView_commentDateTime);

        }
    }
}