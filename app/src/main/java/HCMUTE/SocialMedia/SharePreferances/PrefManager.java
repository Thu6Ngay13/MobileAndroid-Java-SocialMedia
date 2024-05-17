package HCMUTE.SocialMedia.SharePreferances;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import HCMUTE.SocialMedia.Activities.LoginActivity;
import HCMUTE.SocialMedia.Enums.Role;

public class PrefManager {
    private static final String SHARED_PREF_NAME = "LoginDetails";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ACCESS_TOKEN = "keyaccesstoken";
    private static final String KEY_ROLE = "keyrole";
    private static PrefManager mIntance;
    private static Context ctx;

    public PrefManager(Context ctx) {
        this.ctx = ctx;
    }
    public static synchronized PrefManager getInstance(Context context){
        if (mIntance == null){
            mIntance = new PrefManager(context);
        }
        return mIntance;
    }
    public void login(String username, String email, String accessToken, String role){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
    public String getAccessToken(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "");
    }
    public String getRole(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE, "");
    }
    public String getEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public boolean isUserLoggedOut(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString(KEY_EMAIL, "").isEmpty();
        boolean isUsernameEmpty = sharedPreferences.getString(KEY_USERNAME,"").isEmpty();
        return isUsernameEmpty || isEmailEmpty;
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivities(new Intent[]{intent});
    }
}
