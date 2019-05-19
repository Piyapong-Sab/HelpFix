package com.example.helpfix.technician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helpfix.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.helpfix.technician.Pending_list_tech.JSON_EVALUATEDT;
import static com.example.helpfix.technician.Pending_list_tech.JSON_ID_ISSUE;
import static com.example.helpfix.technician.Pending_list_tech.JSON_LEVEL;
import static com.example.helpfix.technician.Pending_list_tech.JSON_PLACE;
import static com.example.helpfix.technician.Pending_list_tech.JSON_PRICE;
import static com.example.helpfix.technician.Pending_list_tech.JSON_PRIORITY;
import static com.example.helpfix.technician.Pending_list_tech.JSON_PROBLEM;
import static com.example.helpfix.technician.Pending_list_tech.JSON_STATUS;


public class Manage_repair_tech extends AppCompatActivity {

    private static final String TAG = "Manage_repair_tech";
    private TextView issueID , Evaluate_date ,Priority ,Status ,Problem ,Place ,Level ,Price;
    private ImageView Pic_success;
    private Button btn_choose, btn_close;
    private Bitmap bitmap;
    private ImageView Pic_issue;
    ProgressDialog progressDialog;
    private static final String URL_SUCCESS = "http://203.158.131.67/~devhelpfix/apphelpfix/technician_updaterepair.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_repair_tech);
        unit();
    }
    private void unit(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);
        issueID = findViewById(R.id.issueID);
        Evaluate_date = findViewById(R.id.Evaluate_date);
        Priority = findViewById(R.id.Priority);
        Status = findViewById(R.id.Status);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Price = findViewById(R.id.Price);
        Pic_success = findViewById(R.id.Pic_success);
        btn_choose = findViewById(R.id.btn_choose);
        btn_close = findViewById(R.id.btn_close);
        Pic_issue = findViewById(R.id.Pic_issue);


        Intent intent = getIntent();
        String JISSUE = intent.getStringExtra(JSON_ID_ISSUE);
        String JPRIORITY = intent.getStringExtra(JSON_PRIORITY);
        String JPRICE = intent.getStringExtra(JSON_PRICE);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);
        String JEVALUATEDT = intent.getStringExtra(JSON_EVALUATEDT);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);

        issueID.setText(JISSUE);
        Evaluate_date.setText(JEVALUATEDT);
        Priority.setText(JPRIORITY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        Status.setText(JSTATUS);
        Price.setText(JPRICE);
        Lodingimage();
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseIssue();
            }
        });

    }

    private void CloseIssue() {
        final String SISSUEID = issueID.getText().toString().trim();
        if (!SISSUEID.isEmpty()){
            UPDATEISSUE(SISSUEID , getStringImage(bitmap));
        }
    }

    private void UPDATEISSUE(final String ISSUEID , final String PIC_SUCCESS) {

        progressDialog.setMessage("UPDATE ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST ,URL_SUCCESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "UPDATE Response: " + response.toString());
                        hideDialog();


                        Intent intent = new Intent(getApplicationContext(), Maintechnician.class);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Manage_repair_tech.this, "UPDATE Error!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("ISSUEID" ,ISSUEID );
                params.put("PIC_SUCCESS" , PIC_SUCCESS);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void chooseFile() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Pic_success.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
    private void Lodingimage() {
        String id = issueID.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Manage_repair_tech.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                Pic_issue.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://203.158.131.67/~devhelpfix/apphelpfix/view_detail_issue.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }

    @Override
    public void onBackPressed() {

    }
}
