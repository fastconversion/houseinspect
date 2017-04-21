package com.houseinspect.activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houseinspect.R;
import com.houseinspect.model.supportModel.FamilyMember;

import java.util.List;

/**
 * Created by Lalit on 11/9/2016.
 */
public class FamilyMemberAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final List<FamilyMember> familyMemberList;
    private final Activity activity;
    private final OnRemoveMemberListener listener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(activity).inflate(R.layout.item_family_member, parent, false);
        viewHolder = new ViewHolderFamily(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderFamily holder = (ViewHolderFamily) viewHolder;
        FamilyMember familyMember= familyMemberList.get(position);
        holder.memberName.setText((position+1)+". "+familyMember.getMemberName()+"("+familyMember.getMemberAge()+")");
        holder.remove.setTag(familyMember);
        holder.remove.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return familyMemberList.size();
    }

    @Override
    public void onClick(View view) {
        FamilyMember familyMember = (FamilyMember) view.getTag();
        listener.onRemoveMember(familyMember);
    }

    public interface OnRemoveMemberListener{
        void onRemoveMember(FamilyMember familyMember);
    }

    public FamilyMemberAdapter(Activity activity, List<FamilyMember> familyMember, OnRemoveMemberListener listener) {
        this.familyMemberList = familyMember;
        this.activity =  activity;
        this.listener =  listener;
    }

    private class ViewHolderFamily extends RecyclerView.ViewHolder {
        TextView memberName, remove;
        public ViewHolderFamily(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.textView_member);
            remove = (TextView) view.findViewById(R.id.textView_removeMember);

        }
    }
}
