package com.example.photofilter.uitls;



import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
/**
 * @author Trung
 */
public class AppPreferences {


    public static void setIntroData(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.apply();
    }

    public static boolean isIntroData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }
}




