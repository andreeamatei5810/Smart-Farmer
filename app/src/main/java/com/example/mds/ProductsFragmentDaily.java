
package com.example.mds;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mds.model.Product;

import java.util.ArrayList;

public class ProductsFragmentDaily extends Fragment implements RVAdapterShop.OnItemListener, RVAdapterSubs.OnItemListener{

    private ArrayList<Pair<Product, Integer>> list = new ArrayList<>();
    private DBHelper dbHelper;
    private ArrayList<Pair<Product, Pair<Integer, String>>> list1 = new ArrayList<>();
    static String type;
    public ProductsFragmentDaily() {
        // Required empty public constructor
    }

    public static ProductsFragmentDaily newInstance(String type1) {
        ProductsFragmentDaily fragment = new ProductsFragmentDaily();
        type = type1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        dbHelper = new DBHelper(this.getContext());
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recViewProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        SessionManagement sessionManagement = new SessionManagement(getContext());
        String emailUserLog = sessionManagement.getSession();
        if(type.equals("shop")) {
            list = dbHelper.getAllProductsFromShopCart(emailUserLog, "Daily");
            RVAdapterShop adapter = new RVAdapterShop(list, this, getActivity());
            recyclerView.setAdapter(adapter);
        }
        else{
            Cursor cursor = dbHelper.getUser(emailUserLog);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                if (cursor.getString(4).equals("client")) {
                    list1 = dbHelper.getAllProductsFromSubs(emailUserLog, "Daily");
                }
                else{
                    list1 = dbHelper.getAllProductsFromSubsForFarmer(emailUserLog, "Daily");
                }
                RVAdapterSubs adapter = new RVAdapterSubs(list1, this, getActivity());
                recyclerView.setAdapter(adapter);
            }

        }
    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(getView().getContext(),ProductDetailsActivity.class);
        intent.putExtra("idProduct",product.getProdId());
        getView().getContext().startActivity(intent);
    }
}