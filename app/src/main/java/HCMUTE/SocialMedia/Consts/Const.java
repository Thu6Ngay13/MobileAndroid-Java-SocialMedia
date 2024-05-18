package HCMUTE.SocialMedia.Consts;

import HCMUTE.SocialMedia.SharePreferances.PrefManager;

public class Const {
    public static final String TOKEN = PrefManager.getAccessToken();
    public static final String SERVER_SOCKETIO = "ws://192.168.110.114:1234";
    public static final String SERVER_API = "http://192.168.232.219:8181/api/";
    public static final String AVATAR = PrefManager.getAvatarURL();

    public static final String USERNAME = PrefManager.getUsername();
    public static final String FULLNAME = PrefManager.getFullname();
    public static final String ROLE = PrefManager.getRole();
}
