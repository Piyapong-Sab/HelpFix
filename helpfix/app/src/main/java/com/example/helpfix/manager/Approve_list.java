package com.example.helpfix.manager;

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
import com.example.helpfix.adapter_manager.GetDataAdapterManager;
import com.example.helpfix.adapter_manager.RecyclerViewAdapterManager;
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Approve_list extends AppCompatActivity implements RecyclerViewAdapterManager.OnItemClickListener {

    private List<GetDataAdapterManager> mDataList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterManager recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://203.158.131.67/~devhelpfix/apphelpfix/view_detail_approve_list.php";
    public static final String JSON_ID_ISSUE= "Id_issue";
    public static final String JSON_APPROVE_DATE = "Approve_date";
    public static final String JSON_SENT_APP_NAME= "Sent_approve_by";
    public static final String JSON_PRICE = "Price";
    public static final String JSON_APPROVE_STATUS = "Approve_status";
    public static final String JSON_CREATE_BY = "Create_by";
    public static final String JSON_PROBLEM = "Problem";
    public static final String JSON_PLACE = "Place";
    public static final String JSON_ASSESSMENT_BY = "Assessment_by";
    public static final String JSON_EUIPMENT_USED = "Euipment_used";

    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handle;
    private Runnable runable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_list);
            unit();
    }

    private void unit() {
        Menu_navigation();
        mDataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_manager);
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
            GetDataAdapterManager GetDataAdapter2 = new GetDataAdapterManager();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setIdUser(json.getString(JSON_ID_ISSUE));
                GetDataAdapter2.setApprove_date(json.getString(JSON_APPROVE_DATE));
                GetDataAdapter2.setSent_approve_by(json.getString(JSON_SENT_APP_NAME));
                GetDataAdapter2.setPrice(json.getString(JSON_PRICE));
                GetDataAdapter2.setApprove_status(json.getString(JSON_APPROVE_STATUS));
                GetDataAdapter2.setCreate_by(json.getString(JSON_CREATE_BY));
                GetDataAdapter2.setProblem(json.getString(JSON_PROBLEM));
                GetDataAdapter2.setPlace(json.getString(JSON_PLACE));
                GetDataAdapter2.setAssessment_by(json.getString(JSON_ASSESSMENT_BY));
                GetDataAdapter2.setEuipment_used(json.getString(JSON_EUIPMENT_USED));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDataList.add(GetDataAdapter2);
        }
        recyclerViewadapter = new RecyclerViewAdapterManager(mDataList,Approve_list.this);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerViewadapter.setOnItemClickListener(Approve_list.this);


    }
    private void Menu_navigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent intent = new Intent(Approve_list.this, Mainmanager.class);
                        startActivity(intent);
                        break;

                    case R.id.ApprovalRequest:
                        Intent intent1 = new Intent(Approve_list.this, Pending_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.ReportDashboard:

                        //
                        break;
                    case R.id.ApprovedApplication:
                        Intent intent3 = new Intent(Approve_list.this, Approve_list.class);
                        startActivity(intent3);
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

        Intent intent = new Intent(getApplicationContext() ,Detail_approvelist.class);
        GetDataAdapterManager click =  mDataList.get(position);

        intent.putExtra(JSON_ID_ISSUE ,click.getId_issue() );
        intent.putExtra(JSON_APPROVE_DATE ,click.getApprove_date() );
        intent.putExtra(JSON_SENT_APP_NAME ,click.getSent_approve_by() );
        intent.putExtra(JSON_PRICE ,click.getPrice() );
        intent.putExtra(JSON_APPROVE_STATUS ,click.getApprove_status() );
        intent.putExtra(JSON_CREATE_BY ,click.getCreate_by() );
        intent.putExtra(JSON_PROBLEM ,click.getProblem() );
        intent.putExtra(JSON_PLACE ,click.getPlace() );
        intent.putExtra(JSON_ASSESSMENT_BY ,click.getAssessment_by() );
        intent.putExtra(JSON_EUIPMENT_USED ,click.getEuipment_used() );

        startActivity(intent);
    }
}