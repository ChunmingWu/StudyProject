package com.adminstrator.guaguakaapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * https://codeload.github.com/wenmingvs/AndroidProcess/zip/master
 *下载Zip文件并解压，拿出图片展示
 * */

public class LoadFileActivity extends AppCompatActivity {

    private String url = "http://static.open.baidu.com/media/ch1000/png/chenweiting.png";
    private long mTaskId = 100;
    DownloadManager downloadManager;
    Button btn_download,btn_upzip,btn_show_picture;

    ViewPager pics;
    /** 存放当前文件夹下所有文件的路径的集合 **/
    private  ArrayList<String> paths = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file);
         btn_download = findViewById(R.id.btn_download);
        btn_upzip = findViewById(R.id.btn_upzip);
        btn_show_picture = findViewById(R.id.btn_show_picture);
        pics = findViewById(R.id.vp_picture);
        FileDownloader.setup(this);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();

              /*  try {
                    ZipUtils.UnZipFolder(mSinglePath + File.separator + "picturezip.zip",mSinglePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });

        btn_upzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    upZipFile(new File(mSinglePath + File.separator + fileName),mSinglePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(getClass().getSimpleName(),"--------->fuck  e:" + e.getMessage().toString());
                }
            }
        });

        btn_show_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
    }

    private void init() {

        Map<String,Bitmap> maps = new TreeMap<String, Bitmap>();
        try {
            maps = buildThum();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.e(getClass().getSimpleName(),"----->paths:" + paths.size() + "    "  + paths.toString());
        Log.e(getClass().getSimpleName(),"----->maps:" + maps.size() + "     " +  maps.toString());

        /** 存放当前文件夹下的图片转换成的bitmap **/
        final List<Bitmap> datas = new ArrayList<>();
        /** 存放所有的ImageView的集合 **/
        final List<ImageView> images = new ArrayList<>();

        for (Map.Entry<String,Bitmap> entry : maps.entrySet()) {
            if(null == entry.getValue()){
                continue;
            }
            datas.add(entry.getValue());
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(entry.getValue());
         /*   Glide.with(this)
                    .load(entry.getKey())
                    .into(iv);*/
            images.add(iv);
        }
        Log.e(getClass().getSimpleName(),"----->images:" + images.toString());
        Log.e(getClass().getSimpleName(),"----->datas:" + datas.size());
        // 为viewPager设置适配器
        pics.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(images.get(position), 0);//添加页卡
                return images.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(images.get(position));
            }
        });

        // viewPager 设置页面改变的监听
        pics.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                count.setText((position + 1) +" / "+datas.size()+" 张");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    /**
     * 判断当前存储卡是否可用
     **/
    public boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取当前需要查询的文件夹
     **/
    public String takePicRootDir(Context context) {
        if (checkSDCardAvailable()) {
            return Environment.getExternalStorageDirectory() + File.separator + "runman";
        } else {
            return context.getFilesDir().getAbsolutePath() + File.separator + "runman";
        }
    }

    /**
     * 描述：  获取指定文件夹下面的所有文件目录
     * 作者：  gyz
     * 时间：  2017-01-17 17:02:40
     */
    private Map<String, Bitmap> buildThum() throws FileNotFoundException {
        File baseFile = new File(mSinglePath);
        // 使用TreeMap，排序问题就不需要纠结了
        Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
        if (baseFile != null && baseFile.exists()) {

            getFiles(paths,baseFile);

            if (!paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));

                    maps.put(paths.get(i), bitmap);
                }
            }
        }

        return maps;
    }


    //获取图片路径
    private  void getFiles(List list,File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory()) {//若是目录，则递归打印该目录下的文件
                getFiles(list,f);
            }
            if(f.isFile()) {    //若是文件，直接打印
                Log.e("getFiles","------------->f:" + f.getAbsolutePath() + "   f.exists():" + f.exists());
                if(isImageFile(f.getAbsolutePath())){
                    list.add(f.getAbsolutePath());
                }
            }
        }
    }

    //判断文件是否是图片
   private boolean isImageFile(String filePath){
       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inJustDecodeBounds = true;
       BitmapFactory.decodeFile(filePath,options);
       if(options.outWidth == -1){
           return false;
       }
        return true;
   }



    BaseDownloadTask singleTask ;
    public int singleTaskId = 0;
