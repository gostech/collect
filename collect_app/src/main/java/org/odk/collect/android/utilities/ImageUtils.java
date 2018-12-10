package org.odk.collect.android.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageUtils {
    public static void resizeImage(File input, File output) throws FileNotFoundException {
        resizeBitmap(input, 1024, 768).compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(output));
    }

    public static Bitmap resizeBitmap(File file, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(file.getPath(), bmOptions);
    }
}
