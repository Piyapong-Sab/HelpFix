package com.example.helpfix.head_division;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.example.helpfix.adapter_head.ModelNameTechnician;
import com.example.helpfix.session_login.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.example.helpfix.head_division.Assignment_list.JSON_ID_ISSUE;
import static com.example.helpfix.head_division.Assignment_list.JSON_LEVEL;
import static com.example.helpfix.head_division.Assignment_list.JSON_MCATEGORY;
import static com.example.helpfix.head_division.Assignment_list.JSON_NAME;
import static com.example.helpfix.head_division.Assignment_list.JSON_PLACE;
import static com.example.helpfix.head_division.Assignment_list.JSON_PROBLEM;
import static com.example.helpfix.head_division.Assignment_list.JSON_RECORDDATE;
import static com.example.helpfix.head_division.Assignment_list.JSON_SCATEGORY;
import static com.example.helpfix.head_division.Assignment_list.JSON_STATUS;

public class Assignment extends AppCompatActivity {

    private static final String TAG = "Assignment";
    private static final String URL_ASSIGNMENT = "http://203.158.131.67/~devhelpfix/apphelpfix/assignment.php";
    private TextView issueID , Create_date,Createby,subCategory,Category ,Problem ,Place ,Level ,USERID , TXT_Assignment;
    private ImageView Pic_issue;
    private Button btn_assignment;
    private RadioGroup priority_radio_group;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    private static ProgressDialog mProgressDialog;
    private String URL_GETTECHNICIAN = "http://203.158.131.67/~devhelpfix/apphelpfix/get_technician.php";
    private ArrayList<ModelNameTechnician> NameModelArrayList;
    private ArrayList<String> NameTechnician = new ArrayList<String>();
    private Spinner SAssignment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment);

        unit();
    }
    private void unit(){
        sessionManager = new SessionManager(this);
        HashMap<String , String> user = sessionManager.getUserDetail();
        String mUserID = user.get(sessionManager.USERID);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);
        TXT_Assignment = findViewById(R.id.TXT_Assignment);
        issueID = findViewById(R.id.issueID);
        Create_date = findViewById(R.id.Create_date);
        Createby = findViewById(R.id.Createby);
        subCategory = findViewById(R.id.subCategory);
        Category = findViewById(R.id.Category);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        USERID = findViewById(R.id.USERID);
        Pic_issue = findViewById(R.id.Pic_issue);
        SAssignment = findViewById(R.id.SAssignment);
        btn_assignment = findViewById(R.id.btn_assignment);
        priority_radio_group = (RadioGroup) findViewById(R.id.priority_radio_group);

        Intent intent = getIntent();
        String JIDISSUE= intent.getStringExtra(JSON_ID_ISSUE);
        String JRECORDDATE = intent.getStringExtra(JSON_RECORDDATE);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM );
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);
        String JNAME = intent.getStringExtra(JSON_NAME);
        String JMCATEGORY = intent.getStringExtra(JSON_MCATEGORY);
        String JSCATEGORY = intent.getStringExtra(JSON_SCATEGORY);

        issueID.setText(JIDISSUE);
        Create_date.setText(JRECORDDATE);
        Createby.setText(JNAME);
        subCategory.setText(JSCATEGORY);
        Category.setText(JMCATEGORY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        USERID.setText(mUserID);

        Lodingimage();
        btn_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assignment ();
            }
        });
        CATEGORYJSON();
        SAssignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ASSIGNMENT =   SAssignment.getItemAtPosition(SAssignment.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),country ,Toast.LENGTH_LONG).show();
                TXT_Assignment.setText(ASSIGNMENT);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }
    private void CATEGORYJSON() {
        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GETTECHNICIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("NAME TECHNICIAN", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                NameModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    ModelNameTechnician playerModel = new ModelNameTechnician();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setId_user(dataobj.getString("Id_user"));
                                    playerModel.setName(dataobj.getString("Name"));

                                    NameModelArrayList.add(playerModel);

                                }

                                for (int i = 0; i < NameModelArrayList.size(); i++){
                                    NameTechnician.add(NameModelArrayList.get(i).getId_user().toString() + " " +
                                            NameModelArrayList.get(i).getName().toString());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Assignment.this, simple_spinner_item, NameTechnician);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                SAssignment.setAdapter(spinnerArrayAdapter);
                                removeSimpleProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Assignment (){

        int selectedId = priority_radio_group.getCheckedRadioButtonId();
        final String priority;
        if (selectedId == R.id.btn_radio_veryquick) {
            priority = "ด่วนมาก";
        } else if (selectedId == R.id.btn_radio_urgent){
            priority = "ด่วนที่สุด";
        }else{
            priority = "ด่วน";
        }

        final String sid_user = USERID.getText().toString().trim();
        final String sassignment = TXT_Assignment.getText().toString().trim();
        final String sid_issue = issueID.getText().toString().trim();

        if (!sassignment.isEmpty()){
            Assignmentby(sid_user , sassignment , sid_issue , priority);
        }else{
            TXT_Assignment.setError("เลือกช่างที่รับผิดชอบ");
        }

    }
    private void Assignmentby(final String IDUser , final String assign_by , final String IDIssue , final String priority ){

        progressDialog.setMessage("Assignment in helpfix ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGNMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Assignment Response: " + response.toString());
                        hideDialog();

                        Toast.makeText(Assignment.this, "SUCCESS", Toast.LENGTH_SHORT).show();
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

                params.put("IDUser" , IDUser);
                params.put("assign_by" ,assign_by );
                params.put("IDIssue" ,IDIssue );
                params.put("priority" ,priority );
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
                loading = ProgressDialog.show(Assignment.this, "Downloading...", null,true,true);
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
