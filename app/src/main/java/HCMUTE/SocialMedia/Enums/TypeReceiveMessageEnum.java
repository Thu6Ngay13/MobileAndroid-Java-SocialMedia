package HCMUTE.SocialMedia.Enums;

import android.util.Log;

public enum TypeReceiveMessageEnum {
    YOU,
    FRIEND;

    public static TypeReceiveMessageEnum fromString(String value) {
        try{
            for (TypeReceiveMessageEnum type : TypeReceiveMessageEnum.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
        }
        catch (Exception e) {
            Log.d("TypeReceiveMessageEnum", "fromString failed " + e.getMessage());
        }
        return null;
    }
}
