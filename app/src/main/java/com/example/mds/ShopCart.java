package com.example.mds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ShopCart extends AppCompatActivity {

    Button order;
    TextView oneTime, daily, weekly, monthly, total;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        SessionManagement sessionManagement = new SessionManagement(ShopCart.this);
        String emailUser = sessionManagement.getSession();
        dbHelper = new DBHelper(this);

        Fragment fragment = ProductsFragmentOneTime.newInstance("shop");
        Fragment fragment1 = ProductsFragmentDaily.newInstance("shop");
        Fragment fragment2 = ProductsFragmentWeekly.newInstance("shop");
        Fragment fragment3 = ProductsFragmentMonthly.newInstance("shop");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutProdFragOneTime,fragment,"one_time_fragment");
        transaction.replace(R.id.layoutProdFragDaily,fragment1,"daily_fragment");
        transaction.replace(R.id.layoutProdFragWeekly,fragment2,"weekly_fragment");
        transaction.replace(R.id.layoutProdFragMonthly,fragment3,"monthly_fragment");
        transaction.commit();

        float onePrice = dbHelper.getPriceForCart(emailUser, "One time");
        float dPrice = dbHelper.getPriceForCart(emailUser, "Daily");
        float wPrice = dbHelper.getPriceForCart(emailUser, "Weekly");
        float mPrice = dbHelper.getPriceForCart(emailUser, "Monthly");

        oneTime = findViewById(R.id.totalOneTime);
        daily = findViewById(R.id.totalDaily);
        monthly = findViewById(R.id.totalMonthly);
        weekly = findViewById(R.id.totalWeekly);
        total = findViewById(R.id.totalOrder);

        oneTime.setText(oneTime.getText() + " " + onePrice + " $");
        daily.setText(daily.getText() + " " + dPrice + " $");
        weekly.setText(weekly.getText() + " " + wPrice + " $");
        monthly.setText(monthly.getText() + " " + mPrice + " $");
        total.setText(total.getText() + " " + (onePrice + dPrice + wPrice + mPrice) + " $");

        order = findViewById(R.id.order);
        order.setOnClickListener(v -> {
            dbHelper.deleteProductsFromCart(emailUser);
            Intent intent = new Intent(ShopCart.this, ClientHomeActivity.class);
            startActivity(intent);
        });
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
