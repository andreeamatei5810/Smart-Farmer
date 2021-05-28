package chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mds.DBHelper;
import com.example.mds.R;
import com.example.mds.SessionManagement;
import com.example.mds.UserProfile;

import java.util.ArrayList;

public class NewMessageActivity  extends AppCompatActivity {

    Button newMessage;
    EditText emailMessage;
    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        database = new DBHelper(this);
        Intent pastIntent = getIntent();
        SessionManagement sessionManagement = new SessionManagement(NewMessageActivity.this);
        String emailUser = sessionManagement.getSession();
        emailMessage = findViewById(R.id.editEmailSend);

        newMessage = findViewById(R.id.sentEmailToButton);
        newMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailReceived = String.valueOf(emailMessage.getText());
                ArrayList<String> str = database.getUserInfo(emailReceived);
                if (str.size() != 4) {
                    Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NewMessageActivity.this, ChatContactActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(NewMessageActivity.this, ChatActivity.class);
                    intent.putExtra("emailReceiver", emailMessage.getText().toString());
                    startActivity(intent);
                }
            }
            });


    }
}
