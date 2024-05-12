package HCMUTE.SocialMedia.Enums;

import android.util.Log;

public enum TypeMessageEnum {
    SENDER_MESSAGE,
    SENDER_MEDIA,
    RECEIVER_MESSAGE,
    RECEIVER_MEDIA;

    public static TypeMessageEnum fromString(String value) {
        try {
            for (TypeMessageEnum type : values()) {
                if (type.name().equalsIgnoreCase(value.toUpperCase())) {
                    return type;
                }
            }
            return null;
        } catch (Exception e) {
            Log.d("TypeMessageEnum", "fromString failed " + e.getMessage());
            return null;
        }
    }

}
