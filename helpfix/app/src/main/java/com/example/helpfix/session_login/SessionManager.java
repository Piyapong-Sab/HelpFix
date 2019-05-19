package com.example.helpfix.session_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.helpfix.head_division.Mainhead_division;
import com.example.helpfix.login_regist.Login;
import com.example.helpfix.manager.Mainmanager;
import com.example.helpfix.technician.Maintechnician;
import com.example.helpfix.user.Mainuser;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String USERID ="USERID";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences =context.getSharedPreferences(PREF_NAME , PRIVATE_MODE);
        editor =  sharedPreferences.edit();
    }
    public void createSession(String userid ,String name , String email ){
        editor.putBoolean(LOGIN ,true);
        editor.putString(USERID , userid);
        editor.putString(NAME, name);
        editor.putString(EMAIL , email);
        editor.apply();

    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i =new Intent(context, Login.class);
            context.startActivity(i);
            ((Mainuser) context).finish();
            //
        }
    }
    public HashMap<String , String> getUserDetail(){
        HashMap<String , String> user = new HashMap<>();
        user.put(USERID , sharedPreferences.getString(USERID , null));
        user.put(NAME , sharedPreferences.getString(NAME , null));
        user.put(EMAIL  ,sharedPreferences.getString(EMAIL , null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context, Login.class);
        context.startActivity(i);
        ((Mainuser ) context).finish();

    }
    public void logouthead(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context, Login.class);
        context.startActivity(i);
        ((Mainhead_division) context).finish();

    }
    public void logoutmanage(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context, Login.class);
        context.startActivity(i);
        ((Mainmanager) context).finish();

    }
    public void logouttechnician(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context, Login.class);
        context.startActivity(i);
        ((Maintechnician) context).finish();

    }

}
