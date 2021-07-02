package com.example.mdproject;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashMap;

public class mediaPlayerHandler {

    private mediaPlayerHandler() {}

    public static void optimizeBGM(Context context, int category) {
        SessionManager sessionManager = new SessionManager(context);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();

        if(category == 1) {
            LoginActivity.main.reset();
            LoginActivity.main = MediaPlayer.create(context, R.raw.main);
            if(userInfo.get(SessionManager.KEY_VBM).equals("1")) LoginActivity.main.setVolume(1,1);
            if(userInfo.get(SessionManager.KEY_VBM).equals("0")) LoginActivity.main.setVolume(0,0);
            LoginActivity.main.start();
        }
        if(category == 2) {
            MainActivity.gamePlaySound.reset();
            MainActivity.gamePlaySound = MediaPlayer.create(context, R.raw.game_bg);
            if(userInfo.get(SessionManager.KEY_VBM).equals("1")) MainActivity.gamePlaySound.setVolume(1,1);
            if(userInfo.get(SessionManager.KEY_VBM).equals("0")) MainActivity.gamePlaySound.setVolume(0,0);
            MainActivity.gamePlaySound.start();
        }
    }
}
