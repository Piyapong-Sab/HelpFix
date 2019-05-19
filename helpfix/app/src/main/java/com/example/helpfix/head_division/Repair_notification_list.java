package com.example.helpfix.head_division;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;
import com.example.helpfix.adapter_head.GetDataAdapterHead;
import com.example.helpfix.adapter_head.RecyclerViewAdapterHeadRepair;
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repair_notification_list extends AppCompatActivity implements RecyclerViewAdapterHeadRepair.OnItemClickListener {


    private List<GetDataAdapterHead> mDataList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterHeadRepair recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL = "http://203.158.131.67/~devhelpfix/apphelpfix/view_repair_head.php";

    public static final String JSON_ID_ISSUE = "Id_issue";
    public static final String JSON_RECORDDATE = "Record_date";
    public static final String JSON_NAME = "Name";
    public static final String JSON_MCATEGORY = "Mcategory_name";
    public static final String JSON_SCATEGORY = "Scategory_name";
    public static final String JSON_PROBLEM = "Problem";
    public static final String JSON_STATUS = "Status_name";
    public static final String JSON_PLACE = "Place";
    public static final String JSON_LEVEL = "Level";
    public static final String JSON_IMAGE_URL = "Pic_issue";

    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handle;
    private Runnable runable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_notification_list);
        unit();

    }

    private void unit() {
        Menu_navigation();
        mDataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.Recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        JSON_DATA_WEB_CALL();
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
                        JSON_DATA_WEB_CALL();

                        handle.removeCallbacks(runable); // stop runable.
                    }
                };
                handle.postDelayed(runable, 3000); // delay 3 s.
            }
        });
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
                        Intent intent = new Intent(Repair_notification_list.this, Mainhead_division.class);
                        startActivity(intent);
                        break;

                    case R.id.Application:
                        Intent intent1 = new Intent(Repair_notification_list.this, Assignment_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.RepairWork:
                        Intent intent2 = new Intent(Repair_notification_list.this, Repair_notification_list.class);
                        startActivity(intent2);
                        break;
                    case R.id.SubmittedApplication:
                        Intent intent3 = new Intent(Repair_notification_list.this, Work_list.class);
                        startActivity(intent3);
                        break;
                    case R.id.ApprovalRequest:
                        Intent intent4 = new Intent(Repair_notification_list.this, Sentapproval_list.class);
                        startActivity(intent4);
                        break;

                }

                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {

    }

    private void JSON_DATA_WEB_CALL() {
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
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
    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            GetDataAdapterHead GetDataAdapter2 = new GetDataAdapterHead();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setPicIssue(json.getString(JSON_IMAGE_URL));
                GetDataAdapter2.setId_issue(json.getString(JSON_ID_ISSUE));
                GetDataAdapter2.setRecordDate(json.getString(JSON_RECORDDATE));
                GetDataAdapter2.setProblem(json.getString(JSON_PROBLEM));
                GetDataAdapter2.setStatus(json.getString(JSON_STATUS));
                GetDataAdapter2.setPlace(json.getString(JSON_PLACE));
                GetDataAdapter2.setLevel(json.getString(JSON_LEVEL));
                GetDataAdapter2.setCreate_by(json.getString(JSON_NAME));
                GetDataAdapter2.setId_m_category(json.getString(JSON_MCATEGORY));
                GetDataAdapter2.setId_s_category(json.getString(JSON_SCATEGORY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDataList.add(GetDataAdapter2);
        }
        recyclerViewadapter = new RecyclerViewAdapterHeadRepair(mDataList, this);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerViewadapter.setOnItemClickListener(Repair_notification_list.this);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext() , Detail_repair_notificationlist.class);
        GetDataAdapterHead clicked =  mDataList.get(position);
        intent.putExtra(JSON_ID_ISSUE ,clicked.getId_issue() );
        intent.putExtra(JSON_RECORDDATE ,clicked.getRecordDate() );
        intent.putExtra(JSON_PROBLEM ,clicked.getProblem() );
        intent.putExtra(JSON_PLACE ,clicked.getPlace() );
        intent.putExtra(JSON_LEVEL ,clicked.getLevel() );
        intent.putExtra(JSON_STATUS ,clicked.getStatus() );
        intent.putExtra(JSON_NAME ,clicked.getCreate_by() );
        intent.putExtra(JSON_MCATEGORY ,clicked.getId_m_category() );
        intent.putExtra(JSON_SCATEGORY ,clicked.getId_s_category() );
        startActivity(intent);
    }
}
