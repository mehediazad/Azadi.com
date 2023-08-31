package com.example.azadicom.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.azadicom.R;
import com.example.azadicom.databinding.ActivitySellerAddBinding;
import com.example.azadicom.databinding.CustomDialogZoomBinding;
import com.example.azadicom.view.adapters.MultipleImageRecyclerAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;


public class SellerAddActivity extends AppCompatActivity implements MultipleImageRecyclerAdapter.CountOfImagesWhenRemoved,
        MultipleImageRecyclerAdapter.itemClickListener {
    private ActivitySellerAddBinding binding;

    //
    ArrayList<Uri> uri = new ArrayList<>();
    MultipleImageRecyclerAdapter adapter;
    private static final int Read_Permission = 101;
    private static final int PICK_IMAGE = 1;
    //
    String[] Phone_Condition = {"ব্যবহৃত","নতুন"};
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seller_add);

        binding.Toolbar.setNavigationOnClickListener(view -> onBackPressed());

//        binding.image1.setOnClickListener(v -> startImagePicker());
        setUpRecyclerView();

        binding.buttonMultipleImageSubmit.setOnClickListener(v -> startImagePicker());

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, Phone_Condition);
        binding.actSelectPhoneCondition.setAdapter(adapterItems);

        binding.actSelectPhoneCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"অবস্থা: "+item,Toast.LENGTH_SHORT).show();
            }
        });


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
        adapter = new MultipleImageRecyclerAdapter(uri, getApplicationContext(), this, this);
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
                    // limited the on of image piker from the gallery
                    if (uri.size() < 4) {
                        Uri imageuri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageuri);
                    } else {
                        Toast.makeText(SellerAddActivity.this, "Not allow to pic more than 4 images", Toast.LENGTH_SHORT).show();
                    }
                }
                // then notify the adapter
                adapter.notifyDataSetChanged();
                binding.totalPhotos.setText("ছবি : (" + uri.size() + ")");

            } else {
                // limited the on of image piker from the gallery
                if (uri.size() < 4) {
                    // this is for to get single image
                    Uri imageuri = data.getData();
                    // and add the code into arrayList
                    uri.add(imageuri);
                } else {
                    Toast.makeText(SellerAddActivity.this, "Not allow to pic more than 4 images", Toast.LENGTH_SHORT).show();
                }
            }
            //notify the adapter
            adapter.notifyDataSetChanged();
            binding.totalPhotos.setText("ছবি : (" + uri.size() + ")");
        } else {
            Toast.makeText(this, "You Have Not Pick Any Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clicked(int getSize) {
        // whenever images gets removed adapter will get update
        //and this will print the actual count
        binding.totalPhotos.setText("ছবি : (" + uri.size() + ")");
        Toast.makeText(SellerAddActivity.this, "Removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClick(int position) {
        // here we will decalre the custom dialog code
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog_zoom);

//        TextView txt_dialog = dialog.findViewById(R.id.txt_dialog);
        ImageView imageView_dialog = dialog.findViewById(R.id.imageView_dialog);
        Button btn_close_dialog = dialog.findViewById(R.id.btn_close_dialog);

//        txt_dialog.setText("ছবি "+position);


        imageView_dialog.setImageURI(uri.get(position));

        btn_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}