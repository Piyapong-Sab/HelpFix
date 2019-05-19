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
import static com.example.helpfix.head_division.Repair_notification_list.JSON_PLACE;
import static com.example.helpfix.head_division.Repair_notification_list.JSON_PROBLEM;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_APPROVE_DT;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_APPROVE_STATUS;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_ASSESSMENT_BY;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_CREATE_BY;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_EUIPMENTUS;
import static com.example.helpfix.head_division.Sent_requestlist.JSON_PRICE;

public class Detail_sentrequest_list extends AppCompatActivity {

    private TextView issueID , Approve_date ,Create_by ,Problem ,Place ,Level ,Assessment_by ,Euipment_used ,Price ,status;
    private Button btn_backhome;
    private ImageView Pic_issue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_sentrequest_list);
        unit();
    }
    private void unit(){
        issueID = findViewById(R.id.issueID);
        Approve_date = findViewById(R.id.Approve_date);
        Create_by = findViewById(R.id.Create_by);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Assessment_by = findViewById(R.id.Assessment_by);
        Euipment_used = findViewById(R.id.Euipment_used);
        Price = findViewById(R.id.Price);
        status = findViewById(R.id.status);
        Pic_issue = findViewById(R.id.Pic_issue);

        btn_backhome = findViewById(R.id.btn_backhome);

        Intent intent = getIntent();
        String JIDISSUE = intent.getStringExtra(JSON_ID_ISSUE);
        String JAPPROVEDT = intent.getStringExtra(JSON_APPROVE_DT);
        String JASSESSMENTBY = intent.getStringExtra(JSON_ASSESSMENT_BY );
        String JCREATEBY = intent.getStringExtra(JSON_CREATE_BY);
        String JAPPROVEST = intent.getStringExtra(JSON_APPROVE_STATUS);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);
        String JEUIPMENTUS = intent.getStringExtra(JSON_EUIPMENTUS);
        String JPRICE = intent.getStringExtra(JSON_PRICE);

        issueID.setText(JIDISSUE);
        Approve_date.setText(JAPPROVEDT);
        Create_by.setText(JCREATEBY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        Assessment_by.setText(JASSESSMENTBY);
        Euipment_used.setText(JEUIPMENTUS);
        Price.setText(JPRICE);
        status.setText(JAPPROVEST);
        Lodingimage();
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
                loading = ProgressDialog.show(Detail_sentrequest_list.this, "Downloading...", null,true,true);
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
}
