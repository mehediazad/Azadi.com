package com.example.azadicom.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadicom.databinding.CustomMultipleImageBinding;

import java.util.ArrayList;

public class MultipleImageRecyclerAdapter extends RecyclerView.Adapter<MultipleImageRecyclerAdapter.ViewHolder> {
    private ArrayList<Uri> uriArrayList;
    private Context context;
    CountOfImagesWhenRemoved countOfImagesWhenRemoved;
    private itemClickListener itemClickListener;

    public MultipleImageRecyclerAdapter(ArrayList<Uri> uriArrayList, Context context, CountOfImagesWhenRemoved countOfImagesWhenRemoved,
                                        itemClickListener itemClickListener) {
        this.uriArrayList = uriArrayList;
        this.context = context;
        this.countOfImagesWhenRemoved = countOfImagesWhenRemoved;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MultipleImageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomMultipleImageBinding binding = CustomMultipleImageBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, countOfImagesWhenRemoved, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleImageRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.image.setImageURI(uriArrayList.get(position));
        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriArrayList.remove(uriArrayList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
                countOfImagesWhenRemoved.clicked(uriArrayList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CustomMultipleImageBinding binding;
        CountOfImagesWhenRemoved countOfImagesWhenRemoved;
        itemClickListener itemClickListener;
        public ViewHolder(@NonNull CustomMultipleImageBinding binding, CountOfImagesWhenRemoved countOfImagesWhenRemoved,
                          itemClickListener itemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.countOfImagesWhenRemoved = countOfImagesWhenRemoved;
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null){
                itemClickListener.itemClick(getAdapterPosition());
            }

        }
    }
    public interface CountOfImagesWhenRemoved{
        void clicked(int getSize);
    }
    public interface itemClickListener{
        void itemClick(int position);
    }
}
