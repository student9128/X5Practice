package com.practice.kevin.x5practice;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/28.
 * <h3>
 * Describe:
 * <h3/>
 */
public class OfficeView extends FrameLayout implements TbsReaderView.ReaderCallback{
    private static String TAG = "OfficeView.class";
    private TbsReaderView tbsReaderView;
    private int saveTime = -1;
    private Context context;
    public OfficeView(@NonNull Context context) {
        this(context,null);
    }

    public OfficeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OfficeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tbsReaderView = new TbsReaderView(context, this);
        this.addView(tbsReaderView,new LinearLayout.LayoutParams(-1,-1));
        this.context = context;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e(TAG, "onCallBackAction: "+integer );
    }

    public void showFile(File f){
        if (f != null && !TextUtils.isEmpty(f.toString())) {
            String readerTemp = "/storage/emulated/0/TbsReaderTemp";
            File file = new File(readerTemp);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                if (!mkdir) {
                    Log.e(TAG, "showFile: readerTemp创建失败");
                }
            }
            Bundle bundle = new Bundle();
            bundle.putString("filePath", f.toString());
            bundle.putString("tempPath", Environment.getExternalStorageState() + "/TbsReaderTemp");
            if (tbsReaderView == null) {
                tbsReaderView = new TbsReaderView(context, this);
            }
            boolean preOpen = tbsReaderView.preOpen(getFileType(f.toString()), false);
            if (preOpen) {
                tbsReaderView.openFile(bundle);
            } else {
                Toast.makeText(context,"打不开",Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "showFile: 文件无效");
        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null");
            return str;
        }
        Log.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        Log.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }

    public void addListener() {
        if (listener != null) {
            listener.onFetchPath(this);
        } else {
            throw new NullPointerException("OnFetchPathListener is null");
        }
    }

    public void onStopShow() {
        if (tbsReaderView != null) {
            tbsReaderView.onStop();
        }
    }

    private OnFetchPathListener listener;
    public interface  OnFetchPathListener{
        void onFetchPath(OfficeView officeView);
    }
    public void setOnFetchPathListener(OnFetchPathListener l){
        this.listener = l;
    }

}
