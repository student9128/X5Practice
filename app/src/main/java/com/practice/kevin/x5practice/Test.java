package com.practice.kevin.x5practice;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/29.
 * <h3>
 * Describe:
 * <h3/>
 */
    import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

    public class Test extends AppCompatActivity {

        private ArrayList<File> scanedFiles = new ArrayList<>();

        /*扫描线程*/
        private Thread scanThread;
        /*定时器  用于定时检测扫描线程的状态*/
        private Timer scanTimer;

        /*检测扫描线程的任务*/
        private TimerTask scanTask;
//        private FileAdapter adapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            initWidget();

            startScan();
        }


        /*初始化控件*/
        private void initWidget() {

//            recy.setLayoutManager(new LinearLayoutManager(this));
//
//            toolbar.setTitle("扫描文件");
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//            adapter = new FileAdapter(R.layout.scan_file_list_item, scanedFiles);
//            recy.setAdapter(adapter);

        }

        /*开始扫描*/
        private void startScan() {


            /*根目录*/
            final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();


            /*要扫描的文件后缀名*/
            final String endFilter = ".txt";

            final File dir = new File(rootPath);


            scanThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    scanFile(dir, endFilter);
                }
            });


            /*判断扫描是否完成 其实就是个定时任务 时间可以自己设置  每2s获取一下扫描线程的状态  如果线程状态为结束就说明扫描完成*/
            scanTimer = new Timer();
            scanTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i("线程状态",scanThread.getState().toString());

                    if (scanThread.getState() == Thread.State.TERMINATED) {
                        /*说明扫描线程结束 扫描完成  更新ui*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("线程结束","扫描完成");
//                                scanTv.setText("扫描完成，共扫描出" + scanedFiles.size() + "个txt文件");
//                                scanProgressbar.setVisibility(View.GONE);
                                cancelTask();

                            }
                        });
                    }
                }
            };

            scanTimer.schedule(scanTask, 0,1000);

            /*开始扫描*/
            scanThread.start();


        }




        /*扫描*/
        private void scanFile(File dir, String endFilter) {

            File[] files = dir.listFiles();

            if (files != null && files.length > 0) {

                for (final File file : files) {
                    if (file.getName().toUpperCase().endsWith(endFilter.toUpperCase())) {
                        /*是符合后缀名的文件  添加到列表中*/


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*刷新界面  要在主线程运行*/
//                                adapter.addData(file);
//                                scanTv.setText("已扫描出" + scanedFiles.size() + "个txt文件");

                            }
                        });
                    }
                    /*是目录*/
                    if (file.isDirectory()) {
                        /*递归扫描*/
                        scanFile(file, endFilter);
                    }
                }

            }
        }


        @Override
        protected void onDestroy() {
            super.onDestroy();
            cancelTask();

        }

        private void cancelTask() {

            Log.i("cancelTask","结束任务");
            if (scanTask!=null){
                scanTask.cancel();
            }

            if (scanTimer!=null){
                scanTimer.purge();
                scanTimer.cancel();;
            }
        }
    }

