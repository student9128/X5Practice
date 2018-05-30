package com.practice.kevin.x5practice.filescan;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/30.
 * <h3>
 * Describe:
 * <h3/>
 */
public interface FileScanListener {
    void onBegin();

    void onProgressing();

    void onFinish();

    void onPaused();

    void onCancelled();
}
