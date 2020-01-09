package com.adminstrator.guaguakaapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuchunming on 2016/12/9.
 */
public class Util {
    private static String TAG = "Util";

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> cla = Class.forName("com.android.internal.R$dimen");
            Object obj = cla.newInstance();
            int height = Integer.parseInt(cla.getField("status_bar_height").get(obj).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }




    public static String getFormatTime(long time,String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(time);
    }



    public static int dp2px(Context context, float dpVal) {
        if(null == context){
            return (int) dpVal;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }



    /**
     *      * 千位分隔符,并且小数点后保留2位
     *      *
     *      * @param num
     *      * @return String
     *     
     */
    public static String qianweifenge(String num) {
        if (null != num) {
            num.trim();
        }else{
            return num;
        }

        if (null != num && num.length() >= 3) {
            try {
                DecimalFormat df = new DecimalFormat("#,###");
                double dnum = Double.parseDouble(num.replace(",", ""));

                String ss = df.format(dnum);
                if (ss.contains(".")) {
                    try {
                        return ss.split(".")[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ss;
                    }
                } else {
                    return ss;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                if (num.contains(".")) {
                    return num.split(".")[0];
                } else {
                    return num;
                }

            }
        } else {
            if (num.contains(".")) {
                return num.split(".")[0];
            } else {
                return num;
            }
        }
    }




    public static String qianweifenge_origin(String num) {
        if (null != num && num.length() >= 3) {
            num.trim();
            DecimalFormat df = new DecimalFormat("#,###");
            double dnum = Double.parseDouble(num.replace(",", ""));
            String ss = df.format(dnum);
            return ss;
        } else {
            //如果输入的第一个数是0，则不允许继续往后输
            if(null != num && num.trim().length() != 0){
                num.trim();
                if(num.substring(0,1).equals("0")){
                    return "0";
                }
            }
            return num;
        }

    }


    public static String qianweifenge_save2(String num) {
        if (null != num && num.contains("E")) {
            double d = Double.parseDouble(num);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            double dnum = Double.parseDouble(String.valueOf(d).replace(",", ""));
            String ss = df.format(dnum);
            return ss;
        }
        if (null != num && num.length() >= 3) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            double dnum = Double.parseDouble(num.replace(",", ""));
            String ss = df.format(dnum);
            return ss;
        } else {
            return num;
        }

    }

    public static boolean isNumeric(String str) {
        boolean isDigit = false;
        for (int i = 0; i < str.length() - 1; i++) {
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            }
        }
        return isDigit;
    }



    public static boolean isPkgInstalled(Activity activity, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}