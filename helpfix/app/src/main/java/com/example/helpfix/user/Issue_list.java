package com.example.helpfix.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;
import com.example.helpfix.adapter_user.GetDataAdapterUser;
import com.example.helpfix.adapter_user.RecyclerViewAdapterUser;
import com.example.helpfix.session_login.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Issue_list extends AppCompatActivity implements RecyclerViewAdapterUser.OnItemClickListener {

    private TextView UID ;
    SessionManager sessionManager;
    private List<GetDataAdapterUser> mDataList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterUser recyclerViewadapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handle;
    private Runnable runable;
    private final String GET_JSON_DATA_HTTP_URL = "http://203.158.131.67/~devhelpfix/apphelpfix/view_issueuser.php";
    public static final String JSON_ID_ISSUE = "Id_issue";
    public static final String JSON_PROBLEM = "Problem";
    public static final String JSON_RECORDDATE = "Record_date";
    public static final String JSON_STATUS = "Status_name";
    public static final String JSON_PLACE = "Place";
    public static final String JSON_LEVEL = "Level";

    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ,requestQueue1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_list);

       unit();
    }

    private void unit() {
        Menu_navigation();
        sessionManager = new SessionManager(this);
        UID = findViewById(R.id.UID);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mUSERID = user.get(sessionManager.USERID);

        UID.setText(mUSERID);

        mDataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.Recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                                    GetDataAdapterUser GetDataAdapter2 = new GetDataAdapterUser();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    GetDataAdapter2.setIdissue(dataobj.getString(JSON_ID_ISSUE));
                                    GetDataAdapter2.setRecordDate(dataobj.getString(JSON_RECORDDATE));
                                    GetDataAdapter2.setProblem(dataobj.getString(JSON_PROBLEM));
                                    GetDataAdapter2.setStatus(dataobj.getString(JSON_STATUS));
                                    GetDataAdapter2.setPlace(dataobj.getString(JSON_PLACE));
                                    GetDataAdapter2.setLevel(dataobj.getString(JSON_LEVEL));

                                    mDataList.add(GetDataAdapter2);

                                }

                                recyclerViewadapter = new RecyclerViewAdapterUser(mDataList,this );
                                recyclerView.setAdapter(recyclerViewadapter);
                                recyclerViewadapter.setOnItemClickListener(Issue_list.this);

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
                        //JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest);
    }
    private void Menu_navigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent intent = new Intent(Issue_list.this, Mainuser.class);
                        startActivity(intent);
                        break;

                    case R.id.Create:
                        Intent intent1 = new Intent(Issue_list.this, Create_issue.class);
                        startActivity(intent1);
                        break;

                    case R.id.Status:
                        Intent intent2 = new Intent(Issue_list.this, Issue_list.class);
                        startActivity(intent2);
                        break;

                }


                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext() , Detail_issuelist.class);
        GetDataAdapterUser clicked =  mDataList.get(position);
        intent.putExtra(JSON_ID_ISSUE ,clicked.getIdissue() );
        intent.putExtra(JSON_RECORDDATE ,clicked.getRecordDate() );
        intent.putExtra(JSON_PROBLEM ,clicked.getProblem() );
        intent.putExtra(JSON_PLACE ,clicked.getPlace() );
        intent.putExtra(JSON_LEVEL ,clicked.getLevel() );
        intent.putExtra(JSON_STATUS ,clicked.getStatus() );
        startActivity(intent);
    }
}
