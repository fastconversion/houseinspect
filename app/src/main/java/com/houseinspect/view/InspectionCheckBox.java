package com.houseinspect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.houseinspect.R;

public class InspectionCheckBox extends LinearLayout{

    private final View mainView;
    private String mExampleString;

    public InspectionCheckBox(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mainView = inflater.inflate(R.layout.view_inspection_checkbox, this);
        init(null, 0);
    }


    public InspectionCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        mainView = inflater.inflate(R.layout.view_inspection_checkbox, this);
        init(attrs,0);
    }

    public InspectionCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        mainView = inflater.inflate(R.layout.view_inspection_checkbox, this);
        init(attrs,0);
    }


    private void init(AttributeSet attrs, int defStyle) {
        if(attrs!= null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.InspectionCheckBox, defStyle, 0);
             mExampleString = a.getString(R.styleable.InspectionCheckBox_titleInspection);
            a.recycle();
        }
        ((TextView) mainView.findViewById(R.id.textView_inspectionParameterName)).setText(mExampleString);
    }

    public void setUnchecked(){
        mainView.findViewById(R.id.imageView_InspectionFrontCheck).setVisibility(INVISIBLE);
        ((ImageView) mainView.findViewById(R.id.imageView_InspectionBackCheck))
                .setImageResource(R.drawable.img_black_border);
    }


    public void setChecked() {
        mainView.findViewById(R.id.imageView_InspectionFrontCheck).setVisibility(VISIBLE);
        ((ImageView) mainView.findViewById(R.id.imageView_InspectionBackCheck))
                .setImageResource(R.drawable.img_primary_border);
    }

    public String getText(){
        return ((TextView) mainView.findViewById(R.id.textView_inspectionParameterName)).getText().toString();
    }

}
