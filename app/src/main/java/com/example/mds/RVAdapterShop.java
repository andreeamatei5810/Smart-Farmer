package com.example.mds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mds.model.Product;

import java.util.ArrayList;




public class RVAdapterShop extends RecyclerView.Adapter<RVAdapterShop.RVViewHolderClass> {



    ArrayList<Pair<Product, Integer>> objectModelClassList;
    Product objectProductClass;
    OnItemListener onItemListener;
    DBHelper dbHelper;
    Activity activity;



    public RVAdapterShop(ArrayList<Pair<Product, Integer>> objectModelClassList, OnItemListener onItemListener, Activity activity) {
        this.objectModelClassList = objectModelClassList;
        this.onItemListener = onItemListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull  ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.single_row_cart,viewgroup,false);
        return new RVViewHolderClass(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  RVAdapterShop.RVViewHolderClass rvViewHolderClass, int i) {
        objectProductClass = objectModelClassList.get(i).first;
        dbHelper = new DBHelper(rvViewHolderClass.itemView.getContext());
        SessionManagement sessionManagement = new SessionManagement(rvViewHolderClass.itemView.getContext());
        String emailUser = sessionManagement.getSession();
        int q = dbHelper.getQuantityFromCart(emailUser, objectProductClass.getProdId());

        rvViewHolderClass.prodName.setText(objectProductClass.getProdName());
        String price = objectProductClass.getProdPrice() + " $";
        rvViewHolderClass.prodPrice.setText(price);
        rvViewHolderClass.prodQuantity.setText("Quantity: " + q);
        rvViewHolderClass.objectImageView.setImageBitmap(objectProductClass.getImage());

        rvViewHolderClass.itemView.setOnClickListener(v -> onItemListener.onItemClick(objectModelClassList.get(i).first));

        rvViewHolderClass.increase.setOnClickListener(v -> {
            dbHelper.increaseQuantityInShoppingCart(emailUser, objectProductClass.getProdId(),q+1);
            activity.startActivity(new Intent(activity, ShopCart.class));

        });

        rvViewHolderClass.decrease.setOnClickListener(v -> {
            dbHelper.decreaseQuantityInShoppingCart(emailUser, objectProductClass.getProdId(),q-1);
            activity.startActivity(new Intent(activity, ShopCart.class));
        });
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }


    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView prodName, prodPrice, prodQuantity;
        ImageButton increase, decrease;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.productName);
            prodPrice = itemView.findViewById(R.id.productPrice);
            objectImageView = itemView.findViewById(R.id.productImage);
            increase = itemView.findViewById(R.id.increase);
            decrease = itemView.findViewById(R.id.decrease);
            prodQuantity = itemView.findViewById(R.id.productQuantity);

        }
    }

    public interface OnItemListener{
        void onItemClick(Product product);
    }

}
