package com.example.helpfix.include_help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpfix.R;
import com.example.helpfix.session_login.SessionManager;

import java.util.HashMap;

public class Sentemail extends AppCompatActivity {

    private TextView sentTo , name;
    private EditText edt_subject ,edt_message;
    private Button btn_sent;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentemail);

        unit();
    }

    private void unit() {
        sessionManager = new SessionManager(this);
        name = findViewById(R.id.name);
        sentTo = findViewById(R.id.sentTo);
        edt_subject = findViewById(R.id.edt_subject);
        edt_message = findViewById(R.id.edt_message);
        btn_sent = findViewById(R.id.btn_Sent);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);

        name.setText(mName);

        sentTo.setText("piyapong.sab.it58@cpc.ac.th");

        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentMail();
            }
        });
    }
        private void sentMail() {
        String recipientList = sentTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = edt_subject.getText().toString().trim();
        String message = edt_message.getText().toString().trim();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL , recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT , subject);
            intent.putExtra(Intent.EXTRA_TEXT , message);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent , "Choose an email client"));

        }

}
