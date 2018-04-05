package com.promact.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Flash;
import com.otaliastudios.cameraview.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishma on 22-01-2018.
 */

public class CameraActivity extends AppCompatActivity {
    List<ImageModel> imageModelList = new ArrayList<>();
    private CameraView cameraView;
    private TextView recordBtn;
    private boolean mCapturingPicture;
    private ImageView flashIV;
    private ImageView cameraSwitchIv;
    private boolean isFlashlightOn;
    private ImageView doneIV;
    // To show stuff in the callback
    private Size mCaptureNativeSize;
    private long mCaptureTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        cameraView = (CameraView) findViewById(R.id.camera);
        recordBtn = (TextView) findViewById(R.id.btnRecord);
        flashIV = (ImageView) findViewById(R.id.flashIv);
        cameraSwitchIv = (ImageView) findViewById(R.id.cameraSwitch);
        doneIV = (ImageView) findViewById(R.id.done);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });

        flashIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFlashlightOn) {
                    flashIV.setImageResource(R.drawable.ic_flash_auto_black_24dp);
                    cameraView.setFlash(Flash.AUTO);
                    isFlashlightOn = true;
                } else {
                    flashIV.setImageResource(R.drawable.ic_flash_off_black_24dp);
                    cameraView.setFlash(Flash.OFF);
                    isFlashlightOn = false;
                }
            }
        });

        cameraSwitchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCamera();
            }
        });

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                // CameraUtils will read EXIF orientation for you, in a worker thread.
                CameraUtils.decodeBitmap(picture, new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {

                        String mFileName = getFilesDir() + "/Images";

                        String mVideoFileName = String.valueOf("TempFolder" + System.currentTimeMillis());
                        if (!new File(mFileName).exists()) {
                            (new File(mFileName)).mkdir();
                        }

                        File image = new File(mFileName, mVideoFileName + ".jpg");
                        String imagePath = image.getAbsolutePath();
                        ImageModel imageModel = new ImageModel();
                        imageModel.setImagePath(imagePath);
                        imageModelList.add(imageModel);

                        try {
                            FileOutputStream out = new FileOutputStream(image);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            Log.e("CameraActivity", e.getMessage(), e);
                        }
                        Log.e("CameraActivity::", "Image File:" + image.getAbsolutePath());
                        isFlashlightOn = false;
                        mCapturingPicture = false;
                        //finish();
                    }
                });
            }

        });
        doneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                //intent.putExtra("Hello", image.getAbsolutePath());
                intent.putExtra("Hi", (Serializable) imageModelList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void toggleCamera() {
        if (mCapturingPicture) return;
        switch (cameraView.toggleFacing()) {
            case BACK:
                message("Switched to back camera!", false);
                flashIV.setVisibility(View.VISIBLE);
                break;

            case FRONT:
                flashIV.setVisibility(View.GONE);
                message("Switched to front camera!", false);
                break;
        }
    }

    private void capturePhoto() {
        if (mCapturingPicture) return;
        mCapturingPicture = true;
        mCaptureTime = System.currentTimeMillis();
        mCaptureNativeSize = cameraView.getPictureSize();
        message("Capturing picture...", false);
        cameraView.capturePicture();

    }

    private void message(String content, boolean important) {
        int length = important ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(this, content, length).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
