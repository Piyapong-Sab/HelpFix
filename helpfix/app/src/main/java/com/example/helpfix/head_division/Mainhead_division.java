package com.example.helpfix.head_division;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpfix.R;
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;
import com.example.helpfix.include_help.Sentemail;
import com.example.helpfix.session_login.SessionManager;

import java.util.HashMap;

public class Mainhead_division extends AppCompatActivity {
    private TextView name ;
    private CardView btn_assignment , btn_repair , btn_sentapproval, btn_work,btn_sendrequest ,btn_help,btn_logout;
    SessionManager sessionManager;
    private ImageView LogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainhead_division);

        unit();
    }

    private void unit() {
        Menu_navigation();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        name = findViewById(R.id.name);
        btn_assignment = findViewById(R.id.btn_assignment);
        btn_repair = findViewById(R.id.btn_repair);
        btn_sentapproval = findViewById(R.id.btn_sentapproval);
        btn_work = findViewById(R.id.btn_work);
        btn_sendrequest = findViewById(R.id.btn_sendrequest);
        btn_help = findViewById(R.id.btn_help);
        LogOut = findViewById(R.id.LogOut);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        name.setText(mName);

        btn_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Assignment_list.class);
                startActivity(intent);
            }
        });
        btn_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Repair_notification_list.class);
                startActivity(intent);
            }
        });
        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Work_list.class);
                startActivity(intent);
            }
        });
        btn_sentapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sentapproval_list.class);
                startActivity(intent);
            }
        });

        btn_sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sent_requestlist.class);
                startActivity(intent);
            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sentemail.class);
                startActivity(intent);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Mainhead_division.this);
                builder.setMessage("คุณแน่ใจใช่ไหมว่าจะออกจากระบบ ? ")
                        .setTitle("Exit");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logout();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
    }

    private void Menu_navigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent intent = new Intent(Mainhead_division.this, Mainhead_division.class);
                        startActivity(intent);
                        break;

                    case R.id.Application:
                        Intent intent1 = new Intent(Mainhead_division.this, Assignment_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.RepairWork:
                        Intent intent2 = new Intent(Mainhead_division.this, Repair_notification_list.class);
                        startActivity(intent2);
                        break;
                    case R.id.SubmittedApplication:
                        Intent intent3 = new Intent(Mainhead_division.this, Work_list.class);
                        startActivity(intent3);
                        break;
                    case R.id.ApprovalRequest:
                        Intent intent4 = new Intent(Mainhead_division.this, Sentapproval_list.class);
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
}
