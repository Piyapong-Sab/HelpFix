package com.example.win.helpfix.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import com.example.win.helpfix.R;

public class Main_Login extends AppCompatActivity {


    ViewSwitcher Vs;
    private static final int REFRESH_SCREEN = 1;
    private Button signin ;
    private EditText idnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login);


        signin = (Button) findViewById(R.id.btn_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Vs = (ViewSwitcher) findViewById(R.id.viewSwhitcher1);
        startScan();
    }

    private void startScan() {
        new Thread(){
            public void run(){
                try{
                    Thread.sleep(9000);
                    hRefresh.sendEmptyMessage(REFRESH_SCREEN);
                }catch (Exception e){

                }
            }
        }.start();
    }
    Handler hRefresh = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case REFRESH_SCREEN:
                    Vs.showNext();
                    break;
                default:
                    break;
            }
        }
    };
}