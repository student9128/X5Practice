package com.practice.kevin.x5practice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        FileListAdapter.OnRecyclerItemClickListener {
    @BindView(R.id.rv_recycler_view)
    RecyclerView rvRecyclerView;
    @BindView(R.id.pb_progress_bar)
    ProgressBar pbProgressBar;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.btn_scan)
    Button btnScan;
    @BindView(R.id.tv_current_size)
    TextView tvCurrentSize;
    @BindView(R.id.tv_current_file)
    TextView tvCurrentFile;
    @BindView(R.id.btn_get_contact)
    Button btnGetContact;
    private String TAG = getClass().getSimpleName();
    private List<FileInfoBean> data = new ArrayList<>();
    private List<FileInfoBean> fileInfoData = new ArrayList<>();
    private FileListAdapter mAdapter;
    private static final int UPDATE = 100;
    private LoadingDialog mDialog;
    private int length;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new FileListAdapter(this, data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        decoration.setDivider(R.drawable.bg_recycler_divider);
        rvRecyclerView.setLayoutManager(linearLayoutManager);
        rvRecyclerView.addItemDecoration(decoration);
        rvRecyclerView.setAdapter(mAdapter);
        rvRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        pbProgressBar.setVisibility(View.GONE);
        mDialog = new LoadingDialog();

        mAdapter.setOnRecyclerItemClickListener(this);
        btnScan.setOnClickListener(this);
        btnGetContact.setOnClickListener(this);

//
//        if (mDialog != null && !mDialog.isAdded()) {
//
//        mDialog.show(getSupportFragmentManager(),"Loading");
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        File file = Environment.getExternalStorageDirectory();
//        fileInfoData.clear();
        switch (item.getItemId()) {
            case R.id.action_show_word:
//                FileUtil.mapMultiFile(file, fileInfoData, ".doc", ".docx");
//                mAdapter.updateData(fileInfoData);
                new AsyncWordFile().execute();
                break;
            case R.id.action_show_ppt:
//                FileUtil.mapPPTFile(file, fileInfoData);
//                mAdapter.updateData(fileInfoData);
                new AsyncPPTFile().execute();
                break;
            case R.id.action_show_excel:
//                FileUtil.mapMultiFile(file, fileInfoData, ".xls", ".xlsx");
//                mAdapter.updateData(fileInfoData);
                new AsyncExcelFile().execute();
                break;
            case R.id.action_show_pdf:
//                FileUtil.mapMultiFile(file, fileInfoData, ".pdf");
//                mAdapter.updateData(fileInfoData);
                new AsyncPdfFile().execute();
                break;
            case R.id.action_show_txt:
//                FileUtil.mapMultiFile(file, fileInfoData, ".txt");
//                mAdapter.updateData(fileInfoData);
                new AsyncTxtFile().execute();
                break;
            default:
                break;
        }

        return true;
    }

    public void mapFile(File dir, List<FileInfoBean> data) {
        length = 0;
        index = 0;
        final File[] files = dir.listFiles();
        length = files.length;

        if (files == null) return;
//        for (File file : files) {

//            if (file.isDirectory() && !file.isHidden()) {
//                mapFile(file, data);
//            } else {
//                if (ifAll(file)) {
//                    Log.d("FileUtil.class", file.getName());
//                    FileInfoBean fileInfoBean = new FileInfoBean();
//                    fileInfoBean.setFileName(file.getName());
//                    fileInfoBean.setFilePath(file.getAbsolutePath());
//                    data.add(fileInfoBean);
//                }
//            }
//        }
        for (int i = 0; i < files.length; i++) {
            index++;
            if (files[i].isDirectory() && !files[i].isHidden()) {
                mapFile(files[i], data);
            } else {
                if (ifAll(files[i])) {
                    FileInfoBean fileInfoBean = new FileInfoBean();
                    fileInfoBean.setFileName(files[i].getName());
                    fileInfoBean.setFilePath(files[i].getAbsolutePath());
                    data.add(0, fileInfoBean);
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCurrentSize.setText("长度：" + length + "\t索引" + index + "\t集合数量" +
                                    fileInfoData.size());
                            tvCurrentFile.setText(files[finalI].getName());
                            mAdapter.updateData(fileInfoData);
                        }
                    });
                }
            }
        }
    }

    private boolean ifAll(File file) {
        return ifType(file, ".doc") || ifType(file, ".docx") || ifType(file, ".ppt") || ifType
                (file, ".pptx") || ifType(file, ".xls") || ifType(file, ".xlsx") || ifType(file,
                ".txt") || ifType(file, ".pdf");
    }

    private boolean ifType(File file, String extension) {
        return file.getName().endsWith(extension);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
//                File file = Environment.getExternalStorageDirectory();
//                fileInfoData.clear();
//                mapFile(file, fileInfoData);
//                mAdapter.updateData(fileInfoData);

                new Thread() {
                    @Override
                    public void run() {

                        File file = Environment.getExternalStorageDirectory();
                        fileInfoData.clear();
                        mapFile(file, fileInfoData);
//                        mAdapter.updateData(fileInfoData);

                    }
                }.start();
//                new AsyncGetFile().execute();
//                Timer t = new Timer();
//                TimerTask tt = new TimerTask() {
//                    @Override
//                    public void run() {
//
//                    }
//                };
                break;
            case R.id.btn_get_contact:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission
                        .READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest
                            .permission.READ_CONTACTS)) {

                    } else {

                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.READ_CONTACTS},
                                1);
                    }
                } else {
                getContactInfo();
                }
                break;
        }

    }

    @Override
    public void onRecyclerItemClick(int position) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("path", fileInfoData.get(position).getFilePath());
        startActivity(intent);
    }

    class AsyncGetFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            mapFile(file, fileInfoData);
            publishProgress(String.valueOf(length), String.valueOf(index));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for (String x : values) {
                Log.w("MainProgress", "values:==" + x);
            }
        }
    }

    class AsyncWordFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            FileUtil.mapMultiFile(file, fileInfoData, ".doc", ".docx");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }
    }

    class AsyncPPTFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            FileUtil.mapMultiFile(file, fileInfoData, ".ppt", ".pptx");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }
    }

    class AsyncExcelFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            FileUtil.mapMultiFile(file, fileInfoData, ".xls", ".xlsx");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }
    }

    class AsyncPdfFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            FileUtil.mapMultiFile(file, fileInfoData, ".pdf");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }
    }

    class AsyncTxtFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fileInfoData.clear();
            if (mDialog != null && !mDialog.isAdded()) {
                mDialog.show(getSupportFragmentManager(), "Loading");
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            File file = Environment.getExternalStorageDirectory();
            FileUtil.mapMultiFile(file, fileInfoData, ".txt");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdapter.updateData(fileInfoData);
            if (fileInfoData == null || fileInfoData.size() == 0) {
                Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mDialog.isAdded()) {
                mDialog.dismiss();
            }
        }
    }

    private void getContactInfo() {
        Cursor cursor = getContentResolver().query(Data.CONTENT_URI, null, null,
                null,
                Data.RAW_CONTACT_ID);
        while (cursor.moveToNext()) {
            int contactId = cursor.getInt(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
            //mimetype类型
            String mimeType = cursor.getString(cursor.getColumnIndex(Data.MIMETYPE));
            if (StructuredName.CONTENT_ITEM_TYPE.equals(mimeType)) {
                String prefix = getContent(cursor, StructuredName.PREFIX);
                printLogi("prefix:\t" + prefix);
                String familyName = getContent(cursor, StructuredName.FAMILY_NAME);
                printLogi("familyName:\t" + familyName);
                String middleName = getContent(cursor, StructuredName.MIDDLE_NAME);
                printLogi("middleName:\t" + middleName);
                String givenName = getContent(cursor, StructuredName.GIVEN_NAME);
                printLogi("givenName:\t" + givenName);
                String suffix = getContent(cursor, StructuredName.SUFFIX);
                printLogi("suffix:\t" + suffix);
                String phoneticFamilyName = getContent(cursor, StructuredName.PHONETIC_FAMILY_NAME);
                printLogi("phoneticFamilyName:\t" + phoneticFamilyName);
                String phoneticMiddleName = getContent(cursor, StructuredName.PHONETIC_MIDDLE_NAME);
                printLogi("phoneticMiddleName:\t" + phoneticMiddleName);
                String phoneticGivenName = getContent(cursor, StructuredName.PHONETIC_GIVEN_NAME);
                printLogi("phoneticGivenName:\t" + phoneticGivenName);
            }
            if (Phone.CONTENT_ITEM_TYPE.equals(mimeType)) {
                int phoneType = cursor.getInt(cursor.getColumnIndex(
                        Phone.TYPE));
                if (phoneType == Phone.TYPE_MOBILE) {
                    String mobileNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("mobileNumber:\t" + mobileNumber);
                }
                if (phoneType == Phone.TYPE_HOME) {
                    String homeNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("homeNumber:\t" + homeNumber);
                }
                if (phoneType == Phone.TYPE_WORK) {
                    String workNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("workNumber:\t" + workNumber);
                }
                if (phoneType == Phone.TYPE_WORK_MOBILE) {
                    String workMobileNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("workMobileNumber:\t" + workMobileNumber);
                }
                if (phoneType == Phone.TYPE_FAX_HOME) {
                    String faxHomeNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("faxHomeNumber:\t" + faxHomeNumber);
                }
                if (phoneType == Phone.TYPE_FAX_WORK) {
                    String faxWorkNumber = getContent(cursor, Phone.NUMBER);
                    printLogi("faxWorkNumber:\t" + faxWorkNumber);
                }
                if (phoneType == Phone.TYPE_ASSISTANT) {
                    String assistantNum = getContent(cursor, Phone.NUMBER);
                    printLogi("assistantNum:\t" + assistantNum);
                }

            }
        }

    }

    private String getContent(Cursor c, String typeName) {
        return c.getString(c.getColumnIndex(typeName));
    }


    public void printLoge(String str) {
        LogK.e(TAG, str);
    }

    public void printLogd(String str) {
        LogK.d(TAG, str);
    }

    public void printLogi(String str) {
        LogK.i(TAG, str);
    }

    public void printLogv(String str) {
        LogK.v(TAG, str);
    }

    public void printLogw(String str) {
        LogK.w(TAG, str);
    }

}
