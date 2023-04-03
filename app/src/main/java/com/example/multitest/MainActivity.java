package com.example.multitest;


import static com.luck.picture.lib.config.PictureSelectionConfig.selectorStyle;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;

import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;


public class MainActivity extends AppCompatActivity {
    Button gal;

    RecyclerView rec;
    recAdapter adapter;

    ArrayList<LocalMedia> arr = new ArrayList<LocalMedia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gal = findViewById(R.id.gallery);
        rec = findViewById(R.id.rec);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rec.setLayoutManager(layoutManager);

        gal.setOnClickListener(view -> picImage());
    }

    void picImage(){
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(10 - arr.size())
                .setCompressEngine(new CompressFileEngine() {
                    @Override
                    public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
                        Luban.with(context).load(source).ignoreBy(100)
                                .setCompressListener(new OnNewCompressListener() {
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(String source, File compressFile) {
                                        if (call != null) {
                                            call.onCallback(source, compressFile.getAbsolutePath());
                                        }
                                    }

                                    @Override
                                    public void onError(String source, Throwable e) {
                                        if (call != null) {
                                            call.onCallback(source, null);
                                        }
                                    }
                                }).launch();
                    }
                })
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        arr.addAll(result);
                        adapter = new recAdapter(arr);
                        rec.setAdapter(adapter);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
