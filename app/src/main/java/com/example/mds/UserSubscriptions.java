package com.example.mds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class UserSubscriptions extends AppCompatActivity {

    Button order;
    TextView oneTime, daily, weekly, monthly, total;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs);
        SessionManagement sessionManagement = new SessionManagement(UserSubscriptions.this);
        String emailUser = sessionManagement.getSession();
        dbHelper = new DBHelper(this);

        Fragment fragment1 = ProductsFragmentDaily.newInstance("subs");
        Fragment fragment2 = ProductsFragmentWeekly.newInstance("subs");
        Fragment fragment3 = ProductsFragmentMonthly.newInstance("subs");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutProdFragDaily,fragment1,"daily_fragment");
        transaction.replace(R.id.layoutProdFragWeekly,fragment2,"weekly_fragment");
        transaction.replace(R.id.layoutProdFragMonthly,fragment3,"monthly_fragment");
        transaction.commit();
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