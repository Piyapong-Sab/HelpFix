package com.example.helpfix.technician;

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

import static com.example.helpfix.technician.Repair_notification_technician.JSON_ASSESSMENT_DT;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_ASSIGNER_BY;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_ID_ISSUE;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_LEVEL;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_PLACE;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_PRIORITY;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_PROBLEM;
import static com.example.helpfix.technician.Repair_notification_technician.JSON_STATUS;

public class Detail_repair__technician extends AppCompatActivity {

    private TextView Priority ,issueID ,AssessmentDT ,Assigner_by ,Problem ,Place ,Level ,Status;
    private Button btn_backmain;
    private ImageView Pic_issueNO , Pic_success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_repair__technician);

        unit();
    }
    private void unit (){
        Priority = findViewById(R.id.Priority);
        issueID = findViewById(R.id.issueID);
        AssessmentDT = findViewById(R.id.AssessmentDT);
        Assigner_by = findViewById(R.id.Assigner_by);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Status = findViewById(R.id.Status);
        Pic_issueNO = findViewById(R.id.Pic_issueNO);
        Pic_success = findViewById(R.id.Pic_success);
        btn_backmain =findViewById(R.id.btn_backhome);
        btn_backmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Maintechnician.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String JUSERID = intent.getStringExtra(JSON_ID_ISSUE);
        String JASESIGNER_BY = intent.getStringExtra(JSON_ASSIGNER_BY);
        String JASSESSMENT_DT = intent.getStringExtra(JSON_ASSESSMENT_DT );
        String JPRIORITY = intent.getStringExtra(JSON_PRIORITY);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);


        issueID.setText(JUSERID);
        AssessmentDT.setText(JASSESSMENT_DT);
        Assigner_by.setText(JASESIGNER_BY);
        Priority.setText(JPRIORITY);
        Problem.setText(JPROBLEM);
        Status.setText(JSTATUS);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);

        Lodingimage();
        Lodingimagesuccess();
    }
    private void Lodingimage() {
        String id = issueID.getText().toString().trim();
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detail_repair__technician.this, "Downloading...", null,true,true);
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
                loading = ProgressDialog.show(Detail_repair__technician.this, "Downloading...", null,true,true);
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
