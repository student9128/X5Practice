package com.practice.kevin.x5practice;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


/**
 * Created by Kevin on 2017/3/28.
 * Describe: 正在加载中对话框
 * <p>
 * 使用方式：需要调用地方代码：
 * LoadingDialog mLoadingDialog = new LoadingDialog();
 * mLoadingDialog.show(getSupportFragmentManager(), "LoadingDialog");
 */
public class LoadingDialog extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_loading, container, false);

//        //添加这两行代码去除系统自带的边框
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //也可以这样设置背景透明
        // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
