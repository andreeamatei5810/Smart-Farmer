package com.example.mds;

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
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class FarmerProfileActivity extends AppCompatActivity {


    Button buttonChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
        Intent pastIntent = getIntent();
        String userEmail = pastIntent.getStringExtra("email");
        if (userEmail == null) {
            Toast.makeText(getApplicationContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(FarmerProfileActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            DBHelper database = new DBHelper(this);
            TextView name = (TextView) findViewById(R.id.textViewName);

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

            Cursor cursor = database.getClient(userEmail);
            cursor.moveToFirst();
            name.setText(cursor.getString(2));


            buttonChat = findViewById(R.id.buttonChat);
            buttonChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FarmerProfileActivity.this, ChatActivity.class);
                    intent.putExtra("email", userEmail);
                    /* intent.putExtra("emailReceiver", emailMessage.getText().toString());
                    startActivity(intent); */
                }
            });

        }
    }
}