package HCMUTE.SocialMedia.SharePreferances;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String SHARED_PREF_NAME = "LoginDetails";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ACCESS_TOKEN = "keyaccesstoken";
    private static final String KEY_ROLE = "keyrole";
    private static final String KEY_AVATAR = "keyavatar";
    private static final String KEY_FULLNAME = "keyfullname";
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
    public static void login(String username, String email, String accessToken, String role, String avatarURL, String fullname){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_ROLE, role);
        if (avatarURL != null){
            editor.putString(KEY_AVATAR, avatarURL);
        }
        else {
            editor.putString(KEY_AVATAR, "https://drive.google.com/uc?export=view&id=1LZuoz5KlfRIOJiolzkcGDva0GaCN_NCl");
        }
        editor.putString(KEY_FULLNAME, fullname);
        editor.apply();
    }
    public static boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
    public static String getUsername(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, "");
    }
    public static String getFullname(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FULLNAME, "");
    }
    public static String getAvatarURL(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AVATAR, "");
    }
    public static String getAccessToken(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "");
    }
    public static String getRole(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE, "");
    }

    public static String getEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public static void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
