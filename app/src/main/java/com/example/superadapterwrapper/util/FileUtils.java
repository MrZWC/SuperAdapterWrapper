package com.example.superadapterwrapper.util;

import android.os.Environment;
import android.text.TextUtils;

import com.idonans.lang.util.ContextUtil;
import com.idonans.lang.util.FileUtil;
import com.idonans.lang.util.IOUtil;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/31
 * Time: 14:55
 */
public class FileUtils {
    public static final String SAVE_FOLDER = "/" + "Quark/Download";
    //public static final String SAVE_FOLDER = "/" + "扩聊";
    /**
     * sd卡的根目录
     */
    public static String sSdRootPath = Environment.getExternalStorageDirectory().getPath();
    /**
     * 手机的缓存根目录
     */
    public static String sDataRootPath = ContextUtil.getContext().getCacheDir().getPath();


    /**
     * 确保目录存在,没有则创建
     */
    public static boolean confirmFolderExist(String folderPath) {

        File file = new File(folderPath);
        if (!file.exists()) {
            return file.mkdirs();
        }

        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean checkFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return;
        deleteFile(new File(filePath));
    }

    /**
     * 删除文件夹所有内容
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) { // 判断文件是否存在
            if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (File childFile : files) { // 遍历目录下所有的文件
                        deleteFile(childFile); // 把每个文件 用这个方法进行迭代
                    }
                }
            }

            //安全删除文件
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件.防止删除后重新创建文件，报错 open failed: EBUSY (Device or resource busy)
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }


    /**
     * 获取存储的根目录
     */
    public static String getRootDirectory() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sSdRootPath
                + SAVE_FOLDER : sDataRootPath + SAVE_FOLDER;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            FileUtil.createNewFileQuietly(newFile);
            IOUtil.copy(oldFile, newFile, null, null);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<File> getFileList(String directoryName) {
        ArrayList<File> files = new ArrayList<>();
        addFlie(directoryName, files);
        return files;
    }

    public static void addFlie(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                addFlie(file.getAbsolutePath(), files);
            }
        }
    }

    /**
     * oldPath 和 newPath必须是新旧文件的绝对路径
     */
    private File renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return null;
        }

        if (TextUtils.isEmpty(newPath)) {
            return null;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean b = oldFile.renameTo(newFile);
        File file2 = new File(newPath);
        return file2;
    }

    /**
     * 重命名
     */
    public static File renameFile(File srcFile, String newName) {

        File destFile = new File(newName);
        if (destFile.exists() || destFile.isDirectory()) {
            return srcFile;
        }
        boolean b = srcFile.renameTo(destFile);
        if (!b) {
            KLog.d("filelength=false", newName.length());
        } else {
            KLog.d("filelength=true", newName.length());
        }
        return destFile;
    }

    /**
     * 2 * 通过文件路径直接修改文件名
     * 3 *
     * 4 * @param filePath 需要修改的文件的完整路径
     * 5 * @param newFileName 需要修改的文件的名称
     * 6 * @return
     * 7
     */
    public static String FixFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return null;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return null;
        String newFilePath = null;
        if (f.isDirectory()) { // 判断是否为文件夹
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        } else {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName
                    + filePath.substring(filePath.lastIndexOf("."));
        }
        File nf = new File(newFilePath);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
        return newFilePath;
    }
}
