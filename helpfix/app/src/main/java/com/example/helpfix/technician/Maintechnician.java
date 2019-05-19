package com.example.helpfix.technician;

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

public class Maintechnician extends AppCompatActivity {

    private TextView name ;
    private CardView btn_assessment , btn_repaircation , btn_help , btn_pending , btn_repairable;
    SessionManager sessionManager;
    private ImageView LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintechnician);
        unit();
    }
    private void unit() {
        Menu_navigation();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = findViewById(R.id.name);
        btn_assessment = findViewById(R.id.btn_assessment);
        btn_repaircation = findViewById(R.id.btn_repaircation);
        btn_pending = findViewById(R.id.btn_pending);
        btn_repairable = findViewById(R.id.btn_repairable);
        btn_help = findViewById(R.id.btn_help);
        LogOut = findViewById(R.id.LogOut);

        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        name.setText(mName);

        btn_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Assessment_list.class);
                startActivity(intent);
            }
        });
        btn_repaircation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Repair_notification_technician.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Maintechnician.this);
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
        btn_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pending_list_tech.class);
                startActivity(intent);
            }
        });
        btn_repairable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Repairable_list_tech.class);
                startActivity(intent);
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

                    case R.id.RepairApplication:
                        Intent intent1 = new Intent(Maintechnician.this, Assessment_list.class);
                        startActivity(intent1);
                        break;

                    case R.id.CompletedRepair:
                        Intent intent2 = new Intent(Maintechnician.this, Repair_notification_technician.class);
                        startActivity(intent2);
                        break;
                    case R.id.ApprovalWait:
                        Intent intent3 = new Intent(Maintechnician.this, Pending_list_tech.class);
                        startActivity(intent3);
                        break;
                    case R.id.ApplicationsClosed:
                        Intent intent4 = new Intent(Maintechnician.this, Repairable_list_tech.class);
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
