package com.practice.kevin.x5practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/28.
 * <h3>
 * Describe:
 * <h3/>
 */
public class ShowActivity extends AppCompatActivity implements OfficeView.OnFetchPathListener {
    @BindView(R.id.ov_office_view)
    OfficeView ovOfficeView;
    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        ovOfficeView.setOnFetchPathListener(this);
        Intent intent = getIntent();
        filePath = intent.getStringExtra("path");
//        if (!TextUtils.isEmpty(filePath)) {
        ovOfficeView.addListener();
//
//        }


    }

    @Override
    public void onFetchPath(OfficeView officeView) {
        getFilePathAndShow(ovOfficeView);
    }

    private void getFilePathAndShow(OfficeView ovOfficeView) {
        if (!TextUtils.isEmpty(filePath)) {
        ovOfficeView.showFile(new File(filePath));//本地的文件
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ovOfficeView != null) {
            ovOfficeView.onStopShow();
        }
    }
}
