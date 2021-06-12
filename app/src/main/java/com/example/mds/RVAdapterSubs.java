package com.example.mds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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


public class RVAdapterSubs extends RecyclerView.Adapter<RVAdapterSubs.RVViewHolderClass> {



    ArrayList<Pair<Product, Pair<Integer, String>>> objectModelClassList;
    Product objectProductClass;
    OnItemListener onItemListener;
    DBHelper dbHelper;
    Activity activity;



    public RVAdapterSubs(ArrayList<Pair<Product, Pair<Integer, String>>> objectModelClassList, OnItemListener onItemListener, Activity activity) {
        this.objectModelClassList = objectModelClassList;
        this.onItemListener = onItemListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull  ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.single_row_subs,viewgroup,false);
        return new RVViewHolderClass(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  RVAdapterSubs.RVViewHolderClass rvViewHolderClass, int i) {
        objectProductClass = objectModelClassList.get(i).first;
        dbHelper = new DBHelper(rvViewHolderClass.itemView.getContext());
        SessionManagement sessionManagement = new SessionManagement(rvViewHolderClass.itemView.getContext());
        String emailUser = sessionManagement.getSession();
        int q = objectModelClassList.get(i).second.first;
        String client = objectModelClassList.get(i).second.second;
        rvViewHolderClass.prodName.setText(objectProductClass.getProdName());
        String price = objectProductClass.getProdPrice() + " $";
        rvViewHolderClass.prodPrice.setText(price);
        rvViewHolderClass.prodQuantity.setText("Quantity: " + q);
        Cursor cursor = dbHelper.getUser(emailUser);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            if (cursor.getString(4).equals("client")) {
                rvViewHolderClass.client.setText("Client: " + emailUser);
                rvViewHolderClass.delete.setOnClickListener(v -> {
                    dbHelper.deleteProductFromSubsClient(emailUser, objectProductClass.getProdId());
                    activity.startActivity(new Intent(activity, UserSubscriptions.class));

                });
            } else {
                rvViewHolderClass.client.setText("Client: " + client);
                rvViewHolderClass.delete.setOnClickListener(v -> {
                    dbHelper.deleteProductFromSubsClient(client, objectProductClass.getProdId());
                    activity.startActivity(new Intent(activity, UserSubscriptions.class));
                });
            }
        }
        rvViewHolderClass.farmer.setText("Farmer: " + objectProductClass.getFarmerId());

        rvViewHolderClass.objectImageView.setImageBitmap(objectProductClass.getImage());

        rvViewHolderClass.itemView.setOnClickListener(v -> onItemListener.onItemClick(objectModelClassList.get(i).first));

    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }


    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView prodName, prodPrice, prodQuantity, farmer, client;
        ImageButton delete;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.productName);
            prodPrice = itemView.findViewById(R.id.productPrice);
            prodQuantity = itemView.findViewById(R.id.productQuantity);
            objectImageView = itemView.findViewById(R.id.productImage);
            delete = itemView.findViewById(R.id.delete);
            client = itemView.findViewById(R.id.productClient);
            farmer = itemView.findViewById(R.id.productFarmer);

        }
    }

    public interface OnItemListener{
        void onItemClick(Product product);
    }

}
