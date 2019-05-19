package com.example.helpfix.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;
import com.example.helpfix.session_login.SessionManager;

import java.util.HashMap;
import java.util.Map;

import static com.example.helpfix.manager.Pending_list.JSON_APPROVE_DATE;
import static com.example.helpfix.manager.Pending_list.JSON_ASSESSMENT_BY;
import static com.example.helpfix.manager.Pending_list.JSON_CREATE_BY;
import static com.example.helpfix.manager.Pending_list.JSON_EUIPMENT_USED;
import static com.example.helpfix.manager.Pending_list.JSON_ID_ISSUE;
import static com.example.helpfix.manager.Pending_list.JSON_PLACE;
import static com.example.helpfix.manager.Pending_list.JSON_PRICE;
import static com.example.helpfix.manager.Pending_list.JSON_PROBLEM;
import static com.example.helpfix.manager.Pending_list.JSON_SENT_APP_NAME;

public class Managepending extends AppCompatActivity {

    private static final String TAG = "Managepending";
    private TextView issueID ,Createby ,Problem , Place , Assessment_by ,Euipment_used ,Price ,Approve_date ,sentapprove_by ,USERID;
    private Button btn_approve , btn_noapprove;
    private static final String URL_APPROVE ="http://203.158.131.67/~devhelpfix/apphelpfix/manager_approve.php";
    private static final String URL_NOAPPROVE ="http://203.158.131.67/~devhelpfix/apphelpfix/manager_noapprove.php";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managepending);

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
        btn_approve = findViewById(R.id.btn_approve);
        btn_noapprove = findViewById(R.id.btn_noapprove);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.USERID);
        USERID.setText(mName);

        Intent intent = getIntent();
        String JUSERID = intent.getStringExtra(JSON_ID_ISSUE);
        String JAPPOVE_DT = intent.getStringExtra(JSON_APPROVE_DATE);
        String JSENT_APPNAME = intent.getStringExtra(JSON_SENT_APP_NAME );
        String JPREICE = intent.getStringExtra(JSON_PRICE);
        String JCREATE_BY = intent.getStringExtra(JSON_CREATE_BY);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JASSESSMENT_BY = intent.getStringExtra(JSON_ASSESSMENT_BY);
        String JEUIPMENT_UD = intent.getStringExtra(JSON_EUIPMENT_USED);

        issueID.setText(JUSERID);
        Createby.setText(JCREATE_BY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Assessment_by.setText(JASSESSMENT_BY);
        Euipment_used.setText(JEUIPMENT_UD);
        Price.setText(JPREICE);
        Approve_date.setText(JAPPOVE_DT);
        sentapprove_by.setText(JSENT_APPNAME);



        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Approve();
            }
        });
        btn_noapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOApprove();
            }
        });
    }
    private void Approve () {

        final String id_issue = issueID.getText().toString().trim();
        final String Approve_by = USERID.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "APPROVE Response: " + response.toString());
                //Toast.makeText(Managepending.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Mainmanager.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Managepending.this, "APPROVE Error!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("id_issue", id_issue);
                params.put("Approve_by", Approve_by);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void NOApprove () {

        final String id_issue = issueID.getText().toString().trim();
        final String Approve_by = USERID.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NOAPPROVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "NOAPPROVE Response: " + response.toString());
                        //Toast.makeText(Managepending.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Mainmanager.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Managepending.this, "NOAPPROVE Error!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("id_issue", id_issue);
                params.put("Approve_by", Approve_by);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {

    }
}

