package com.seakleang.measureapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seakleang.measureapp.view.CustomView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private CustomView customView;
    private TextView textView;
    private Button camerabtn, distancebtn, defaultbtn;
    private double defaultDistance, distance;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        customView = findViewById(R.id.custom_view);
        textView = findViewById(R.id.tv_distance);
        camerabtn = findViewById(R.id.camera_button);
        distancebtn = findViewById(R.id.distance_button);
        defaultbtn = findViewById(R.id.default_button);

        camerabtn.setOnClickListener(this);
        distancebtn.setOnClickListener(this);
        defaultbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_button:
                callCamera();
                break;
            case R.id.default_button:
                defaultDistance = customView.getDefaultDistance();
                break;
            case R.id.distance_button:
                textView.setText(((customView.getDistance()*29)/defaultDistance)+"");
        }
    }

    public void callCamera() {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_REQUEST:
                if(resultCode == RESULT_OK && data.hasExtra("data")){
                    Glide.with(this).load(data.getData()).into(imageView);
                }
                break;
        }
    }
}
