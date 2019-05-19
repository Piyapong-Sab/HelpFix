package com.example.helpfix.head_division;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;
import com.example.helpfix.adapter_head.GetDataAdapterHead;
import com.example.helpfix.adapter_head.RecyclerViewAdapterHeadSentRequest;
import com.example.helpfix.session_login.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sent_requestlist extends AppCompatActivity implements RecyclerViewAdapterHeadSentRequest.OnItemClickListener {


    private List<GetDataAdapterHead> mDataList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterHeadSentRequest recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL = "http://203.158.131.67/~devhelpfix/apphelpfix/view_sentrequest.php";

    public static final String JSON_ID_ISSUE = "Id_issue";
    public static final String JSON_APPROVE_DT = "Approve_date";
    public static final String JSON_ASSESSMENT_BY = "Assessment_by";
    public static final String JSON_CREATE_BY = "Create_by";
    public static final String JSON_APPROVE_STATUS = "Approve_status";
    public static final String JSON_PROBLEM = "Problem";
    public static final String JSON_PLACE = "Place";
    public static final String JSON_LEVEL = "Level";
    public static final String JSON_EUIPMENTUS = "Euipment_used";
    public static final String JSON_PRICE = "Price";

    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    private TextView UID;
    SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handle;
    private Runnable runable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sent_requestlist);
        mDataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_request);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        JSON_DATA_WEB_CALL();
        sessionManager = new SessionManager(this);
        UID = findViewById(R.id.UID);
        HashMap<String , String> user = sessionManager.getUserDetail();
        String mUSERID = user.get(sessionManager.USERID);
        UID.setText(mUSERID);
        JSON_DATA_WEB_PUT_CALL ();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handle = new Handler();
                runable = new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        mDataList.clear();
                        JSON_DATA_WEB_PUT_CALL ();

                        handle.removeCallbacks(runable); // stop runable.
                    }
                };
                handle.postDelayed(runable, 3000); // delay 3 s.
            }
        });
    }

    private void JSON_DATA_WEB_PUT_CALL() {
        final String LogID = UID.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_DATA_WEB_CALL();

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {
                                    GetDataAdapterHead GetDataAdapter2 = new GetDataAdapterHead();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    GetDataAdapter2.setId_issue(dataobj.getString(JSON_ID_ISSUE));
                                    GetDataAdapter2.setApprove_date(dataobj.getString(JSON_APPROVE_DT));
                                    GetDataAdapter2.setAssessment_by(dataobj.getString(JSON_ASSESSMENT_BY));
                                    GetDataAdapter2.setCreate_by(dataobj.getString(JSON_CREATE_BY));
                                    GetDataAdapter2.setStatus(dataobj.getString(JSON_APPROVE_STATUS));
                                    GetDataAdapter2.setProblem(dataobj.getString(JSON_PROBLEM));
                                    GetDataAdapter2.setPlace(dataobj.getString(JSON_PLACE));
                                    GetDataAdapter2.setLevel(dataobj.getString(JSON_LEVEL));
                                    GetDataAdapter2.setEuipment_used(dataobj.getString(JSON_EUIPMENTUS));
                                    GetDataAdapter2.setPrice(dataobj.getString(JSON_PRICE));

                                    mDataList.add(GetDataAdapter2);
                                }
                                recyclerViewadapter = new RecyclerViewAdapterHeadSentRequest(mDataList, this);
                                recyclerView.setAdapter(recyclerViewadapter);
                                recyclerViewadapter.setOnItemClickListener(Sent_requestlist.this);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("LogID" , LogID);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void JSON_DATA_WEB_CALL() {
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getApplicationContext() , Detail_sentrequest_list.class);
        GetDataAdapterHead clicked =  mDataList.get(position);
        intent.putExtra(JSON_ID_ISSUE ,clicked.getId_issue() );
        intent.putExtra(JSON_APPROVE_DT ,clicked.getApprove_date() );
        intent.putExtra(JSON_ASSESSMENT_BY ,clicked.getAssessment_by() );
        intent.putExtra(JSON_CREATE_BY ,clicked.getCreate_by() );
        intent.putExtra(JSON_APPROVE_STATUS ,clicked.getStatus() );
        intent.putExtra(JSON_PROBLEM ,clicked.getProblem() );
        intent.putExtra(JSON_PLACE ,clicked.getPlace() );
        intent.putExtra(JSON_LEVEL ,clicked.getLevel() );
        intent.putExtra(JSON_EUIPMENTUS ,clicked.getEuipment_used() );
        intent.putExtra(JSON_PRICE ,clicked.getPrice() );
        startActivity(intent);

    }
}
