package com.example.mds;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import chat.ChatActivity;
import chat.ChatContactActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class FarmerProfileActivity extends AppCompatActivity {


    Button buttonChat;
    //Button rating;
    String farmerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
        SessionManagement sessionManagement = new SessionManagement(FarmerProfileActivity.this);
        String emailUserLog = sessionManagement.getSession();
        Intent intent = getIntent();
        farmerEmail = intent.getStringExtra("emailFarmer");
        if (emailUserLog == null) {
            Toast.makeText(getApplicationContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(FarmerProfileActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            DBHelper database = new DBHelper(this);


            TextView name = findViewById(R.id.textViewName);

            TabLayout tabLayout = findViewById(R.id.tabLayout);
            TabItem tabProd = findViewById(R.id.tabProducts);
            TabItem tabRev = findViewById(R.id.tabRatings);
            ViewPager viewPager = findViewById(R.id.viewPager);
            PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

            viewPager.setAdapter(pagerAdapter);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            Cursor cursor = database.getUser(farmerEmail);
            cursor.moveToFirst();
            name.setText(cursor.getString(2));

            Cursor cursor1 = database.getUser(emailUserLog);
            cursor1.moveToFirst();


            buttonChat = findViewById(R.id.buttonChat);
            buttonChat.setOnClickListener(v -> {
                if (cursor1.getString(4).equals("client")) {
                    Intent intent1 = new Intent(FarmerProfileActivity.this, ChatActivity.class);
                    intent1.putExtra("emailReceiver", farmerEmail);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(FarmerProfileActivity.this, ChatContactActivity.class);
                    startActivity(intent1);
                }
            });

            /*rating = findViewById(R.id.ratingBar);
            rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FarmerProfileActivity.this, RatingsFragment.class));
                }
            });*/


        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), GoHome.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}