//    String apkUrl = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";
    String apkUrl = "https://b2c-store.oss-ap-southeast-1.aliyuncs.com/ceshi/FRT.zip";
    String singleFileSaveName = "liulishuo";
    public String mSinglePath = FileDownloadUtils.getDefaultSaveRootPath()+ File.separator+"feifei_save"
            + File.separator + singleFileSaveName;;
    public String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath()+File.separator+"feifei_save";
    public String fileName = "";

    private void startDownload() {
        checkFiles(new File(FileDownloadUtils.getDefaultSaveRootPath()));
        deleteDir(new File(FileDownloadUtils.getDefaultSaveRootPath()));

        String url = apkUrl;
        singleTask = FileDownloader.getImpl().create(url)
                .setPath(mSinglePath,true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener(){
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("feifei","pending taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("feifei","progress taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed());
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e("feifei","blockComplete taskId:"+task.getId()+",filePath:"+task.getPath()+",fileName:"+task.getFilename()+",speed:"+task.getSpeed()+",isReuse:"+task.reuse());
                        fileName = task.getFilename();
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e("feifei","completed taskId:"+task.getId()+",isReuse:"+task.reuse());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("feifei","paused taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e("feifei","error taskId:"+task.getId()+",e:"+e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e("feifei","warn taskId:"+task.getId());
                    }
                });

        singleTaskId =  singleTask.start();
    }



    /**
     * 解压缩功能.
     * 将zipFile文件解压到folderPath目录下.
     *
     * @throws Exception
     */
    public  int upZipFile(File zipFile, String folderPath) throws Exception {
        paths.clear();
        try {
            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {

                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    Log.e("upZipFile", "ze.getName() = " + ze.getName());
                    String dirstr = folderPath +  ze.getName();
                    //dirstr.trim();
                    dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                    Log.e("upZipFile", "str = " + dirstr);
                    File f = new File(dirstr);
                    boolean mdir = f.mkdir();
                    Log.e("upZipFile", "mdir = " + mdir);
                    continue;
                }
                Log.e("upZipFile", "ze.getName() = " + ze.getName());
                OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath,ze.getName())));
                Log.e("upZipFile","---->getRealFileName(folderPath,ze.getName()): " + getRealFileName(folderPath,ze.getName()).getPath() + "  name:" + getRealFileName(folderPath,ze.getName()).getName());

//                paths.add(getRealFileName(folderPath,ze.getName()).getAbsolutePath());

                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    Log.e("upZipFile","---->readLen: " + readLen);
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
                Log.e("upZipFile","---->finish ");
            }
            zfile.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("upZipFile","---->catch  e:" + e.getMessage().toString());
        }

        Log.e("upZipFile","---->zipFile:" + zipFile.getAbsolutePath());
        //解压完成之后删除压缩包
        deleteDir(zipFile);
        checkFiles(new File(folderPath));
        Log.e("upZipFile","---->zipFile.exists():" + zipFile.exists());
        return 0;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            Log.d(getClass().getSimpleName(), "1ret = " + ret);
            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d(getClass().getSimpleName(), "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            Log.d(getClass().getSimpleName(), "2ret = " + ret);
            return ret;
        }
        return ret;
    }


    /**
     * 删除整个文件夹 或者文件
     *
     * @param file
     */
    public void deleteDir(File file) {
        Log.e("deleteDir","------------->file:" + file.getAbsolutePath());
        if (!file.exists()) {
            return;
        }
        Log.e("deleteDir","------------->111" );
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
            }

            for (int index = 0; index < childFiles.length; index++) {
                deleteDir(childFiles[index]);
            }
        }
        file.delete();
        Log.e("deleteDir","------------->222" );
    }

    private  void checkFiles(File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory()) {//若是目录，则递归打印该目录下的文件
                checkFiles(f);
            }
            if(f.isFile()) {    //若是文件，直接打印
                Log.e("checkFiles","------------->f:" + f.getAbsolutePath() );
            }
        }
    }



}
