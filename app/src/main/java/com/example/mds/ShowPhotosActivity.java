package com.example.mds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

public class ShowPhotosActivity extends AppCompatActivity {

    private DBHelper objectDBHelper;
    private RecyclerView objectRecyclerView;
    private RVAdapter objectRVAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        System.out.println("Pe aici in show");
        try{
            objectRecyclerView=findViewById(R.id.imagesRV);
            objectDBHelper=new DBHelper(this);


        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void getData(View view){
        try{
            System.out.println("Pe aici in metoda show");
            objectRVAdapter = new RVAdapter(objectDBHelper.getAllProductsData());
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectRVAdapter);


        }catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}