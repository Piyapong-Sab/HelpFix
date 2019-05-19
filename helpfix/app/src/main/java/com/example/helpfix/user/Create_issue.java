package com.example.helpfix.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.helpfix.adapter_navigation.BottomNavigationViewHelper;
import com.example.helpfix.adapter_user.ModelCategory;
import com.example.helpfix.adapter_user.ModelSCategory;
import com.example.helpfix.session_login.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class Create_issue extends AppCompatActivity {

    private static final String TAG = "Create_issue";
    private TextView Create_issue, Create_by, Record_date, userCreate ,TXT_Category , TXT_SCategory,MainID , put;
    private EditText Place, Level, Problem;
    private ImageView Pic_issue;
    private Button btn_choose, btn_save;
    SessionManager sessionManager;
    private Bitmap bitmap;
    private ProgressBar loading;
    ProgressDialog progressDialog;
    private static final String URL_CREATE = "http://203.158.131.67/~devhelpfix/apphelpfix/create_issue.php";
    private static final String URL_LASTID = "http://203.158.131.67/~devhelpfix/apphelpfix/view_max_idissue.php";

    private String URL_CATEGORY = "http://203.158.131.67/~devhelpfix/apphelpfix/get_category.php";
    private static ProgressDialog mProgressDialog;
    private ArrayList<ModelCategory> goodModelArrayList;
    private ArrayList<String> Category = new ArrayList<String>();
    private ArrayList<String> PUT = new ArrayList<String>();

    private String URL_SUBCATEGORY = "http://203.158.131.67/~devhelpfix/apphelpfix/get_subcategory.php";
    private ArrayList<ModelSCategory> SUBModelArrayList;
    private ArrayList<String> SCategory = new ArrayList<String>();

    private Spinner SpinCategory , SpinSCategory;
    RequestQueue requestQueue;
    private Uri uri;
    public static final int REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_issue);
        unit();
    }

    public void unit() {
        Menu_navigation();

        put = findViewById(R.id.put);
        ShowlastIDissue();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCancelable(false);
        TXT_Category = findViewById(R.id.TXT_Category);
        TXT_SCategory = findViewById(R.id.TXT_SCategory);
        Create_issue = findViewById(R.id.Create_issue);
        Create_by = findViewById(R.id.Create_by);
        userCreate = findViewById(R.id.userCreate);
        Record_date = findViewById(R.id.Record_date);
        SpinCategory = findViewById(R.id.SpinCategory);
        SpinSCategory = findViewById(R.id.SpinSCategory);
        Place = findViewById(R.id.Place);
        Level = findViewById(R.id.Level);
        Problem = findViewById(R.id.Problem);
        Pic_issue = findViewById(R.id.Pic_issue);
        btn_choose = findViewById(R.id.btn_choose);
        btn_save = findViewById(R.id.btn_save);
        loading = findViewById(R.id.progressBar);
        MainID = findViewById(R.id.MainID);
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        String musercrate = user.get(sessionManager.USERID);
        Create_by.setText(mName);
        userCreate.setText(musercrate);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        Record_date.setText(formattedDate);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateIssue();
            }
        });

        CATEGORYJSON();

        SpinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String CATEGORY =   SpinCategory.getItemAtPosition(SpinCategory.getSelectedItemPosition()).toString();
                //final String MAINID = CATEGORY.substring(4,CATEGORY.length());
                //Toast.makeText(getApplicationContext(),CATEGORY ,Toast.LENGTH_LONG).show();
                TXT_Category.setText(CATEGORY);
                final String MCATEGOEY = TXT_Category.getText().toString().trim();

                final String mainCategory = MCATEGOEY.substring(0,3);
                WhereSubCategory(mainCategory);

                put.setText(mainCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        SpinSCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String SUBCATEGORY =   SpinSCategory.getItemAtPosition(SpinSCategory.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),country ,Toast.LENGTH_LONG).show();
                TXT_SCategory.setText(SUBCATEGORY);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void Menu_navigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent intent = new Intent(Create_issue.this, Mainuser.class);
                        startActivity(intent);

                        break;

                    case R.id.Create:
                        Intent intent1 = new Intent(Create_issue.this, Create_issue.class);
                        startActivity(intent1);
                        break;

                    case R.id.Status:
                        Intent intent2 = new Intent(Create_issue.this, Issue_list.class);
                        startActivity(intent2);
                        break;

                }


                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void WhereSubCategory(final String mainCategory){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUBCATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                SUBModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    ModelSCategory playerModel = new ModelSCategory();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setId_s_category(dataobj.getString("Id_s_category"));
                                    playerModel.setId_m_category(dataobj.getString("Id_m_category"));
                                    playerModel.setScategory_name(dataobj.getString("Scategory_name"));

                                    SUBModelArrayList.add(playerModel);

                                }

                                SCategory.clear();
                                for (int i = 0; i < SUBModelArrayList.size(); i++){
                                    SCategory.add(SUBModelArrayList.get(i).getId_s_category().toString() + " " +
                                            SUBModelArrayList.get(i).getScategory_name().toString());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Create_issue.this, simple_spinner_item, SCategory);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                SpinSCategory.setAdapter(spinnerArrayAdapter);
                                removeSimpleProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mainCategory" , mainCategory);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void CATEGORYJSON() {
        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("CATEGORY", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                goodModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    ModelCategory playerModel = new ModelCategory();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setId_m_category(dataobj.getString("Id_m_category"));
                                    playerModel.setMcategory_name(dataobj.getString("Mcategory_name"));

                                    goodModelArrayList.add(playerModel);

                                }

                                for (int i = 0; i < goodModelArrayList.size(); i++){
                                    Category.add(goodModelArrayList.get(i).getId_m_category().toString() + " " +
                                            goodModelArrayList.get(i).getMcategory_name().toString());



                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Create_issue.this,
                                        simple_spinner_item, Category);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                SpinCategory.setAdapter(spinnerArrayAdapter);
                                removeSimpleProgressDialog();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShowlastIDissue() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_LASTID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("maxid");

                    Log.d(TAG, "last id Response: " + response.toString());
                    hideDialog();
                    if (success.equals("1")){

                        for (int i =0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String lastid = object.getString("lastid").trim();

                            Create_issue.setText(lastid);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Create_issue.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Create_issue.this, "Login Error!" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void chooseFile() {
/*  open camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String timeStamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "IMG_" + timeStamp + ".jpg";
            File f = new File(Environment.getExternalStorageDirectory()
                    , "DCIM/Camera/" + imageFileName);
            uri = Uri.fromFile(f);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(Intent.createChooser(intent
                    , "Take a picture with"), REQUEST_CAMERA);

*/
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
                Pic_issue.setVisibility(View.VISIBLE);
                Pic_issue.setImageBitmap(bitmap);

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

    public void CreateIssue(){

        final String screate_issue = Create_issue.getText().toString().trim();
        final String screate_by = userCreate.getText().toString().trim();
        final String splace =  Place.getText().toString().trim();
        final String slevel = Level.getText().toString().trim();
        final String sproblem =  Problem.getText().toString().trim();
        final String sCategory =  TXT_Category.getText().toString().trim();
        final String sSCategory =  TXT_SCategory.getText().toString().trim();

        if (!screate_issue.isEmpty() && !screate_by.isEmpty() && !splace.isEmpty() && !slevel.isEmpty() && !sproblem.isEmpty()
        && !sCategory.isEmpty() && !sSCategory.isEmpty()){
            Create(screate_issue ,screate_by ,splace , slevel ,sproblem , getStringImage(bitmap) ,sCategory ,sSCategory );
        }else if (splace.isEmpty()) {
            Place.setError("กรุณากรอกสถานที่");
        }else if (slevel.isEmpty()){
            Level.setError("กรุณาากรอกชื่อห้อง หรือชั้น");
        }else if(sproblem.isEmpty()){
            Problem.setError("กรุณาากรอกอาการ หรือปัญหา");
        }
        //else if (getStringImage(bitmap).isEmpty()){
        //    Toast.makeText(this, "กรุณาแนบรูปภาพ", Toast.LENGTH_SHORT).show();
        //}

    }
    private void Create(final String Cre_id ,final String User_by , final String Pla_ce , final String Lev_el
            , final String Pro_blem , final String Pic_is , final String Category , final String SCategory){

        progressDialog.setMessage("Saving ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST ,URL_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Create Response: " + response.toString());
                        hideDialog();


                        Intent intent = new Intent(getApplicationContext(), Mainuser.class);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Create_issue.this, "Create Error!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("Cre_id" ,Cre_id );
                params.put("User_by" , User_by);
                params.put("Pla_ce" , Pla_ce);
                params.put("Lev_el" , Lev_el);
                params.put("Pro_blem" , Pro_blem);
                params.put("Pic_is" , Pic_is);
                params.put("Category" , Category);
                params.put("SCategory", SCategory);
                return params;
            }
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
}
