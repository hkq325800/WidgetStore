package shellljx.gmail.com.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.io.File;

public class ImageUtil{
	private ImageUtil() { }

	public static final String TAG = ImageUtil.class.getSimpleName();

	public static final long MAX_BITMAP_SIZE   = 1024 * 1024 * 4; //允许的图片最大值;
	public static final long SAFE_DISTANCE     = 1024 * 100;      //至少要保留多大预留空间;

	public static final long SCALE_BOUND_SIZE  = 1024; //图片宽高最小值大于这个时，选择缩小图片而不是画质.

	public static final int ROTATE_MAX_SIZE = 512; //旋转图片时，图片缩放的最大尺寸

	public static final int UNCONSTRAINED = -1;

	public static final int OPTIONS_RECYCLE_INPUT = 0x2;
	public static final int OPTIONS_NONE = 0x0;
	public static final int OPTIONS_SCALE_UP = 0x1;

	/*
 * 进行图片缩放
 */
	public static Bitmap scaleImageFile(File imageFile, int size) {
		if (imageFile == null
				|| !imageFile.isFile()
				|| !imageFile.exists()
				|| size  <= 0) {
			return null;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();

		//先指定原始大小
		options.inSampleSize = 1;
		//只进行大小判断
		options.inJustDecodeBounds = true;
		//调用此方法得到options得到图片的大小
		BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		//获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		//OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		//根据options参数，减少所需要的内存
		Bitmap sourceBitmap = null;
		sourceBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

		return sourceBitmap;
	}

	public static int getScaleSampleSize(BitmapFactory.Options options, int target) {//target缩放倍数
		if (options == null || target <= 0) {
			return 1;
		}

		int w = options.outWidth;//位图产生的宽度
		int h = options.outHeight;//位图产生的高度
		int candidateW = w / target;
		int candidateH = h / target;
		int candidate = Math.max(candidateW, candidateH);//返回两个数之间最大的数
		if (candidate == 0)
			return 1;
		if (candidate > 1) {
			if ((w > target) && (w / candidate) < target) {
				candidate -= 1;
			}
		}
		if (candidate > 1) {
			if ((h > target) && (h / candidate) < target) {
				candidate -= 1;
			}
		}

		return candidate;
	}

	public static Bitmap rotate(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix matrix = new Matrix();
			matrix.setRotate(degrees,
					(float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
			try {
				Bitmap rotated = Bitmap.createBitmap(
						bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				if (bitmap != rotated) {
					bitmap.recycle();
					bitmap = rotated;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return bitmap;
	}

	/**
	 * Creates a centered bitmap of the desired size.
	 *
	 * @param source original bitmap source
	 * @param width targeted width
	 * @param height targeted height
	 */
	public static Bitmap extractThumbnail(
			Bitmap source, int width, int height) {
		return extractThumbnail(source, width, height, OPTIONS_NONE);
	}

	/**
	 * Creates a centered bitmap of the desired size.
	 *
	 * @param source original bitmap source
	 * @param width targeted width
	 * @param height targeted height
	 * @param options options used during thumbnail extraction
	 */
	public static Bitmap extractThumbnail(
			Bitmap source, int width, int height, int options) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, width, height,
				OPTIONS_SCALE_UP | options);
		return thumbnail;
	}

	/**
	 * Transform source Bitmap to targeted width and height.
	 */
	public static Bitmap transform(Matrix scaler,
								   Bitmap source,
								   int targetWidth,
								   int targetHeight,
								   int options) {
		boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
		boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;

		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
            * In this case the bitmap is smaller, at least in one dimension,
            * than the target.  Transform it by placing as much of the image
            * as possible into the target and leaving the top/bottom or
            * left/right (or both) black.
            */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(
					deltaXHalf,
					deltaYHalf,
					deltaXHalf + Math.min(targetWidth, source.getWidth()),
					deltaYHalf + Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth  - src.width())  / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(
					dstX,
					dstY,
					targetWidth - dstX,
					targetHeight - dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect   = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0,
					source.getWidth(), source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		if (recycle && b1 != source) {
			source.recycle();
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(
				b1,
				dx1 / 2,
				dy1 / 2,
				targetWidth,
				targetHeight);

		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}

		return b2;
	}

}