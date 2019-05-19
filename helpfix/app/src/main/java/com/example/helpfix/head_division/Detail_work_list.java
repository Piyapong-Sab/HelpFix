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

import static com.example.helpfix.head_division.Work_list.JSON_ASSESSMENTBY;
import static com.example.helpfix.head_division.Work_list.JSON_ASSESSMENTDT;
import static com.example.helpfix.head_division.Work_list.JSON_ID_ISSUE;
import static com.example.helpfix.head_division.Work_list.JSON_LEVEL;
import static com.example.helpfix.head_division.Work_list.JSON_MCATEGORY;
import static com.example.helpfix.head_division.Work_list.JSON_PLACE;
import static com.example.helpfix.head_division.Work_list.JSON_PRIORITY;
import static com.example.helpfix.head_division.Work_list.JSON_PROLLEM;
import static com.example.helpfix.head_division.Work_list.JSON_SCATEGORY;
import static com.example.helpfix.head_division.Work_list.JSON_STATUS;

public class Detail_work_list extends AppCompatActivity {

    private TextView issueID ,Assessment_date ,Priority ,Assessment_by ,category ,subcategory ,Problem ,Place ,Level,status;
    private ImageView Pic_issue;
    private Button btn_backhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_work_list);
        unit();
    }
    private void unit(){
        issueID = findViewById(R.id.issueID);
        Assessment_date = findViewById(R.id.Assessment_date);
        Priority = findViewById(R.id.Priority);
        Assessment_by = findViewById(R.id.Assessment_by);
        category = findViewById(R.id.category);
        subcategory = findViewById(R.id.subcategory);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        status = findViewById(R.id.status);
        Pic_issue = findViewById(R.id.Pic_issue);
        btn_backhome = findViewById(R.id.btn_backhome);

        Intent intent = getIntent();
        String JIDISSUE= intent.getStringExtra(JSON_ID_ISSUE);
        String JASSESSMENTDT = intent.getStringExtra(JSON_ASSESSMENTDT);
        String JPRIORITY = intent.getStringExtra(JSON_PRIORITY );
        String JASSESSMENTBY = intent.getStringExtra(JSON_ASSESSMENTBY);
        String JMCATEGORY = intent.getStringExtra(JSON_MCATEGORY);
        String JSCATEGORY = intent.getStringExtra(JSON_SCATEGORY);
        String JPROBLEM = intent.getStringExtra(JSON_PROLLEM);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);

        issueID.setText(JIDISSUE);
        Assessment_date.setText(JASSESSMENTDT);
        Priority.setText(JPRIORITY);
        Assessment_by.setText(JASSESSMENTBY);
        category.setText(JMCATEGORY);
        subcategory.setText(JSCATEGORY);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        status.setText(JSTATUS);
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
                loading = ProgressDialog.show(Detail_work_list.this, "Downloading...", null,true,true);
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
