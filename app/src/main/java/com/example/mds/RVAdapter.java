package com.example.mds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mds.model.Product;

import java.util.ArrayList;




public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {



    ArrayList<Product> objectModelClassList;
    OnItemListener onItemListener;


    public RVAdapter(ArrayList<Product> objectModelClassList, OnItemListener onItemListener) {
        this.objectModelClassList = objectModelClassList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull  ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.single_row,viewgroup,false);
        return new RVViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RVAdapter.RVViewHolderClass rvViewHolderClass, int i) {
        Product objectProductClass = objectModelClassList.get(i);

        rvViewHolderClass.prodName.setText(objectProductClass.getProdName());
        String price = String.valueOf(objectProductClass.getProdPrice()) + " $";
        rvViewHolderClass.prodPrice.setText(price);
        //rvViewHolderClass.prodDesc.setText("Pret: " +objectProductClass.getProdDescription());
        rvViewHolderClass.objectImageView.setImageBitmap(objectProductClass.getImage());

        rvViewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onItemClick(objectModelClassList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView prodName, prodPrice, prodDesc;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.productName);
            prodPrice = itemView.findViewById(R.id.productPrice);
            //prodDesc = itemView.findViewById(R.id.productDesc);
            objectImageView = itemView.findViewById(R.id.productImage);
        }
    }

    public interface OnItemListener{
        void onItemClick(Product product);
    }
}
