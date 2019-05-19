package com.example.helpfix.manager;

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

public class Mainmanager extends AppCompatActivity {

    private TextView name ;
    private CardView btn_pending , btn_resummary ,btn_approve_list, btn_help;
    private ImageView LogOut;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmanager);

        unit();
    }
    private void unit() {
        Menu_navigation();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        name = findViewById(R.id.name);
        btn_pending = findViewById(R.id.btn_pending);
        btn_resummary = findViewById(R.id.btn_resummary);
        btn_approve_list = findViewById(R.id.btn_approve_list);
        btn_help = findViewById(R.id.btn_help);
        LogOut = findViewById(R.id.LogOut);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        name.setText(mName);

        btn_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pending_list.class);
                startActivity(intent);
            }
        });
        btn_resummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_approve_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Approve_list.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Mainmanager.this);
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

                        break;

                    case R.id.ApprovalRequest:
                        Intent intent1 = new Intent(Mainmanager.this, Pending_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.ReportDashboard:

                        //
                        break;
                    case R.id.ApprovedApplication:
                        Intent intent3 = new Intent(Mainmanager.this, Approve_list.class);
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
}
