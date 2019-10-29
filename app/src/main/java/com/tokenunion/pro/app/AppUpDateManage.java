package com.tokenunion.pro.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.BuildConfig;
import com.tokenunion.pro.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * APP更新类
 */
public class AppUpDateManage {
    public static void loadNewVersionProgress(final Context mContext, final String uri) {
        try {
            final ProgressDialog pd;    //进度条对话框
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(false);
            pd.setMessage(mContext.getString(R.string.mine_app_download_title)); // 正在下载更新
            pd.show();
            //启动子线程下载任务
            new Thread(){
                @Override
                public void run() {
                    try {
                        File file = getFileFromServer(uri, pd);
                        installApk(mContext,file);
                        pd.dismiss(); //结束掉进度条对话框
                        // System.exit(0);
                    } catch (Exception e) {
                        //下载apk失败
                        ToastUtils.showToast(mContext, mContext.getString(R.string.mine_app_download_failed));//"下载失败,请稍后重试。。。");
                        e.printStackTrace();
                        LogUtil.e(AppUpDateManage.class.getSimpleName(),
                                mContext.getString(R.string.mine_app_download_failed)+ e.getMessage());
                    }
                }}.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory()+"/Download", time+"any_app.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }
    /**
     * 安装apk
     */
    public static void installApk(Context context, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
