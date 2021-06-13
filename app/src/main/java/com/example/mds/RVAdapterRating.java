package com.example.mds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mds.model.Rating;

import java.util.ArrayList;

public class RVAdapterRating extends RecyclerView.Adapter<RVAdapterRating.RVViewHolderClass>{

    ArrayList<Rating> ratingsList;


    public RVAdapterRating(ArrayList<Rating> ratingsList) {
        this.ratingsList = ratingsList;
    }

    @NonNull
    @Override
    public RVAdapterRating.RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.rating,viewgroup,false);
        return new RVAdapterRating.RVViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RVAdapterRating.RVViewHolderClass rvViewHolderClass, int i) {
        Rating rating = ratingsList.get(i);

        rvViewHolderClass.ratingContent.setText(rating.getContent());
        rvViewHolderClass.userName.setText(rating.getUserEmail());
        rvViewHolderClass.ratingBar.setRating((float) rating.getNoStars());

    }

    @Override
    public int getItemCount() {
        if (ratingsList == null){
            return 0;
        }
        else {
            return ratingsList.size();
        }
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView userName,ratingContent;
        RatingBar ratingBar;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.ratingUserName);
            ratingContent = itemView.findViewById(R.id.ratingContent);
            ratingBar = itemView.findViewById(R.id.ratingBarGiven);
        }
    }

}
