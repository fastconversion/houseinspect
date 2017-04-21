package com.houseinspect.activity.subItemActivity.formActivity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.houseinspect.R;
import com.houseinspect.activity.adapter.PreviousCommentAdapter;
import com.houseinspect.model.FormModel.InspectionComment;

import java.util.List;

public class PreviousCommentActivity extends AppCompatActivity {

    public static final String INSPECTION_COMMENTS = "inspection_comment";
    private List<InspectionComment> inspectionCommentList;
    private PreviousCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_previous_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String inspectionCommentListStr = getIntent().getStringExtra(INSPECTION_COMMENTS);
        inspectionCommentList = (new Gson()).fromJson(inspectionCommentListStr,
                new TypeToken<List<InspectionComment>>(){}.getType());
        setTitle("Previous Comments");
        initRecyclerView();
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

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new PreviousCommentAdapter(this, inspectionCommentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}
