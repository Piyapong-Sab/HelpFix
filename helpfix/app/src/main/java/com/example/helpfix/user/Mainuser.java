package com.example.helpfix.user;

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

public class Mainuser extends AppCompatActivity {
    private TextView userid , name ,email;
    private CardView btn_createIssue , btn_viewstatus ,btn_help;
    private ImageView LogOut;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainuser);
        unit();
    }
    public void unit(){
        Menu_navigation();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = findViewById(R.id.name);
        btn_createIssue = findViewById(R.id.CreateIssue);
        btn_viewstatus = findViewById(R.id.ViewStatus);
        btn_help = findViewById(R.id.btn_help);
        LogOut = findViewById(R.id.LogOut);


        HashMap<String , String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);

        name.setText(mName);

        btn_createIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create_issue.class);
                startActivity(intent);
            }
        });
        btn_viewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Issue_list.class);
                startActivity(intent);
                //finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Mainuser.this);
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

                    case R.id.Create:
                        Intent intent1 = new Intent(Mainuser.this, Create_issue.class);
                        startActivity(intent1);
                        onBackPressed();
                        break;

                    case R.id.Status:
                        Intent intent2 = new Intent(Mainuser.this, Issue_list.class);
                        startActivity(intent2);
                        onBackPressed();
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
