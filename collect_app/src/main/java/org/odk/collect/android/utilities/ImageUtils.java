package org.odk.collect.android.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Library to modify and change image rotation for photos
 *
 * @author Dave Roberts (dave.roberts@rsoftware.co.uk)
 * @version 1.0.0
 */
public class ImageUtils {
    /**
     * Get the image orientation and the amount to rotate the image
     *
     * @param newImage The image to get the rotation amount
     * @return The amount to rotate the image by
     */
    public static int getRotationAmount(File newImage) {
        //Default to 0
        int rotationAmount = 0;
        try {
            //Get an ExifInterface to read the metadata
            ExifInterface exif = new ExifInterface(newImage.getAbsolutePath());
            //The orientation is stored as a string
            String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            //Change the orientation amount to an integer for easier parsing
            int orientation = Integer.parseInt(exifOrientation);
            switch (orientation) {
                case 1:
                    //We don't need to change anything
                    rotationAmount = 0;
                    break;
                case 3:
                    //We need to rotate by 180 degrees
                    rotationAmount = 180;
                    break;
                case 6:
                    //We need to rotate by 90 degrees
                    rotationAmount = 90;
                    break;
                case 8:
                    //We need to rotate by 270 degrees
                    rotationAmount = 270;
                    break;
                default:
                    Log.e("Get Rotation", "Invalid orientation!");
            }
        } catch (IOException iox) {
            Log.e("Get Rotation", "Failed to get orientation!");
        }
        //Return the rotation amount - if there are any errors, then use the default of 0
        return rotationAmount;
    }

    /**
     * Rotate the image
     *
     * @param newImageObj    The image file to rotate
     * @param rotationAmount The amount to rotate the image by
     */
    public static void rotateImage(File newImageObj, int rotationAmount) {
        try {
            //Get the original bitmap
            Bitmap original = BitmapFactory.decodeFile(newImageObj.getAbsolutePath());
            int originalWidth = original.getWidth();
            int originalHeight = original.getHeight();
            //Create a rotation matrix
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationAmount);
            //Create a basic output stream to work to
            FileOutputStream fos = null;
            //Create a scaled bitmap object for manipulation
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, originalWidth, originalHeight, true);
            //Rotate the bitmap to a new object
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            try {
                //Open the file for write
                fos = new FileOutputStream(newImageObj.getAbsolutePath());
                //Save the file
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (Exception ex) {
                Log.e("Saving bitmap", ex.getMessage());
            } finally {
                try {
                    if (fos != null) {
                        //Close the file
                        fos.close();
                    }
                } catch (IOException iox) {
                    Log.e("Closing bitmap", iox.getMessage());
                }
            }
        } catch (Exception ex) {
            Log.e("Rotating bitmap", ex.getMessage());
        }
    }
}
