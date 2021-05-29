package chat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mds.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    DBHelper database;
    ImageButton sendImageButton;
    EditText newMessage;
    TextView usernameReceiver;
    ListView listOfMessages;
    Button backToChatContacts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = new DBHelper(this);
        SessionManagement sessionManagement = new SessionManagement(ChatActivity.this);
        String emailUser = sessionManagement.getSession();
        Intent pastIntent = getIntent();
        String emailReceiver = pastIntent.getStringExtra("emailReceiver");
        System.out.println(emailReceiver);
        database.readAllMessages(emailUser);
        ArrayList<String> allMessages = database.getMessages(emailUser, emailReceiver);
        usernameReceiver = findViewById(R.id.usernameReceiver);
        usernameReceiver.setText(emailReceiver);
        ShowAllMessages(emailUser,allMessages);
        sendImageButton = findViewById(R.id.sendImageButton);
        newMessage = findViewById(R.id.messageToSent);

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String messageSend = String.valueOf(newMessage.getText());
                if (!messageSend.equals("")) {
                    database.insertMessage(messageSend, emailUser, emailReceiver);
                    Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
                    intent.putExtra("emailReceiver", emailReceiver);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Empty Message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToChatContacts = findViewById(R.id.backToChatContacts);
        backToChatContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ChatContactActivity.class);
                startActivity(intent);
                }
        });
    }

    public int getItemViewType(String emailCurrentUser, String email) {

        if (email.equals(emailCurrentUser)) {
            return -1; // message will show on left
        } else {
            return 1; // message will show on right
        }
    }

    private void ShowAllMessages(String emailUser, ArrayList<String> allMessages) {
        listOfMessages = findViewById(R.id.messages);
        final ArrayList<ListTypeChat> listTypeChats = new ArrayList<>();
        int i;
        String oldDate= "0000-00-00" ;
        for(i = 0;i<allMessages.size();i++){
            String[] elementsMessage = allMessages.get(i).split("///");
            if(!elementsMessage[1].substring(0,10).equals(oldDate)){
                listTypeChats.add(new ListTypeChat(elementsMessage[1].substring(0,10),ChatAdapter.TYPE_CENTER));
            }
            oldDate = elementsMessage[1].substring(0,10);
            if(getItemViewType(emailUser,elementsMessage[2]) == -1){
                listTypeChats.add(new ListTypeChat(elementsMessage[0],ChatAdapter.TYPE_LEFT));
            }
            else{
                listTypeChats.add(new ListTypeChat(elementsMessage[0],ChatAdapter.TYPE_RIGHT));
            }
        }
        System.out.println(listTypeChats);
        ChatAdapter customAdapter = new ChatAdapter(this, R.id.text, listTypeChats);
        listOfMessages.setAdapter(customAdapter);
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
