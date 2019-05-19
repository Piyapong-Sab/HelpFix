package com.example.helpfix.head_division;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpfix.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.helpfix.head_division.Repair_notification_list.JSON_ID_ISSUE;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_LEVEL;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_MCATEGORY;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_NAME;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_PLACE;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_PROBLEM;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_RECORDDATE;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_SCATEGORY;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_STATUS;

public class Detail_repair_notificationlist extends AppCompatActivity {

    private TextView issueID ,Createdate ,Create_by ,category ,subcategory ,Problem ,Place ,Level ,Status;
    private ImageView Pic_issueNO , Pic_success;
    private Button btn_backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_repair_notificationlist);

        unit();
    }
    private void unit(){

        issueID = findViewById(R.id.issueID);
        Createdate = findViewById(R.id.Createdate);
        Create_by = findViewById(R.id.Create_by);
        category = findViewById(R.id.category);
        subcategory = findViewById(R.id.subcategory);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Status = findViewById(R.id.Status);
        Pic_issueNO = findViewById(R.id.Pic_issueNO);
        Pic_success = findViewById(R.id.Pic_success);
        btn_backhome = findViewById(R.id.btn_backhome);

        Intent intent = getIntent();
        String JIDISSUE= intent.getStringExtra(JSON_ID_ISSUE);
        String JRECORDDATE = intent.getStringExtra(JSON_RECORDDATE);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM );
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);
        String JNAME = intent.getStringExtra(JSON_NAME);
        String JMCATEGORY = intent.getStringExtra(JSON_MCATEGORY);
        String JSCATEGORY = intent.getStringExtra(JSON_SCATEGORY);

        issueID.setText(JIDISSUE);
        Createdate.setText(JRECORDDATE);
        Create_by.setText(JNAME);
        subcategory.setText(JSCATEGORY);
        category.setText(JMCATEGORY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        Status.setText(JSTATUS);
        Lodingimage();
        Lodingimagesuccess();
        btn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mainhead_division.class);
                startActivity(intent);
            }
        });
    }
    private void Lodingimage() {
        String id = issueID.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detail_repair_notificationlist.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                Pic_issueNO.setImageBitmap(b);
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
    private void Lodingimagesuccess() {
        String id = issueID.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detail_repair_notificationlist.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                Pic_success.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://203.158.131.67/~devhelpfix/apphelpfix/view_detail_success.php?id="+id;
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



}
