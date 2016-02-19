package shellljx.gmail.com.app.support.imageUtility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import shellljx.gmail.com.app.support.FileManager;
import shellljx.gmail.com.app.support.Utility;

import static android.media.ExifInterface.ORIENTATION_NORMAL;
import static android.media.ExifInterface.ORIENTATION_ROTATE_180;
import static android.media.ExifInterface.ORIENTATION_ROTATE_270;
import static android.media.ExifInterface.ORIENTATION_ROTATE_90;
import static android.media.ExifInterface.TAG_ORIENTATION;

/**
 * Created by Administrator on 2015/6/10.
 */
public class ImageUtility {

    public static final int WITH_UNDEFIND = -1;
    public static final int HEIGHT_UNDEFINED = -1;


    public static boolean isThisBitmapCanRead(String path){
        if(TextUtils.isEmpty(path)){
            return false;
        }
        File file = new File(path);
        if(!file.exists()){
            return false;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int width = options.outWidth;
        int height = options.outHeight;
        if(width==-1||height==-1){
            return false;
        }
        return true;
    }

    public static Bitmap decodeBitmapFromSDCard(String path,
                                                int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getCreatePostPictureThumblr(String path){
        try {
            //actionbar button image width and height is 32 dip
            int reqWidth = Utility.dip2px(32);
            int reqHeight = Utility.dip2px(32);

            if (!FileManager.isExternalStorageMounted()) {
                return null;
            }

            boolean fileExist = new File(path).exists();

            if (!fileExist) {
                return null;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            if (bitmap == null) {
                //this picture is broken,so delete it
                new File(path).delete();
                return null;
            }

            int[] size = calcResize(bitmap.getWidth(), bitmap.getHeight(), reqWidth, reqHeight);
            if (size[0] > 0 && size[1] > 0) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size[0], size[1], true);
                if (scaledBitmap != bitmap) {
                    bitmap.recycle();
                    bitmap = scaledBitmap;
                }
            }

            Bitmap roundedBitmap = ImageEditUtility.getRoundedCornerBitmap(bitmap);
            if (roundedBitmap != bitmap) {
                bitmap.recycle();
                bitmap = roundedBitmap;
            }

            int exifRotation = ImageUtility.getFileExifRotation(path);
            if (exifRotation != 0) {
                Matrix mtx = new Matrix();
                mtx.postRotate(exifRotation);
                Bitmap adjustedBitmap = Bitmap.createBitmap(roundedBitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), mtx, true);
                if (adjustedBitmap != bitmap) {
                    bitmap.recycle();
                    bitmap = adjustedBitmap;
                }
            }

            return bitmap;
        } catch (OutOfMemoryError ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (height > reqHeight && reqHeight != 0) {
                inSampleSize = (int) Math.floor((double) height / (double) reqHeight);
            }

            int tmp = 0;

            if (width > reqWidth && reqWidth != 0) {
                tmp = (int) Math.floor((double) width / (double) reqWidth);
            }

            inSampleSize = Math.max(inSampleSize, tmp);
        }
        int roundedSize;
        if (inSampleSize <= 8) {
            roundedSize = 1;
            while (roundedSize < inSampleSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (inSampleSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int[] calcResize(int actualWidth, int actualHeight, int reqWidth,
                                    int reqHeight) {

        int height = actualHeight;
        int width = actualWidth;

        float betweenWidth = ((float) reqWidth) / (float) actualWidth;
        float betweenHeight = ((float) reqHeight) / (float) actualHeight;

        float min = Math.min(betweenHeight, betweenWidth);

        height = (int) (min * actualHeight);
        width = (int) (min * actualWidth);

        return new int[]{width, height};
    }

    public static int getFileExifRotation(String filePath) {
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(TAG_ORIENTATION,
                    ORIENTATION_NORMAL);
            switch (orientation) {
                case ORIENTATION_ROTATE_90:
                    return 90;
                case ORIENTATION_ROTATE_180:
                    return 180;
                case ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            return 0;
        }
    }
}
