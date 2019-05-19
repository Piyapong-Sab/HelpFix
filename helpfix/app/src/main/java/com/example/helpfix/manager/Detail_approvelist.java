package com.example.helpfix.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.helpfix.R;
import com.example.helpfix.session_login.SessionManager;

import java.util.HashMap;

import static com.example.helpfix.manager.Approve_list.JSON_APPROVE_STATUS;
import static com.example.helpfix.manager.Pending_list.JSON_APPROVE_DATE;
import static com.example.helpfix.manager.Pending_list.JSON_ASSESSMENT_BY;
import static com.example.helpfix.manager.Pending_list.JSON_CREATE_BY;
import static com.example.helpfix.manager.Pending_list.JSON_EUIPMENT_USED;
import static com.example.helpfix.manager.Pending_list.JSON_ID_ISSUE;
import static com.example.helpfix.manager.Pending_list.JSON_PLACE;
import static com.example.helpfix.manager.Pending_list.JSON_PRICE;
import static com.example.helpfix.manager.Pending_list.JSON_PROBLEM;
import static com.example.helpfix.manager.Pending_list.JSON_SENT_APP_NAME;

public class Detail_approvelist extends AppCompatActivity {

    private TextView issueID ,Createby ,Problem , Place , Assessment_by ,Euipment_used ,Price ,Approve_date ,sentapprove_by ,USERID, Approve_status;
    private Button btn_backhome ;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_approvelist);
        unit();
    }

    private void unit() {
        sessionManager = new SessionManager(this);
        issueID = findViewById(R.id.issueID);
        Createby = findViewById(R.id.Createby);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Assessment_by = findViewById(R.id.Assessment_by);
        Euipment_used = findViewById(R.id.Euipment_used);
        Price = findViewById(R.id.Price);
        Approve_date = findViewById(R.id.Approve_date);
        sentapprove_by = findViewById(R.id.sentapprove_by);
        USERID = findViewById(R.id.USERID);
        Approve_status = findViewById(R.id.Approve_status);
        btn_backhome = findViewById(R.id.btn_backhome);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.USERID);
        USERID.setText(mName);

        Intent intent = getIntent();
        String JISSUE = intent.getStringExtra(JSON_ID_ISSUE);
        String JAPPOVE_DT = intent.getStringExtra(JSON_APPROVE_DATE);
        String JSENT_APPNAME = intent.getStringExtra(JSON_SENT_APP_NAME );
        String JPREICE = intent.getStringExtra(JSON_PRICE);
        String JCREATE_BY = intent.getStringExtra(JSON_CREATE_BY);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JASSESSMENT_BY = intent.getStringExtra(JSON_ASSESSMENT_BY);
        String JEUIPMENT_UD = intent.getStringExtra(JSON_EUIPMENT_USED);
        String JAPPROVE_STATUS = intent.getStringExtra(JSON_APPROVE_STATUS);

        issueID.setText(JISSUE);
        Createby.setText(JCREATE_BY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Assessment_by.setText(JASSESSMENT_BY);
        Euipment_used.setText(JEUIPMENT_UD);
        Price.setText(JPREICE);
        Approve_date.setText(JAPPOVE_DT);
        sentapprove_by.setText(JSENT_APPNAME);
        Approve_status.setText(JAPPROVE_STATUS);

        btn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),Mainmanager.class);
                startActivity(back);
                finish();
            }
        });
    }
}
