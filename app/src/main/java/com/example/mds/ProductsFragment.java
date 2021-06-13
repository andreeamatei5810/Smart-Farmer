
package com.example.mds;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mds.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment implements RVAdapter.OnItemListener{

    private ArrayList<Product> list = new ArrayList<>();
    private DBHelper dbHelper;
    private String emailFarmer = null;
    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();
        return fragment;
    }

    public void setEmailFarmer(String emailFarmer){
        this.emailFarmer = emailFarmer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        dbHelper = new DBHelper(this.getContext());
        initList();
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recViewProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RVAdapter adapter = new RVAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }

    private void initList(){
        if(emailFarmer == null) {
            list = dbHelper.getAllProductsData();
        }else{
            list = dbHelper.getAllProductsByEmail(emailFarmer);
        }
    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(getView().getContext(),ProductDetailsActivity.class);
        intent.putExtra("idProduct",product.getProdId());
        getView().getContext().startActivity(intent);
    }
}