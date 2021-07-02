package com.example.mdproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;
    
    private static final String IS_LOGIN = "isLoggedIn";
    
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_MQS1 = "mq1";
    public static final String KEY_MQS2 = "mq2";
    public static final String KEY_MQS3 = "mq3";
    public static final String KEY_MQS4 = "mq4";
    public static final String KEY_MQS5 = "mq5";
    public static final String KEY_MQS6 = "mq6";
    public static final String KEY_MQS7 = "mq7";
    public static final String KEY_LSS1 = "ls1";
    public static final String KEY_LSS2 = "ls2";
    public static final String KEY_MGS1 = "mg1";
    public static final String KEY_MGS2 = "mg2";
    public static final String KEY_MGS3 = "mg3";
    public static final String KEY_MGS4 = "mg4";
    public static final String KEY_MGS5 = "mg5";
    public static final String KEY_MGS6 = "mg6";
    public static final String KEY_MGS7 = "mg7";
    public static final String KEY_VSE = "vse";
    public static final String KEY_VBM = "vbm";

    public SessionManager(Context _context) {
        context = _context;
        usersSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String username, String name, int mqs1, int mqs2, int mqs3, int mqs4, int mqs5, int mqs6, int mqs7,
                                   int lss1, int lss2, int mgs1, int mgs2, int mgs3, int mgs4, int mgs5, int mgs6, int mgs7, int vse, int vbm) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MQS1, String.valueOf(mqs1));
        editor.putString(KEY_MQS2, String.valueOf(mqs2));
        editor.putString(KEY_MQS3, String.valueOf(mqs3));
        editor.putString(KEY_MQS4, String.valueOf(mqs4));
        editor.putString(KEY_MQS5, String.valueOf(mqs5));
        editor.putString(KEY_MQS6, String.valueOf(mqs6));
        editor.putString(KEY_MQS7, String.valueOf(mqs7));
        editor.putString(KEY_LSS1, String.valueOf(lss1));
        editor.putString(KEY_LSS2, String.valueOf(lss2));
        editor.putString(KEY_MGS1, String.valueOf(mgs1));
        editor.putString(KEY_MGS2, String.valueOf(mgs2));
        editor.putString(KEY_MGS3, String.valueOf(mgs3));
        editor.putString(KEY_MGS4, String.valueOf(mgs4));
        editor.putString(KEY_MGS5, String.valueOf(mgs5));
        editor.putString(KEY_MGS6, String.valueOf(mgs6));
        editor.putString(KEY_MGS7, String.valueOf(mgs7));
        editor.putString(KEY_VSE, String.valueOf(vse));
        editor.putString(KEY_VBM, String.valueOf(vbm));

        editor.commit();
    }

    public HashMap<String, String> getUserDataFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_NAME, usersSession.getString(KEY_NAME, null));
        userData.put(KEY_MQS1, usersSession.getString(KEY_MQS1, null));
        userData.put(KEY_MQS2, usersSession.getString(KEY_MQS2, null));
        userData.put(KEY_MQS3, usersSession.getString(KEY_MQS3, null));
        userData.put(KEY_MQS4, usersSession.getString(KEY_MQS4, null));
        userData.put(KEY_MQS5, usersSession.getString(KEY_MQS5, null));
        userData.put(KEY_MQS6, usersSession.getString(KEY_MQS6, null));
        userData.put(KEY_MQS7, usersSession.getString(KEY_MQS7, null));
        userData.put(KEY_LSS1, usersSession.getString(KEY_LSS1, null));
        userData.put(KEY_LSS2, usersSession.getString(KEY_LSS2, null));
        userData.put(KEY_MGS1, usersSession.getString(KEY_MGS1, null));
        userData.put(KEY_MGS2, usersSession.getString(KEY_MGS2, null));
        userData.put(KEY_MGS3, usersSession.getString(KEY_MGS3, null));
        userData.put(KEY_MGS4, usersSession.getString(KEY_MGS4, null));
        userData.put(KEY_MGS5, usersSession.getString(KEY_MGS5, null));
        userData.put(KEY_MGS6, usersSession.getString(KEY_MGS6, null));
        userData.put(KEY_MGS7, usersSession.getString(KEY_MGS7, null));
        userData.put(KEY_VSE, usersSession.getString(KEY_VSE, null));
        userData.put(KEY_VBM, usersSession.getString(KEY_VBM, null));

        return userData;
    }

    public void setKeyName(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public void setKeyMqs1(int score) {
        editor.putString(KEY_MQS1, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs2(int score) {
        editor.putString(KEY_MQS2, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs3(int score) {
        editor.putString(KEY_MQS3, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs4(int score) {
        editor.putString(KEY_MQS4, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs5(int score) {
        editor.putString(KEY_MQS5, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs6(int score) {
        editor.putString(KEY_MQS6, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMqs7(int score) {
        editor.putString(KEY_MQS7, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs1(int score) {
        editor.putString(KEY_MGS1, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs2(int score) {
        editor.putString(KEY_MGS2, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs3(int score) {
        editor.putString(KEY_MGS3, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs4(int score) {
        editor.putString(KEY_MGS4, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs5(int score) {
        editor.putString(KEY_MGS5, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs6(int score) {
        editor.putString(KEY_MGS6, String.valueOf(score));
        editor.commit();
    }

    public void setKeyMgs7(int score) {
        editor.putString(KEY_MGS7, String.valueOf(score));
        editor.commit();
    }

    public void setKeyLss1(int score) {
        editor.putString(KEY_LSS1, String.valueOf(score));
        editor.commit();
    }

    public void setKeyLss2(int score) {
        editor.putString(KEY_LSS2, String.valueOf(score));
        editor.commit();
    }

    public void setKeyVse(int onOff) {
        editor.putString(KEY_VSE, String.valueOf(onOff));
        editor.commit();
    }

    public void setKeyVbm(int onOff) {
        editor.putString(KEY_VBM, String.valueOf(onOff));
        editor.commit();
    }

    public boolean checkLogin() {
        if(usersSession.getBoolean(IS_LOGIN, true)) return true;
        else return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
