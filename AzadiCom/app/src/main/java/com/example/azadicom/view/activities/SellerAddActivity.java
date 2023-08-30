package com.example.azadicom.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.azadicom.R;
import com.example.azadicom.databinding.ActivitySellerAddBinding;
import com.example.azadicom.view.adapters.MultipleImageRecyclerAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;


public class SellerAddActivity extends AppCompatActivity {
    private ActivitySellerAddBinding binding;

    //
    ArrayList<Uri> uri = new ArrayList<>();
    MultipleImageRecyclerAdapter adapter;
    private static final int Read_Permission = 101;
    private static final int PICK_IMAGE = 1;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seller_add);

        binding.Toolbar.setNavigationOnClickListener(view -> onBackPressed());

//        binding.image1.setOnClickListener(v -> startImagePicker());
        setUpRecyclerView();

        binding.buttonMultipleImageSubmit.setOnClickListener(v -> startImagePicker());


    }

    private void startImagePicker() {
        if (ContextCompat.checkSelfPermission(SellerAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SellerAddActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);

            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    private void setUpRecyclerView() {
        adapter = new MultipleImageRecyclerAdapter(uri);
        binding.recyclerviewGalleryImage.setLayoutManager(new GridLayoutManager(SellerAddActivity.this, 4));
        binding.recyclerviewGalleryImage.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {

                // this part is for to get multiple image
                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    Uri imageuri = data.getClipData().getItemAt(i).getUri();
                    uri.add(imageuri);
                }
                // then notify the adapter
                adapter.notifyDataSetChanged();
                binding.totalPhotos.setText("ছবি : (" + uri.size() + ")");

            } else {
                // this is for to get single image
                Uri imageuri = data.getData();
                // and add the code into arrayList
                uri.add(imageuri);
            }
            //notify the adapter
            adapter.notifyDataSetChanged();
            binding.totalPhotos.setText("ছবি : (" + uri.size() + ")");
        } else {
            Toast.makeText(this, "You Have Not Pick Any Image", Toast.LENGTH_SHORT).show();
        }
    }
//    private void startImagePicker() {
//        ImagePicker.with(SellerAddActivity.this)
//                .crop()                    //Crop image(Optional), Check Customization for more option
//                .compress(1024)            //Final image size will be less than 1 MB(Optional)
////                      .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                .maxResultSize(400, 400)  //Final image resolution will be less than 1080 x 1080(Optional)
//                .start();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Uri uri = data.getData();
//        binding.image1.setImageURI(uri);
//    }

}