package com.practice.kevin.x5practice.filescan;

import android.os.AsyncTask;

import com.practice.kevin.x5practice.FileInfoBean;
import com.practice.kevin.x5practice.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/30.
 * <h3>
 * Describe:
 * <h3/>
 */
public class FileScanTask extends AsyncTask<String, String, String> {
    private File file;
    private List<FileInfoBean> fileInfoData;
    private FileScanListener mListener;


    public FileScanTask(File file, List<FileInfoBean> fileInfoData, FileScanListener mListener) {
        this.file = file;
        this.fileInfoData = fileInfoData;
        this.mListener = mListener;
    }

    public FileScanTask(FileScanListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onBegin();
    }

    @Override
    protected String doInBackground(String... strings) {

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        mListener.onProgressing();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onFinish();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mListener.onCancelled();
    }
}
