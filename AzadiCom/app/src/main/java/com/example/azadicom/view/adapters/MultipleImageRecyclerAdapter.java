package com.example.azadicom.view.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadicom.R;
import com.example.azadicom.databinding.CustomMultipleImageBinding;

import java.util.ArrayList;

public class MultipleImageRecyclerAdapter extends RecyclerView.Adapter<MultipleImageRecyclerAdapter.ViewHolder> {
    private ArrayList<Uri> uriArrayList;

    public MultipleImageRecyclerAdapter(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public MultipleImageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomMultipleImageBinding binding = CustomMultipleImageBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleImageRecyclerAdapter.ViewHolder holder, int position) {
        holder.binding.image.setImageURI(uriArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomMultipleImageBinding binding;
        public ViewHolder(@NonNull CustomMultipleImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
