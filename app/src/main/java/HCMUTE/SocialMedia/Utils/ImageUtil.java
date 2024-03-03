package HCMUTE.SocialMedia.Utils;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class ImageUtil {

    public static int[] getImageDimensions(Resources resources, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(resources, resId, options);

        int width = options.outWidth;
        int height = options.outHeight;

        return new int[]{width, height};
    }
}
