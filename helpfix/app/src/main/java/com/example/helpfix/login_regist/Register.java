package com.example.helpfix.login_regist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String TAG = "Register";
    private Button btn_regist;
    private ProgressBar loading;
    ProgressDialog progressDialog;
    private EditText id_user , password , name , surname ,gender , email, telphone;
    private RadioGroup genderRadioGroup;
    private static final String URL_REGIST = "http://203.158.131.67/~devhelpfix/apphelpfix/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        unit();
    }

    private void unit(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);

        btn_regist = (Button) findViewById(R.id.btn_regist);
        loading = (ProgressBar) findViewById(R.id.loading);
        id_user = findViewById(R.id.id_user);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        email = findViewById(R.id.email);
        telphone = findViewById(R.id.telphone);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Regist();


            }
        });

    }
    private void Regist () {
        //loading.setVisibility(View.VISIBLE);
       // btn_regist.setVisibility(View.GONE);


        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        final String gender;
        if (selectedId == R.id.female_radio_btn) {
            gender = "Female";
        } else {
            gender = "Male";
        }

        final String sid_user = id_user.getText().toString().trim();
        final String spassword =  password.getText().toString().trim();
        final String sname = name.getText().toString().trim();
        final String ssurname =  surname.getText().toString().trim();
        final String semail =  email.getText().toString().trim();
        final String stelphone =  telphone.getText().toString().trim();

        if (!sid_user.isEmpty() &&!spassword.isEmpty()&& !sname.isEmpty()&& !ssurname.isEmpty()&&
                !semail.isEmpty()&&!stelphone.isEmpty()){
            registerUser(sid_user, spassword,sname,ssurname,gender,semail,stelphone);
        }else if (sid_user.isEmpty()) {
            id_user.setError("กรุณากรอกรหัสบัตรประจำตัวพนักงาน");
        }else if (spassword.isEmpty()) {
                password.setError("กรุณากรอกรหัสผ่านพนักงาน");
            }else if (sname.isEmpty()) {
                name.setError("กรุณากรอกชื่อจริง");
            }else if (ssurname.isEmpty()) {
                surname.setError("กรุณากรอกนามสกุล");
            }else if (semail.isEmpty()) {
                email.setError("กรุณากรอกอีเมล");
            }else if (stelphone.isEmpty() ) {
            telphone.setError("Please Enter telphone");
        }


    }
    private void registerUser( final String id_user , final String password, final String name, final String surname ,
                              final String gender, final String email , final String telphone){

        progressDialog.setMessage("Register User in helpfix ...");
                                showDialog();

      //  if (!id_user.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Register Response: " + response.toString());
                            hideDialog();

                            loading.setVisibility(View.GONE);
                           // Toast.makeText(Register.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            btn_regist.setVisibility(View.VISIBLE);

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_user", id_user);
                    params.put("password", password);
                    params.put("name", name);
                    params.put("surname", surname);
                    params.put("gender", gender);
                    params.put("email", email);
                    params.put("telphone", telphone);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    //}
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
