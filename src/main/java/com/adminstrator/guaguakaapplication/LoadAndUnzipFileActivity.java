package com.adminstrator.guaguakaapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.liulishuo.filedownloader.BaseDownloadTask;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LoadAndUnzipFileActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private Button btn_download, btn_unzip, btn_show_picture;
    private ViewPager vp_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_and_unzip_file);
        initViews();
    }

    private void initViews() {
        btn_download = findViewById(R.id.btn_download);
        btn_unzip = findViewById(R.id.btn_unzip);
        btn_show_picture = findViewById(R.id.btn_show_picture);
        vp_picture = findViewById(R.id.vp_picture);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });

        btn_unzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unZipFile(new File(saveZipFilePath + File.separator + fileName),saveZipFilePath);
            }
        });

        btn_show_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndShowPicture();
            }
        });
    }


    /**
     * 存放当前文件夹下所有图片文件的路径的集合
     * **/
    private ArrayList<String> paths = new ArrayList<String>();

    /**
     * 从解压后的文件中获取图片进行展示
     * */
    private void getAndShowPicture() {
        paths.clear();

        Map<String,Bitmap> maps = new TreeMap<String, Bitmap>();
        try {
            maps = getImages(saveZipFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //将获取到的图片放在ImageView中用Viewpager展示
        final List<ImageView> images = new ArrayList<>();
        for (Map.Entry<String,Bitmap> entry : maps.entrySet()) {
            if(null == entry.getValue()){
                continue;
            }
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(entry.getValue());
            images.add(iv);
        }

        vp_picture.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //添加页卡
                container.addView(images.get(position), 0);
                return images.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(images.get(position));
            }
        });

    }

    /**
     * 获取指定文件夹下面的所有图片的文件目录和其Bitmap对象
     */
    private Map<String, Bitmap> getImages(String filePath) throws FileNotFoundException {
        File baseFile = new File(filePath);

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


    /**
     * 获取图片路径
     * */
    private  void getFiles(List list, File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory()) {
                getFiles(list,f);
            }
            if(f.isFile()) {
                Log.e(TAG,"------------->getFiles  f:" + f.getAbsolutePath() );
                if(isImageFile(f.getAbsolutePath())){
                    list.add(f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 判断文件是否是图片
     * */
    private boolean isImageFile(String filePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);
        if(options.outWidth == -1){
            return false;
        }
        return true;
    }


    /**
     * zipFile 压缩文件
     * folderPath 解压后的文件路径
     * */
    private void unZipFile(File zipFile, String folderPath) {
        try {
            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();

                if (ze.isDirectory()) {
                    String dirstr = folderPath +  File.separator + ze.getName();
                    dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                    File f = new File(dirstr);
                    boolean mdir = f.mkdir();
                    continue;
                }

                OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath,ze.getName())));
                Log.e(TAG,"---->getRealFileName(folderPath,ze.getName()): " + getRealFileName(folderPath,ze.getName()).getPath() + "  name:" + getRealFileName(folderPath,ze.getName()).getName());
                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
            }
            zfile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //解压完成之后删除压缩包
        deleteDir(zipFile);
    }

    /**
     * 根据保存zip的文件路径和zip文件相对路径名，返回一个实际的文件
     * 因为zip文件解压后，里边可能是多重文件结构
     * */
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
            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            return ret;
        }
        return ret;
    }

    /**
     * 删除整个文件夹 或者 文件
     */
    public void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
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
    }



    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    //文件下载地址
    private String downloadUrl = "https://b2c-store.oss-ap-southeast-1.aliyuncs.com/ceshi/FRT.zip";
    //文件保存路径
    private String saveZipFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder";
    //下载下来的文件名称
    private String fileName;

    private void startDownload() {
        singleTask = FileDownloader.getImpl().create(downloadUrl)
                .setPath(saveZipFilePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG,"----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
                        super.blockComplete(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG,"---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG, "--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();
    }

}
