package com.example.helpfix.login_regist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;
import com.example.helpfix.head_division.Mainhead_division;
import com.example.helpfix.manager.Mainmanager;
import com.example.helpfix.session_login.SessionManager;
import com.example.helpfix.technician.Maintechnician;
import com.example.helpfix.user.Mainuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    ViewSwitcher Vs;
    private  static final String TAG ="Login";
    private static final int REFRESH_SCREEN = 1;
    private Button btn_signin ;
    private EditText id_user , password;
    private  static final String URL_LOG = "http://203.158.131.67/~devhelpfix/apphelpfix/login.php";
    private static final String user = "1";
    private TextView Link_register;
    private ImageView Img_register;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        unit();
    }

    private void unit(){
        sessionManager = new SessionManager(this);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        id_user = (EditText) findViewById(R.id.id_user);
        password = (EditText) findViewById(R.id.password);
        Link_register = findViewById(R.id.Link_register);
        Img_register = findViewById(R.id.Img_register);
        Img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Sid_user = id_user.getText().toString().trim();
                final String Spassword =  password.getText().toString().trim();

                if (!Sid_user.isEmpty() || !Spassword.isEmpty()) {
                    login(Sid_user, Spassword);
                }else {
                    id_user.setError("กรุณากรอกบัตรประจำตัวพนักงาน");
                    password.setError("กรุณากรอกรหัสผ่านพนักงาน");
                }

            }
        });
        Vs = (ViewSwitcher) findViewById(R.id.viewSwhitcher);
        startScan();
    }

    private void login(final String id_user , final String password){

            progressDialog.setMessage("Logging you in helpfix ...");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    URL_LOG, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");

                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();
                        if (success.equals("1")){

                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String userid = object.getString("userid").trim();
                                String name = object.getString("name").trim();
                                String email = object.getString("email").trim();
                                String roleid = object.getString("roleid").trim();

                                //Toast.makeText(Login.this, roleid, Toast.LENGTH_SHORT).show();
                                 if (roleid.equals("102")){
                                    //Toast.makeText(Login.this, roleid, Toast.LENGTH_SHORT).show();
                                     sessionManager.createSession(userid,name , email);
                                     Intent intent = new Intent(getApplicationContext(), Mainuser.class);
                                     startActivity(intent);
                                     finish();
                                }else if (roleid.equals("103")){
                                    //Toast.makeText(Login.this, roleid, Toast.LENGTH_SHORT).show();
                                     sessionManager.createSession(userid,name , email);
                                     Intent intent = new Intent(getApplicationContext(), Mainmanager.class);
                                     startActivity(intent);
                                     finish();
                                }else if (roleid.equals("104")){
                                    //Toast.makeText(Login.this, roleid, Toast.LENGTH_SHORT).show();
                                     sessionManager.createSession(userid,name , email);
                                     Intent intent = new Intent(getApplicationContext(), Maintechnician.class);
                                     startActivity(intent);

                                }else if(roleid.equals("105")){
                                    //Toast.makeText(Login.this, roleid, Toast.LENGTH_SHORT).show();
                                     sessionManager.createSession(userid,name , email);
                                     Intent intent = new Intent(getApplicationContext(), Mainhead_division.class);
                                     startActivity(intent);
                                     finish();

                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                    }




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, "Login Error!" + error.toString(), Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    // Posting params to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_user", id_user);
                    params.put("password", password);
                    return params;

                };
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {

    }

    private void startScan() {
        new Thread(){
            public void run(){
                try{
                    Thread.sleep(2000);
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