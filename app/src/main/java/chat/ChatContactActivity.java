package chat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mds.*;

import java.util.ArrayList;

public class ChatContactActivity extends AppCompatActivity{

    DBHelper database;
    Button newMessageButton;
    Button backToMain;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_contact);
        database = new DBHelper(this);
        Intent pastIntent = getIntent();
        SessionManagement sessionManagement = new SessionManagement(ChatContactActivity.this);
        String emailUser = sessionManagement.getSession();
        if (emailUser == null) {
            Toast.makeText(getApplicationContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ChatContactActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            ArrayList<String> allContacts = database.getContacts(emailUser);
            final LinearLayout cl = findViewById(R.id.chatLayout);
            if(allContacts.size() <= 0){
                TextView textView = new TextView(this);
                textView.setText("No message was send/receive!");
                textView.setTextSize(20);
                textView.setPadding(100, 50, 10, 0);
                cl.addView(textView);
            }
            else {
                for (int i = 0; i < allContacts.size(); i++) {
                    TextView textView = new TextView(this);
                    textView.setText(allContacts.get(i));
                    textView.setTextSize(20);
                    int finalI = i;
                    if(database.checkReadMessages(allContacts.get(i),emailUser) == 1){
                        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                    }
                    else{
                        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
                    }
                    textView.setPadding(100, 20, 10, 0);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ChatContactActivity.this, ChatActivity.class);
                            intent.putExtra("emailReceiver", allContacts.get(finalI));
                            startActivity(intent);
                        }
                    });
                    cl.addView(textView);
                }
            }


            backToMain = findViewById(R.id.backToMain);
            backToMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = database.getUser(emailUser);
                    cursor.moveToFirst();
                    if(cursor.getString(4).equals("client")){
                        Intent intent = new Intent(ChatContactActivity.this, ClientHomeActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(ChatContactActivity.this, FarmerHomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
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
