package com.wjf.recyclerviewrefresh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wjf.recyclerviewrefresh.util.PrefUtils;

import java.io.File;
import java.io.IOException;

public class TakePhotoActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnTake;
    private Button btnSelect;
    private ImageView imageView;
    private String state;
    private Uri imageUri;
    private static final int TAKE_PHOTO = 1;//从相机选择
    private static final int PICTURE_CAPTURE = 2;//从相册选择
    private static final int ZOOM_AFTER_TAKE_PHOTO = 3;//从相机选择后截图
    private static final int ZOOM_AFTER_PICTURE_CAPTURE = 4;//从照片选择后截图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnTake = (Button) findViewById(R.id.btn_takephoto);
        btnSelect = (Button) findViewById(R.id.btn_xiangce);
        //存储介质
        state = Environment.getExternalStorageState();
        addListener();
    }
    /**
     * 添加监听器
     */
    private void addListener() {
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
    }
    /**
     * 选择图片
     */
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PICTURE_CAPTURE);
    }
    /**
     * 拍照
     */
    private void takePhoto() {
        //创建File对象，用于存储拍照后的图片
        //将此图片存储于SD卡的根目录下
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "tem.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File对象转换成Uri对象
        //Uri表标识着图片的地址
        imageUri = Uri.fromFile(outputImage);
        PrefUtils.putString(getApplicationContext(),"filepath",outputImage.getAbsolutePath());
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(getImageByCamera, TAKE_PHOTO);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult requestCode:"+requestCode+" resultCode:"+resultCode+" data:"+data);
        String filepath;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    filepath = PrefUtils.getString(getApplicationContext(), "filepath",null);
                    imageUri = Uri.fromFile(new File(filepath));
                    startPhotoZoom(imageUri, ZOOM_AFTER_TAKE_PHOTO);
                    break;
                case PICTURE_CAPTURE:
                    imageUri = data.getData();
                    if (imageUri != null) {
                        startPhotoZoom(imageUri, ZOOM_AFTER_PICTURE_CAPTURE);
                    }
                    break;
                case ZOOM_AFTER_TAKE_PHOTO:
                    // 拿到照相截取后的剪切数据
                    //Bitmap bmap = data.getParcelableExtra("data");
                    filepath =  PrefUtils.getString(getApplicationContext(), "filepath",null);
                    imageUri = Uri.fromFile(new File(filepath));
                    if (imageUri != null) {
                        Bitmap bitmap = getBitmapFromUri(imageUri);
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
                case ZOOM_AFTER_PICTURE_CAPTURE:
                    // 拿到从相册选择截取后的剪切数据
                    if (imageUri != null) {
                        Bitmap bitmap = getBitmapFromUri(imageUri);
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 通过uri获取bitmap
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
    /*
     * 图片裁剪
     */
    private void startPhotoZoom(Uri uri, int i) {
        Log.d(TAG,"startPhotoZoom uri:"+uri);
        if (uri == null) {
            Toast.makeText(getApplicationContext(), "选择图片出错！", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        //如果为true,则通过 Bitmap bmap = data.getParcelableExtra("data")取出数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, i);
    }


//    public void testPermissions() {
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(TakePhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(TakePhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                System.out.println("拒绝但是没有选择不再提示");
//                ActivityCompat.requestPermissions(TakePhotoActivity.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        0x1);
//            } else {
//                System.out.println(" 第一次   或者    拒绝并且不再提示");
//                ActivityCompat.requestPermissions(TakePhotoActivity.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        0x1);
//            }
//        } else {
//            System.out.println(" 允许之后  再次调用 ");
//            getnamre();
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 0x1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getnamre();
//                } else {
//                    Toast.makeText(this, "PERMISSION_deny", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
}