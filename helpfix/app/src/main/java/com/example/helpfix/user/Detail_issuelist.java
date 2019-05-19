package com.example.helpfix.user;

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

import static com.example.helpfix.user.Issue_list.JSON_ID_ISSUE;
import static com.example.helpfix.user.Issue_list.JSON_LEVEL;
import static com.example.helpfix.user.Issue_list.JSON_PLACE;
import static com.example.helpfix.user.Issue_list.JSON_PROBLEM;
import static com.example.helpfix.user.Issue_list.JSON_RECORDDATE;
import static com.example.helpfix.user.Issue_list.JSON_STATUS;


public class Detail_issuelist extends AppCompatActivity {

    private TextView issueID ,Createdate ,Problem , Place , Level , status;
    private Button btn_backhome ;
    private ImageView Pic_issue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_issuelist);
        unit();
    }
    public void unit(){
        issueID = findViewById(R.id.issueID);
        Createdate = findViewById(R.id.Createdate);
        Problem = findViewById(R.id.Problem);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        status = findViewById(R.id.status);
        Pic_issue = findViewById(R.id.Pic_issue);
        btn_backhome = findViewById(R.id.btn_backhome);

        Intent intent = getIntent();
        String JIDISSUE= intent.getStringExtra(JSON_ID_ISSUE);
        String JRECORDDATE = intent.getStringExtra(JSON_RECORDDATE);
        String JPROBLEM = intent.getStringExtra(JSON_PROBLEM );
        String JPLACE = intent.getStringExtra(JSON_PLACE);
        String JLEVEL = intent.getStringExtra(JSON_LEVEL);
        String JSTATUS = intent.getStringExtra(JSON_STATUS);

        issueID.setText(JIDISSUE);
        Createdate.setText(JRECORDDATE);
        Problem.setText(JPROBLEM);
        Place.setText(JPLACE);
        Level.setText(JLEVEL);
        status.setText(JSTATUS);
        
        Lodingimage();
        btn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), Mainuser.class);
                startActivity(back);
                finish();
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
                loading = ProgressDialog.show(Detail_issuelist.this, "Downloading...", null,true,true);
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
