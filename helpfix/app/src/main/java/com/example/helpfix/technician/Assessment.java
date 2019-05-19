package com.example.helpfix.technician;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

import static com.example.helpfix.technician.Assessment_list.JSON_ASSESSMENT_DT;
import static com.example.helpfix.technician.Assessment_list.JSON_ASSIGNER_BY;
import static com.example.helpfix.technician.Assessment_list.JSON_ID_ISSUE;
import static com.example.helpfix.technician.Assessment_list.JSON_LEVEL;
import static com.example.helpfix.technician.Assessment_list.JSON_PLACE;
import static com.example.helpfix.technician.Assessment_list.JSON_PRIORITY;
import static com.example.helpfix.technician.Assessment_list.JSON_PROBLEM;

public class Assessment extends AppCompatActivity {

    private static final String TAG = "Assessment";
    private TextView issueID ,Assessment_date ,Assigner_by , Priority , Problem ,Place ,Level ,USERID;
    private EditText Euipment_used ,Price;
    private Button btn_assessment ;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    private static final String URL_ASSESSMENT ="http://203.158.131.67/~devhelpfix/apphelpfix/assessment.php";
    private ImageView Pic_ISSUE;
    private RadioGroup userprice_radio_group;
    private LinearLayout UPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment);
        unit();
    }
    private void unit(){
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);

        USERID = findViewById(R.id.USERID);
        issueID = findViewById(R.id.issueID);
        Assessment_date = findViewById(R.id.Assessment_date);
        Assigner_by = findViewById(R.id.Assigner_by);
        Priority = findViewById(R.id.Priority);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Euipment_used = findViewById(R.id.Euipment_used);
        Price = findViewById(R.id.Price);
        Pic_ISSUE = findViewById(R.id.Pic_ISSUE);
        UPrice = findViewById(R.id.UPrice);
        userprice_radio_group = (RadioGroup) findViewById(R.id.userprice_radio_group);

        int selectedId = userprice_radio_group.getCheckedRadioButtonId();
        if (selectedId == R.id.btn_NouserPrice) {
            UPrice.setVisibility(View.GONE);
            Price.setText("0");
        }
        userprice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                int selectedId = userprice_radio_group.getCheckedRadioButtonId();

                if (selectedId == R.id.btn_NouserPrice) {
                    UPrice.setVisibility(View.GONE);
                    Price.setText("0");
                } else if (selectedId == R.id.btn_usePrice){
                    UPrice.setVisibility(View.VISIBLE);
                    Price.setText("");
                }
            }

        });


        btn_assessment = findViewById(R.id.btn_assessment);
        btn_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             UAssessment();
            }
        });

        Intent intent = getIntent();
        String JUSERID = intent.getStringExtra(JSON_ID_ISSUE);
        String JASESIGNER_BY = intent.getStringExtra(JSON_ASSIGNER_BY);
        String JASSESSMENT_DT = intent.getStringExtra(JSON_ASSESSMENT_DT );
        String JPRIORITY = intent.getStringExtra(JSON_PRIORITY);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mID = user.get(sessionManager.USERID);
        USERID.setText(mID);
        issueID.setText(JUSERID);
        Assessment_date.setText(JASSESSMENT_DT);
        Assigner_by.setText(JASESIGNER_BY);
        Priority.setText(JPRIORITY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        Lodingimage();

    }


    private void UAssessment(){
        final String sid_user = USERID.getText().toString().trim();
        final String seuipment_ud = Euipment_used.getText().toString().trim();
        final String sid_issue = issueID.getText().toString().trim();
        final String sprice = Price.getText().toString().trim();

        if (!seuipment_ud.isEmpty()){
            UEAssessment(sid_user , seuipment_ud , sid_issue , sprice);
        }else{
            Euipment_used.setError("กรุณากรอกอุปกรณ์ที่ใช้");
        }
    }

    private void UEAssessment(final String IDUser , final String EUIpment_UD , final String IDIssue , final String price ) {

        progressDialog.setMessage("Assessment in helpfix ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSESSMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Assignment Response: " + response.toString());
                        hideDialog();

                        Toast.makeText(Assessment.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Maintechnician.class);
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

                params.put("IDUser" , IDUser);
                params.put("EUIpment_UD" ,EUIpment_UD );
                params.put("IDIssue" ,IDIssue );
                params.put("price" ,price );
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
                loading = ProgressDialog.show(Assessment.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                Pic_ISSUE.setImageBitmap(b);
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
