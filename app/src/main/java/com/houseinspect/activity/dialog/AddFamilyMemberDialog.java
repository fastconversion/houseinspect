package com.houseinspect.activity.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.houseinspect.R;
import com.houseinspect.model.supportModel.FamilyMember;

/**
 * Created by Lalit on 11/9/2016.
 */
public class AddFamilyMemberDialog implements View.OnClickListener {

    private final AlertDialog dialog;
    private final EditText memberName;
    private final EditText memberAge;
    private final Activity context;
    private OnFamilyMemberAdded listener;

    @Override
    public void onClick(View view) {
        if(memberAge.getText().toString().length()==0){
            Toast.makeText(context,"Please fill member Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(memberAge.getText().toString().length()==0){
            Toast.makeText(context,"Please fill member Age", Toast.LENGTH_SHORT).show();
            return;
        }
        FamilyMember familyMember = new FamilyMember();
        familyMember.setMemberName(memberName.getText().toString());
        familyMember.setMemberAge(memberAge.getText().toString());
        listener.onNewMemberAdded(familyMember);
        dialog.dismiss();

    }

    public interface OnFamilyMemberAdded{
        void onNewMemberAdded(FamilyMember familyMember);
    }

    public AddFamilyMemberDialog(Activity context, OnFamilyMemberAdded listener){
        this.context =  context;
        this.listener = listener;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_family_member, null);
        dialogBuilder.setView(dialogView);
        memberName = (EditText) dialogView.findViewById(R.id.editText_familyMemberName);
        memberAge = (EditText) dialogView.findViewById(R.id.editText_editText_familyMemberAge);
        dialogView.findViewById(R.id.button_addMember).setOnClickListener(this);
        dialog = dialogBuilder.create();
    }

    public void show(){
        dialog.show();
    }
}
