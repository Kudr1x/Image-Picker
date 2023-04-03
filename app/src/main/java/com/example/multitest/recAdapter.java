package com.example.multitest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.squareup.picasso.Picasso;
import com.zero_code.libEdImage.ExtendKt;

import java.util.ArrayList;

public class recAdapter extends RecyclerView.Adapter<recVh>{

    ArrayList<LocalMedia> items;

    public recAdapter(ArrayList<LocalMedia> items){
        this.items = items;
    }

    @NonNull
    @Override
    public recVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new recVh(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull recVh holder, int position) {
        //todo setimage
        LocalMedia image = items.get(position);
        Picasso.get().load(image.getPath()).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class recVh extends RecyclerView.ViewHolder{

    ImageView imageView;

    private recAdapter adapter;

    public recVh(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image);

        itemView.findViewById(R.id.draw).setOnClickListener(view -> {
        });

        itemView.findViewById(R.id.close).setOnClickListener(view -> {
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
    }

    public recVh linkAdapter(recAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}



