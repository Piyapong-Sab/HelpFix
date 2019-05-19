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
import com.example.helpfix.adapter_head.RecyclerViewAdapterHeadSentApproval;
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sentapproval_list extends AppCompatActivity implements RecyclerViewAdapterHeadSentApproval.OnItemClickListener {


    private List<GetDataAdapterHead> mDataList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterHeadSentApproval recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL = "http://203.158.131.67/~devhelpfix/apphelpfix/view_sentapproval.php";

    public static final String JSON_ID_ISSUE = "Id_issue";
    public static final String JSON_RECORDDT = "Record_date";
    public static final String JSON_CREATEBY = "Create_by";
    public static final String JSON_PROBLEM = "Problem";
    public static final String JSON_PLACE = "Place";
    public static final String JSON_LEVEL = "Level";
    public static final String JSON_ASSESSMENTDT = "Assessment_date";
    public static final String JSON_PRIORITY = "Priority";
    public static final String JSON_ASSESSMENTBY = "Assessment_by";
    public static final String JSON_EVALUATEDT= "Evaluate_date";
    public static final String JSON_EUIPMENTUS = "Euipment_used";
    public static final String JSON_PRICE = "Price";
    public static final String JSON_STATUS = "Status_name";


    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handle;
    private Runnable runable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentapproval_list);
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
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent intent = new Intent(Sentapproval_list.this, Mainhead_division.class);
                        startActivity(intent);
                        break;

                    case R.id.Application:
                        Intent intent1 = new Intent(Sentapproval_list.this, Assignment_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.RepairWork:
                        Intent intent2 = new Intent(Sentapproval_list.this, Repair_notification_list.class);
                        startActivity(intent2);
                        break;
                    case R.id.SubmittedApplication:
                        Intent intent3 = new Intent(Sentapproval_list.this, Work_list.class);
                        startActivity(intent3);
                        break;
                    case R.id.ApprovalRequest:
                        Intent intent4 = new Intent(Sentapproval_list.this, Sentapproval_list.class);
                        startActivity(intent4);
                        break;
                }

                return false;
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
            GetDataAdapterHead GetDataAdapter2 = new GetDataAdapterHead();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setId_issue(json.getString(JSON_ID_ISSUE));
                GetDataAdapter2.setRecordDate(json.getString(JSON_RECORDDT));
                GetDataAdapter2.setCreate_by(json.getString(JSON_CREATEBY));;
                GetDataAdapter2.setProblem(json.getString(JSON_PROBLEM));
                GetDataAdapter2.setPlace(json.getString(JSON_PLACE));
                GetDataAdapter2.setLevel(json.getString(JSON_LEVEL));
                GetDataAdapter2.setAssessment_date(json.getString(JSON_ASSESSMENTDT));
                GetDataAdapter2.setPriority(json.getString(JSON_PRIORITY));;
                GetDataAdapter2.setAssessment_by(json.getString(JSON_ASSESSMENTBY));
                GetDataAdapter2.setEvaluate_date(json.getString(JSON_EVALUATEDT));
                GetDataAdapter2.setEuipment_used(json.getString(JSON_EUIPMENTUS));
                GetDataAdapter2.setPrice(json.getString(JSON_PRICE));
                GetDataAdapter2.setStatus(json.getString(JSON_STATUS));;


            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDataList.add(GetDataAdapter2);
        }
        recyclerViewadapter = new RecyclerViewAdapterHeadSentApproval(mDataList, this);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerViewadapter.setOnItemClickListener(Sentapproval_list.this);
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getApplicationContext() , Sentapproval.class);
        GetDataAdapterHead clicked =  mDataList.get(position);
        intent.putExtra(JSON_ID_ISSUE ,clicked.getId_issue() );
        intent.putExtra(JSON_RECORDDT ,clicked.getRecordDate() );
        intent.putExtra(JSON_CREATEBY ,clicked.getCreate_by() );
        intent.putExtra(JSON_PROBLEM ,clicked.getProblem() );
        intent.putExtra(JSON_PLACE ,clicked.getPlace() );
        intent.putExtra(JSON_LEVEL ,clicked.getLevel() );
        intent.putExtra(JSON_ASSESSMENTDT ,clicked.getAssessment_date() );
        intent.putExtra(JSON_PRIORITY ,clicked.getPriority() );
        intent.putExtra(JSON_ASSESSMENTBY ,clicked.getAssessment_by() );
        intent.putExtra(JSON_EVALUATEDT ,clicked.getEvaluate_date() );
        intent.putExtra(JSON_EUIPMENTUS ,clicked.getEuipment_used() );
        intent.putExtra(JSON_PRICE ,clicked.getPrice() );
        intent.putExtra(JSON_STATUS ,clicked.getStatus() );
        startActivity(intent);

    }
}
