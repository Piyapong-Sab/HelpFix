package com.example.helpfix.head_division;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.helpfix.head_division.Sentapproval_list.JSON_ASSESSMENTBY;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_ASSESSMENTDT;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_CREATEBY;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_EUIPMENTUS;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_EVALUATEDT;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_ID_ISSUE;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_LEVEL;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_PLACE;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_PRICE;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_PRIORITY;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_PROBLEM;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_RECORDDT;
import static com.example.helpfix.head_division.Sentapproval_list.JSON_STATUS;

public class Sentapproval extends AppCompatActivity {

    private static final String TAG = "Sentapproval";
   private TextView issueID,Createdate ,Create_by ,Problem ,Place ,Level ,Assessment_date ,Priority,Evaluate_date ,Assessment_by ,Euipment_used ,Price ,USERID;
   private Button btn_sentapprove , btn_sentnoapprove;
   private final static String SENTAPP ="http://203.158.131.67/~devhelpfix/apphelpfix/sentapproval.php";
   private final static String SENTNOAPP ="http://203.158.131.67/~devhelpfix/apphelpfix/sentno_approval.php";
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    private ImageView Pic_issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentapproval);
        unit();
    }
    private void unit(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);

        sessionManager = new SessionManager(this);
        issueID = findViewById(R.id.issueID);
        Createdate = findViewById(R.id.Createdate);
        Create_by = findViewById(R.id.Create_by);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Assessment_date = findViewById(R.id.Assessment_date);
        Priority = findViewById(R.id.Priority);
        Evaluate_date = findViewById(R.id.Evaluate_date);
        Assessment_by = findViewById(R.id.Assessment_by);
        Euipment_used = findViewById(R.id.Euipment_used);
        Price = findViewById(R.id.Price);
        Pic_issue = findViewById(R.id.Pic_issue);
        USERID = findViewById(R.id.USERID);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mUSERID = user.get(sessionManager.USERID);
        USERID.setText(mUSERID);

        btn_sentapprove = findViewById(R.id.btn_sentapprove);
        btn_sentnoapprove = findViewById(R.id.btn_sentnoapprove);

        Intent intent = getIntent();
        String JIDISSUE= intent.getStringExtra(JSON_ID_ISSUE);
        String JRECORDDATE = intent.getStringExtra(JSON_RECORDDT);
        String JCREATEBY = intent.getStringExtra(JSON_CREATEBY );
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);
        String JASSESSMENTDT = intent.getStringExtra(JSON_ASSESSMENTDT);
        String JPRIORITY = intent.getStringExtra(JSON_PRIORITY);
        String JASSESSMENTBY = intent.getStringExtra(JSON_ASSESSMENTBY);
        String JEVALUATEDT= intent.getStringExtra(JSON_EVALUATEDT);
        String JEUIPMENTUS= intent.getStringExtra(JSON_EUIPMENTUS);
        String JPRICE = intent.getStringExtra(JSON_PRICE);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);

        issueID.setText(JIDISSUE);
        Createdate.setText(JRECORDDATE);
        Create_by.setText(JCREATEBY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        Assessment_date.setText(JASSESSMENTDT);
        Priority.setText(JPRIORITY);
        Evaluate_date.setText(JEVALUATEDT);
        Assessment_by.setText(JASSESSMENTBY);
        Euipment_used.setText(JEUIPMENTUS);
        Price.setText(JPRICE);
        Lodingimage();
        btn_sentapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentapprove();
            }
        });

        btn_sentnoapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentNoapprove();
            }
        });

    }
    private void sentapprove(){

        final String sid_user = USERID.getText().toString().trim();
        final String sid_issue = issueID.getText().toString().trim();

        SentApproval(sid_user ,sid_issue);

    }
    private void sentNoapprove(){
        final String sid_user = USERID.getText().toString().trim();
        final String sid_issue = issueID.getText().toString().trim();

        SentNoApproval(sid_user ,sid_issue);
    }

    private void SentApproval (final String IDUSER , final String IDISSUE){

        progressDialog.setMessage("Sentapproval in helpfix ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SENTAPP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Sentapproval Response: " + response.toString());
                        hideDialog();

                        Toast.makeText(Sentapproval.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Mainhead_division.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();

                params.put("IDUSER" , IDUSER);
                params.put("IDISSUE" ,IDISSUE );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void SentNoApproval (final String IDUSER , final String IDISSUE){

        progressDialog.setMessage("SentNOapproval in helpfix ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SENTNOAPP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Sentapproval Response: " + response.toString());
                        hideDialog();

                        Toast.makeText(Sentapproval.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Mainhead_division.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();

                params.put("IDUSER" , IDUSER);
                params.put("IDISSUE" ,IDISSUE );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
    private void Lodingimage() {
        String id = issueID.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Sentapproval.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                Pic_issue.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://203.158.131.67/~devhelpfix/apphelpfix/view_detail_issue.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }
}
