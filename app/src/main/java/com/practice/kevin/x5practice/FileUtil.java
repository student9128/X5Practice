package com.practice.kevin.x5practice;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/29.
 * <h3>
 * Describe:
 * <h3/>
 */
public class FileUtil {
    //遍历手机所有文件 并将路径名存入集合中 参数需要 路径和集合
    public static void recursionFile(File dir, List<String> images) {
        //得到某个文件夹下所有的文件
        File[] files = dir.listFiles();
        //文件为空
        if (files == null) {
            return;
        }
        //遍历当前文件下的所有文件
        for (File file : files) {
            //如果是文件夹
            if (file.isDirectory() && !file.isHidden()) {
                //则递归(方法自己调用自己)继续遍历该文件夹
                recursionFile(file, images);
            } else { //如果不是文件夹 则是文件
                //如果文件名以 .mp3结尾则是mp3文件
                if (file.getName().endsWith(".txt")) {
                    //往图片集合中 添加图片的路径
                    images.add(file.getAbsolutePath());
                    Log.i("GetFile.class", file.getName() + "===路径：" + file.getAbsolutePath() +
                            "\n");
                }
            }
        }
    }

    /**
     * 将所有文档的信息全部存储到一个集合
     *
     * @param dir
     * @param data
     */
    public static void mapFile(File dir, List<FileInfoBean> data) {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory() && !file.isHidden()) {
                mapFile(file, data);
            } else {
                if (ifAll(file)) {
                    Log.d("FileUtil.class", file.getName());
                    FileInfoBean fileInfoBean = new FileInfoBean();
                    fileInfoBean.setFileName(file.getName());
                    fileInfoBean.setFilePath(file.getAbsolutePath());
                    data.add(fileInfoBean);
                }
            }
        }
    }

    public static void mapMultiFile(File dir, List<FileInfoBean> data, String... type) {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory() && !file.isHidden()) {
                mapMultiFile(file, data);
            } else {
                for (String t : type) {
                    if (file.getName().endsWith(t)) {
                        FileInfoBean fileInfoBean = new FileInfoBean();
                        fileInfoBean.setFileName(file.getName());
                        fileInfoBean.setFilePath(file.getAbsolutePath());
                        data.add(fileInfoBean);

                    }
                }
            }
        }
    }

    public static void mapPPTFile(File dir, List<FileInfoBean> data) {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory() && !file.isHidden()) {
                mapMultiFile(file, data);
            } else {
                    if (file.getName().endsWith(".ppt")||file.getName().endsWith(".pptx")) {
                        FileInfoBean fileInfoBean = new FileInfoBean();
                        fileInfoBean.setFileName(file.getName());
                        fileInfoBean.setFilePath(file.getAbsolutePath());
                        data.add(fileInfoBean);

                    }

            }
        }
    }

    private static String[] extensionArr = {".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx", "" +
            ".txt", ".pdf"};

    /**
     * 包含这几种类型是所有文档
     *
     * @param file
     * @return
     */
    private static boolean ifAll(File file) {
        return ifType(file, ".doc") || ifType(file, ".docx") || ifType(file, ".ppt") || ifType
                (file, ".pptx") || ifType(file, ".xls") || ifType(file, ".xlsx") || ifType(file,
                ".txt") || ifType(file, ".pdf");
    }

    /**
     * 判断文件是否已某个扩展名结尾
     *
     * @param file
     * @param extension
     * @return
     */
    private static boolean ifType(File file, String extension) {
        return file.getName().endsWith(extension);
    }
}

