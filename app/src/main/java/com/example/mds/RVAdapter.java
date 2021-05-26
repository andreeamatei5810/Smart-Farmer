package com.example.mds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {



    ArrayList<ProductClass> objectModelClassList;

    public RVAdapter(ArrayList<ProductClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull  ViewGroup viewgroup, int i) {
        return new RVViewHolderClass(LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.single_row,viewgroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  RVAdapter.RVViewHolderClass rvViewHolderClass, int i) {
        ProductClass objectProductClass = objectModelClassList.get(i);

        rvViewHolderClass.imageNameTV.setText(objectProductClass.getProdName());
        rvViewHolderClass.imageNameTV1.setText(objectProductClass.getProdPirce());
        rvViewHolderClass.imageNameTV2.setText(objectProductClass.getProdDescription());

        rvViewHolderClass.objectImageView.setImageBitmap(objectProductClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView imageNameTV, imageNameTV1, imageNameTV2;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            imageNameTV = itemView.findViewById(R.id.sr_imageDetailsTV);
            imageNameTV1 = itemView.findViewById(R.id.sr_imageDetailsTV1);
            imageNameTV2 = itemView.findViewById(R.id.sr_imageDetailsTV2);

            objectImageView = itemView.findViewById(R.id.sr_imageTV);
        }
    }
}
