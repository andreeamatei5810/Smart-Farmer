package com.example.mds;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mds.model.Product;
import com.example.mds.model.Rating;

import java.util.ArrayList;


public class RatingsFragment extends Fragment {

    private ArrayList<Rating> list = new ArrayList<>();
    private DBHelper dbHelper;
    private String emailUser = null;
    private String emailFarmer = null;
    EditText content;
    Button rating;
    RatingBar ratingBar;

    public RatingsFragment() {
        // Required empty public constructor
    }


    public static RatingsFragment newInstance() {
        RatingsFragment fragment = new RatingsFragment();
        return fragment;
    }

    public void setEmailFarmer(String emailFarmer){
        this.emailFarmer = emailFarmer;
    }

    public void setEmailUser(String emailFarmer){
        this.emailUser = emailFarmer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ratings, container, false);
        rating = view.findViewById(R.id.buttonSaveRating);
        content = view.findViewById(R.id.ratingContentUser);
        ratingBar = view.findViewById(R.id.ratingBarUser);

        dbHelper = new DBHelper(this.getContext());

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString() != ""){
                    System.out.println("Am ajuns la adaugarea produsului");
                    Rating rating = new Rating(emailUser,emailFarmer,content.getText().toString(),ratingBar.getRating());
                    dbHelper.addRating(rating);
                    Toast.makeText(view.getContext(), "Added rating", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initList();
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recViewRatings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RVAdapterRating adapter = new RVAdapterRating(list);
        recyclerView.setAdapter(adapter);
    }

    private void initList(){
        System.out.println(list);
        System.out.println("email fermier cu rating");
        list = dbHelper.getAllRatings(emailFarmer);
        System.out.println(list);
    }
}