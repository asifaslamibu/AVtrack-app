package com.abs.avtrack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "password";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "login";
    private static final String PREF_NAME = "AndroidHivePref";
    int PRIVATE_MODE = 0;
    Context _context;
    Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createLoginSession(String id, String name, String previ) {
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putString(KEY_ID, id);
        this.editor.putString(KEY_NAME, name);
        this.editor.putString(KEY_EMAIL, previ);
        this.editor.commit();
    }

    public void checkLogin() {
        if (isLoggedIn()) {
            Intent i = new Intent(this._context, Dashboard.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this._context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences = this.pref;
        String str = KEY_ID;
        user.put(str, sharedPreferences.getString(str, null));
        SharedPreferences sharedPreferences2 = this.pref;
        String str2 = KEY_NAME;
        user.put(str2, sharedPreferences2.getString(str2, null));
        SharedPreferences sharedPreferences3 = this.pref;
        String str3 = KEY_EMAIL;
        user.put(str3, sharedPreferences3.getString(str3, null));
        return user;
    }

    public void logoutUser() {
        this.editor.clear();
        this.editor.commit();
        Intent i = new Intent(this._context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this._context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return this.pref.getBoolean(IS_LOGIN, false);
    }
}
