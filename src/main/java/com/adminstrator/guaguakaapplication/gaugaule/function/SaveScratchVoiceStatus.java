package com.adminstrator.guaguakaapplication.gaugaule.function;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2019/8/17.
 */

public class SaveScratchVoiceStatus {
    private static final String  CONFIG_MUSIC = "configuration_music_scratch",CONFIG_VOICE = "configuration_voice_scratch",KEY_MUSIC = "music",KEY_VOICE = "voice";

    public static void saveMusicStatus(Context context, boolean status){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_MUSIC,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_MUSIC,status);
        editor.commit();
    }
    public static boolean getMusicStatus(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_MUSIC,Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_MUSIC,true);
    }

    public static void saveVoiceStatus(Context context,boolean status){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_VOICE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_VOICE,status);
        editor.commit();
    }
    public static boolean getVoiceStatus(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_VOICE,Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_VOICE,true);
    }

}
