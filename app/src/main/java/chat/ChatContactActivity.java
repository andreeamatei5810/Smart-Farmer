package chat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mds.DBHelper;
import com.example.mds.MainActivity;
import com.example.mds.R;

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
        String emailUser = pastIntent.getStringExtra("email");
        if (emailUser == null) {
            Toast.makeText(getApplicationContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ChatContactActivity.this, MainActivity.class);
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
                            intent.putExtra("email", emailUser);
                            intent.putExtra("emailReceiver", allContacts.get(finalI));
                            startActivity(intent);
                        }
                    });
                    cl.addView(textView);
                }
            }
            newMessageButton = findViewById(R.id.newMessage);
            newMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NewMessageActivity.class);
                    intent.putExtra("email", emailUser);
                    startActivity(intent);
                }
            });

            backToMain = findViewById(R.id.backToMain);
            backToMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatContactActivity.this, MainActivity.class);
                    intent.putExtra("email", emailUser);
                    startActivity(intent);
                }
            });
        }
    }
}
