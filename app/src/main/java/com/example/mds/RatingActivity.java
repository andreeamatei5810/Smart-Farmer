package com.example.mds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.etFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);
        dbHelper = new DBHelper(this);
        Intent pastIntent = getIntent();
        String comment = pastIntent.getStringExtra("email");
        //Int Stars = pastIntent.getStringExtra("emailReceiver");

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

       /* mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(RatingActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();

                } else {
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(RatingActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    dbHelper.insertRating(messageSend, emailUser, emailReceiver);
                    Intent intent = new Intent(RatingActivity.this, RatingActivity.class);
                    intent.putExtra("email", emailUser);
                    intent.putExtra("emailReceiver", emailReceiver);
                    startActivity(intent);
                }
            }
        });*/

    }
}
