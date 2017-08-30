package com.example.joker.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.R.attr.data;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        String way = intent.getStringExtra(MainActivity.GET_IMAGE);
        if(way == "1") {
            Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }else {
            Intent gaintent = new Intent(Intent.ACTION_GET_CONTENT);
            gaintent.setType("image/*");
            startActivityForResult(gaintent, 2);
        }
    }

}
