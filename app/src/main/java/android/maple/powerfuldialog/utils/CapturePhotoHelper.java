package android.maple.powerfuldialog.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 拍照辅助类
 * <p/>
 * Created by Maple on 2016/5/21.
 */
public class CapturePhotoHelper {

    private final static String TIMESTAMP_FORMAT = "yyyy_MM_dd_HH_mm_ss";

    public final static int CAPTURE_PHOTO_REQUEST_CODE = 0x1111;//拍照
    public final static int PHOTO_REQUEST_GALLERY = 0x1112;// 从相册中选择
    public final static int PHOTO_REQUEST_CUT = 0x1113;//剪切
    public final static int RESULT_OK = 0x1;//操作成功
    public int cropWidth = 136, cropHeight = 136;
    public int outWidth = 240, outHeight = 240;
    private int captureModel = 0;//获取照片模式  0表示裁剪模式  1表示不裁剪模式
    private Activity mActivity;
    public final static String PNG_SUFFIX = ".png";
    /**
     * 存放图片的目录
     */
    private File mPhotoFolder;
    /**
     * 拍照生成的图片文件
     */
    public File mPhotoFile;

    /**
     * @param activity
     * @param photoFolder 存放生成照片的目录，目录不存在时候会自动创建，但不允许为null;
     */
    public CapturePhotoHelper(final Activity activity, File photoFolder) {
        this.mActivity = activity;
        this.mPhotoFolder = photoFolder;
    }

    public CapturePhotoHelper(Activity activity) {
        this.mActivity = activity;
    }

    public CapturePhotoHelper setCropSize(int width, int height) {

        if (width > 0) {
            this.outWidth = width;
        }
        if (height > 0) {
            this.outHeight = height;
        }
        return this;
    }


    /**
     * 拍照
     */
    public void capture() {
        if (hasCamera()) {
            createPhotoFile();
            if (mPhotoFile == null) {
                Toast.makeText(mActivity, "打开相机失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 201);
                }
                fileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", mPhotoFile);

                List<ResolveInfo> resInfoList = mActivity.getApplicationContext().getPackageManager().queryIntentActivities(captureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mActivity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

            } else {
                fileUri = Uri.fromFile(mPhotoFile);
            }

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            mActivity.startActivityForResult(captureIntent, CAPTURE_PHOTO_REQUEST_CODE);

        } else {
            Toast.makeText(mActivity, "打开相机失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 从相册中选择
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 剪切图片
     *
     * @param uri     需要剪切的Uri路径
     * @param outpath 剪切后输出的文件路径
     */
    public void crop(Uri uri, String outpath) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", cropWidth);
        intent.putExtra("aspectY", outHeight);
        //输出图片的宽高均为
        intent.putExtra("outputX", outWidth);
        intent.putExtra("outputY", outHeight);
        intent.putExtra("scale", true);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", false);

        Uri fileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", new File(outpath));

        List<ResolveInfo> resInfoList = mActivity.getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            mActivity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    /**
     * 创建照片文件
     */
    public void createPhotoFile() {
        if (mPhotoFolder != null) {
            if (!mPhotoFolder.exists()) {//检查保存图片的目录存不存在
                mPhotoFolder.mkdirs();
            }

            String fileName = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
            mPhotoFile = new File(mPhotoFolder, fileName + System.currentTimeMillis() + PNG_SUFFIX);
            if (mPhotoFile.exists()) {
                mPhotoFile.delete();
            }
            try {
                mPhotoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                mPhotoFile = null;
            }
        } else {
            mPhotoFile = null;
            Toast.makeText(mActivity, "未指定存储目录", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public boolean hasCamera() {
        PackageManager packageManager = mActivity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 获取当前拍到的图片文件
     *
     * @return
     */
    public File getPhoto() {
        return mPhotoFile;
    }

    public CapturePhotoHelper captureResult(String path, int resultCode) {
        if (mPhotoFile != null) {
            if (resultCode == mActivity.RESULT_OK) {
                //创建输出图片的文件
                if (FileUtils.createFileByDeleteOldFile(path)) {

                    Uri fileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", mPhotoFile);

                    mActivity.grantUriPermission("com.android.camera.action.CROP", fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    crop(fileUri, path);
                } else {
                }
            } else {
                if (mPhotoFile.exists()) {
                    mPhotoFile.delete();
                }
            }
        }
        return this;
    }

    public CapturePhotoHelper galleryResult(String path, Intent i) {
        if (i != null) {
            //创建输出图片的文件
            if (FileUtils.createFileByDeleteOldFile(path)) {
                crop(i.getData(), path);
            } else {
            }
        }
        return this;
    }

    public Bitmap getCutResult(String path, Intent i) {
        Bitmap bitmap = null;
        if (i == null) {
            return bitmap;
        }
        Bundle bundle = i.getExtras();
        //传递bitmap时
        if (bundle != null) {
//            Logger.d("intent返回data true");
            bitmap = bundle.getParcelable("data");
        }
        //传递Uri时
        if (bitmap == null) {
//            Logger.d("intent返回data false 从path获取 ");
            try {
                Uri fileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", new File(path));

                List<ResolveInfo> resInfoList = mActivity.getApplicationContext().getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mActivity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().
                        openInputStream(fileUri));//将imageUri对象的图片加载到内存
            } catch (FileNotFoundException e) {
//                e.printStackTrace();
                bitmap = null;
            }
        }
        return bitmap;
    }

    public Bitmap getnoCutResult() {
        Bitmap bitmap = null;
        if (mPhotoFile != null) {
            //创建输出图片的文件
            Uri fileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", mPhotoFile);

            try {
                bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().
                        openInputStream(fileUri));//将imageUri对象的图片加载到内存
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bitmap= BitmapUtils.revitionImageSize(mPhotoFile.getAbsolutePath());//压缩
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
        return bitmap;
    }
}
